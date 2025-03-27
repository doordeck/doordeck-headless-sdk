import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";

@Component({
    selector: 'app-change-password',
    imports: [
        FormsModule,
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
    templateUrl: './change-password.component.html',
    styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {

  form: FormGroup;

  constructor(public dialogRef: MatDialogRef<ChangePasswordComponent>, private fb: FormBuilder) {
    this.form = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  submit(): void {
    const data = {
      oldPassword: this.form.get('oldPassword')!.value,
      newPassword: this.form.get('newPassword')!.value
    }
    this.dialogRef.close(data);
  }
}
