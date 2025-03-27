import { Component } from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';

@Component({
    selector: 'app-two-factor-verify',
    imports: [
        MatDialogTitle,
        MatDialogContent,
        ReactiveFormsModule,
        MatFormField,
        MatDialogActions,
        MatInput,
        MatButton,
        MatLabel,
        NgIf,
        MatError
    ],
    templateUrl: './two-factor-verify.component.html',
    styleUrl: './two-factor-verify.component.css'
})
export class TwoFactorVerifyComponent {
  form: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<TwoFactorVerifyComponent>,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      code: ['', [Validators.required, Validators.pattern(/^\d{6}$/)]],
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  submit(): void {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value.code);
    }
  }
}
