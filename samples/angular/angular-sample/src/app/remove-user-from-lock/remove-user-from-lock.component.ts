import { Component } from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
    selector: 'app-remove-user-from-lock',
    imports: [
        MatButton,
        MatDialogActions,
        MatDialogContent,
        MatDialogTitle,
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
