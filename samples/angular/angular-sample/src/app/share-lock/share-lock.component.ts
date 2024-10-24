import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {MatStep, MatStepLabel, MatStepper, MatStepperNext, MatStepperPrevious} from '@angular/material/stepper';
import {MatSlideToggle} from '@angular/material/slide-toggle';

@Component({
  selector: 'app-share-lock',
  standalone: true,
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
    ReactiveFormsModule,
    MatStepper,
    MatStep,
    MatStepperPrevious,
    MatStepperNext,
    MatStepLabel,
    MatSlideToggle
  ],
  templateUrl: './share-lock.component.html',
  styleUrl: './share-lock.component.css'
})
export class ShareLockComponent {

  errorMessage: string | null = null;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(public dialogRef: MatDialogRef<ShareLockComponent>, private fb: FormBuilder) {
    this.firstFormGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });
    this.secondFormGroup = this.fb.group({
      isAdministrator: [false],
      isVisitor: [true],
    });

    this.secondFormGroup.get('isAdministrator')?.valueChanges.subscribe(isAdmin => {
      if (isAdmin) {
        this.secondFormGroup.get('isVisitor')?.setValue(false);
      }
    });
    this.secondFormGroup.get('isVisitor')?.valueChanges.subscribe(isVisitor => {
      if (isVisitor) {
        this.secondFormGroup.get('isAdministrator')?.setValue(false);
      }
    });
  }

  canCompleteShare() {
    return (this.secondFormGroup.get('isAdministrator')?.value === true ||
      this.secondFormGroup.get('isVisitor')?.value === true);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  submit(): void {
    const data = {
      email: this.firstFormGroup.get('email')!.value,
      isAdmin: this.secondFormGroup.get('isAdministrator')?.value === true
    }
    this.dialogRef.close(data);
  }
}
