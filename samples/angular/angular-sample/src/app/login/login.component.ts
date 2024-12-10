import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatAnchor, MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';
import {Router} from '@angular/router';
import {accountlessResource, doordeckSDK, accountResource, doordeckCrypto} from '../../main';
import {com} from '@doordeck/doordeck-headless-sdk';
import {TwoFactorVerifyComponent} from '../two-factor-verify/two-factor-verify.component';
import {MatDialog} from '@angular/material/dialog';
import {MatIcon} from '@angular/material/icon';
import {MatSnackBar} from '@angular/material/snack-bar';
import ForbiddenException = com.doordeck.multiplatform.sdk.ForbiddenException;
import UnauthorizedException = com.doordeck.multiplatform.sdk.UnauthorizedException;
import TooManyRequestsException = com.doordeck.multiplatform.sdk.TooManyRequestsException;
import KeyPair = com.doordeck.multiplatform.sdk.api.model.Crypto.KeyPair;

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardTitle,
    MatCard,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatError,
    MatLabel,
    MatButton,
    NgIf,
    MatAnchor,
    MatIcon
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent  {
  loginForm: FormGroup;
  errorMessage: string | null = null;
  keyPair: KeyPair | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  async onLogin() {
    if (this.loginForm.valid) {
      const username = this.loginForm.value.username;
      const password = this.loginForm.value.password;

      try {
        // Attempt to log-in
        await accountlessResource.login(username, password);
      } catch (error) {
        if (error instanceof UnauthorizedException) {
          this.openSnackBar("Failed to login", "")
        }
        return;
      }
      // Generate a new key pair
      this.keyPair = doordeckCrypto.generateKeyPair();
      // Register the key pair
      await accountResource.registerEphemeralKeyWithSecondaryAuthentication(this.keyPair.public)

      // Two factor dialog
      const dialogRef = this.dialog.open(TwoFactorVerifyComponent, {
        width: '500px',
      });
      dialogRef.afterClosed().subscribe(async (result) => {
        if (result) {
          try {
            // Attempt to verify the key pair
            await accountResource.verifyEphemeralKeyRegistration(result, this.keyPair!.private).then(async (response) => {
              // Set the operation context
              doordeckSDK.contextManager().setOperationContext(response.userId, response.certificateChain, this.keyPair!.public, this.keyPair!.private);
              // Redirect to the dashboard
              await this.router.navigate(['dashboard']);
            });
          } catch(error) {
            if (error instanceof ForbiddenException) {
              this.openSnackBar("Code is invalid", "")
            }
            if (error instanceof TooManyRequestsException) {
              this.openSnackBar("Too many pending verifications", "")
            }
          }
        }
      });
    }
  }

  private openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, { "duration": 2000 });
  }
}
