import { Component } from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {NgIf} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-remove-user-from-lock',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './remove-user-from-lock.component.html',
  styleUrl: './remove-user-from-lock.component.css'
})
export class RemoveUserFromLockComponent {

  userEmail: string | undefined;
  lockName: string | undefined;

  constructor(public dialogRef: MatDialogRef<RemoveUserFromLockComponent>) {
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  submit(): void {
    this.dialogRef.close(true);
  }
}
