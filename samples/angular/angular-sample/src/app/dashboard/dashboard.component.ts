import {Component, OnInit} from '@angular/core';
import {MatIcon, MatIconModule} from '@angular/material/icon';
import {MatToolbar, MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';

import {MatCard, MatCardContent} from '@angular/material/card';
import {Router} from '@angular/router';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from '@angular/material/expansion';
import {com, kotlin} from '@doordeck/doordeck-headless-sdk';
import {MatTab, MatTabGroup} from '@angular/material/tabs';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import SiteResponse = com.doordeck.multiplatform.sdk.model.responses.SiteResponse;
import SiteLocksResponse = com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse;
import UserLockResponse = com.doordeck.multiplatform.sdk.model.responses.UserLockResponse;
import UserRole = com.doordeck.multiplatform.sdk.model.common.UserRole;
import AuditResponse = com.doordeck.multiplatform.sdk.model.responses.AuditResponse;
import AuditEvent = com.doordeck.multiplatform.sdk.model.common.AuditEvent;
import {MatFormField, MatFormFieldModule, MatLabel} from '@angular/material/form-field';
import {MatInput, MatInputModule} from '@angular/material/input';
import {RemoveUserFromLockComponent} from '../remove-user-from-lock/remove-user-from-lock.component';
import {MatDialog} from '@angular/material/dialog';
import KtList = kotlin.collections.KtList;
import {
  MatDatepickerModule,
  MatDatepickerToggle,
  MatDateRangeInput
} from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import {combineLatest} from 'rxjs';
import {ShareLockComponent} from '../share-lock/share-lock.component';
import ShareLock = com.doordeck.multiplatform.sdk.model.data.LockOperations.ShareLock;
import {ChangeDisplayNameComponent} from '../change-display-name/change-display-name.component';
import {ChangePasswordComponent} from '../change-password/change-password.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import UnlockOperation = com.doordeck.multiplatform.sdk.model.data.LockOperations.UnlockOperation;
import BaseOperation = com.doordeck.multiplatform.sdk.model.data.LockOperations.BaseOperation;
import RevokeAccessToLockOperation = com.doordeck.multiplatform.sdk.model.data.LockOperations.RevokeAccessToLockOperation;
import ShareLockOperation = com.doordeck.multiplatform.sdk.model.data.LockOperations.ShareLockOperation;
import Utils = com.doordeck.multiplatform.sdk.util.Utils;
import api = com.doordeck.multiplatform.sdk.api;

@Component({
    selector: 'app-dashboard',
    imports: [
    MatIcon,
    MatToolbar,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatMenu,
    MatMenuTrigger,
    MatMenuItem,
    MatCard,
    MatCardContent,
    MatExpansionPanel,
    MatAccordion,
    MatExpansionPanelHeader,
    MatTab,
    MatTabGroup,
    MatExpansionPanelTitle,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCellDef,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatRowDef,
    MatHeaderRowDef,
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatDateRangeInput,
    MatDatepickerToggle,
    MatDatepickerModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule
],
    providers: [
        provideNativeDateAdapter()
    ],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit  {

  displayName: string = '';
  sites : SiteResponse[] = [];
  locksForSelectedSite: SiteLocksResponse[] = [];
  usersDisplayedColumns: string[] = ['user-email', 'user-action'];
  activityDisplayedColumns: string[] = ['activity-email', 'activity-date'];
  usersForSelectedLock: MatTableDataSource<UserLockResponse> = new MatTableDataSource();
  adminsForSelectedLock: MatTableDataSource<UserLockResponse> = new MatTableDataSource();
  auditForSelectedLock: MatTableDataSource<AuditResponse> = new MatTableDataSource();
  selectedSiteIndex: number | null = null;
  selectedLockIndex: number | null = null;
  selectedLockId: string | null = null;
  unlocking: boolean = false;
  searchUserInput: string = '';
  dateForm: FormGroup;

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    const date = new Date();
    date.setDate(date.getDate() - 7);
    this.dateForm = new FormGroup({
      start: new FormControl(date),
      end: new FormControl(new Date()),
    });

    combineLatest([
      this.dateForm.get('start')!.valueChanges,
      this.dateForm.get('end')!.valueChanges
    ]).subscribe(([startDate, endDate]) => {
      if (startDate && endDate && startDate < endDate) {
        api.lockOperations().getLockAuditTrail(this.selectedLockId!, Math.floor(startDate.getTime() / 1000), Math.floor((endDate.getTime() / 1000))).then((response) => {
          this.auditForSelectedLock = new MatTableDataSource<AuditResponse>(response.asJsReadonlyArrayView().map((e) => e));
        });
      }
    });
  }

  ngOnInit(): void {
    // Load the sites
    api.sites().listSites().then(response => {
      this.sites = response.asJsReadonlyArrayView().map((e) => e);
    });
    api.account().getUserDetails().then((response) => {
      this.displayName = response.displayName ? response.displayName : response.email;
    });
  }

  selectSite(siteId: string, index: number) {
    this.selectedLockIndex = null;
    this.selectedSiteIndex = this.selectedSiteIndex === index ? null : index;

    // Load the locks for the selected site
    api.sites().getLocksForSite(siteId).then(response => {
      this.locksForSelectedSite = response.asJsReadonlyArrayView().map((e) => e);
    });
  }

  selectLock(index: number, lockId: string) {
    this.selectedLockIndex = this.selectedLockIndex === index ? null : index;
    this.selectedLockId = this.selectedLockId === lockId ? null : lockId;

    // Load users for lock
    api.lockOperations().getUsersForLock(lockId).then(response => {
      const users = response.asJsReadonlyArrayView().map((e) => e);
      this.usersForSelectedLock = new MatTableDataSource<UserLockResponse>(users.filter(u => u.role == UserRole.USER));
      this.adminsForSelectedLock = new MatTableDataSource<UserLockResponse>(users.filter(u => u.role == UserRole.ADMIN));
    });

    // Load lock activity
    api.lockOperations().getLockAuditTrail(lockId, Math.floor(this.dateForm.get('start')?.value.getTime() / 1000), Math.floor((this.dateForm.get('end')?.value.getTime() / 1000))).then((response) => {
      this.auditForSelectedLock = new MatTableDataSource<AuditResponse>(response.asJsReadonlyArrayView().map((e) => e));
    });
  }

  applyUsersFilter(): void {
    const filter = this.searchUserInput.trim().toLowerCase();
    this.usersForSelectedLock.filter = filter;
    this.adminsForSelectedLock.filter = filter;
  }

  async unlock(lockId: string) {
    // Unlock
    const operation = new UnlockOperation.Builder()
      .setBaseOperation(new BaseOperation.Builder()
        .setLockId(lockId)
        .build())
      .build()
    await api.lockOperations().unlock(operation).then(() => {
      this.openSnackBar("Successfully unlocked", "");
    });

    this.unlocking = true;
    setTimeout(() => {this.unlocking = false}, 3000);
  }

  async logout() {
    await api.account().logout()
    await this.router.navigate(['login']);
  }

  getUtcDate(timestamp: number): string {
    const date = new Date();
    date.setTime(timestamp * 1000);
    return date.toUTCString();
  }

  getType(type: AuditEvent): string {
    const lowerCase = type.name.toLocaleLowerCase().replace("_", " ");
    return lowerCase.charAt(0).toUpperCase() + lowerCase.slice(1);
  }

  removeUserFromLock(lockId: string, lockName: string, userEmail: string, userId: string) {
    const dialogRef = this.dialog.open(RemoveUserFromLockComponent, {
      width: '500px',
      height: '250px'
    });
    dialogRef.componentInstance.userEmail = userEmail;
    dialogRef.componentInstance.lockName = lockName;

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result) {
        // Remove this user from the lock
        const operation = new RevokeAccessToLockOperation.Builder()
          .setBaseOperation(new BaseOperation.Builder()
            .setLockId(lockId)
            .build())
          .setUsers(KtList.fromJsArray([userId]))
          .build()
        await api.lockOperations().revokeAccessToLock(operation).then(() => {
          this.usersForSelectedLock.data = this.usersForSelectedLock.data.filter((u) => u.userId !== userId)
          this.adminsForSelectedLock.data = this.adminsForSelectedLock.data.filter((u) => u.userId !== userId)
        })
      }
    })
  }

  shareLock(lockId: string) {
    const dialogRef = this.dialog.open(ShareLockComponent, {
      width: '800px',
      height: '400px'
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result) {
        const { email, isAdmin } = result;
        // Get the user public key
        await api.lockOperations().getUserPublicKey(email).then(async (result) => {
          // Share the lock
          const shareLock = new ShareLock.Builder()
            .setTargetUserId(result.id)
            .setTargetUserRole(isAdmin ? UserRole.ADMIN : UserRole.USER)
            .setTargetUserPublicKey(Utils.decodeBase64ToByteArray(result.publicKey))
            .build();
          const operation = new ShareLockOperation.Builder()
            .setBaseOperation(new BaseOperation.Builder()
                .setLockId(lockId)
                .build())
            .setShareLock(shareLock)
            .build();
          await api.lockOperations().shareLock(operation);
        });
      }
    })
  }

  changeDisplayName() {
    const dialogRef = this.dialog.open(ChangeDisplayNameComponent, {
      width: '500px',
      height: '250px'
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result) {
        await api.account().updateUserDetails(result).then(() => {
          this.displayName = result;
          this.openSnackBar("Display name successfully changed", "");
        });
      }
    })
  }

  changePassword() {
    const dialogRef = this.dialog.open(ChangePasswordComponent, {
      width: '500px',
      height: '350px'
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result) {
        const { oldPassword, newPassword } = result;
        await api.account().changePassword(oldPassword, newPassword).then(() => {
          this.openSnackBar("Password successfully changed", "");
        });
      }
    })
  }

  private openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, { "duration": 2000 });
  }

  protected readonly document = document;
}
