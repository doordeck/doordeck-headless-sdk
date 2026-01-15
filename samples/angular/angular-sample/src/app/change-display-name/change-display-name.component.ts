import { Component } from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatError, MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';

import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
    selector: 'app-change-display-name',
    imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
],
    templateUrl: './change-display-name.component.html',
    styleUrl: './change-display-name.component.css'
})
export class ChangeDisplayNameComponent {

  form: FormGroup;

  constructor(public dialogRef: MatDialogRef<ChangeDisplayNameComponent>, private fb: FormBuilder) {
    this.form = this.fb.group({
      displayName: ['', Validators.required],
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  submit(): void {
    this.dialogRef.close(this.form.get('displayName')?.value);
  }
}
