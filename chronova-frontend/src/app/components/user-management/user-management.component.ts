import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { User, UserSearchFilter } from '../../models/user.model';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <h2 class="mb-4">User Management</h2>
      
      <!-- Add User Form -->
      <div class="card mb-4">
        <div class="card-header">
          <h5>Add New User</h5>
        </div>
        <div class="card-body">
          <form (ngSubmit)="saveUser()" #userForm="ngForm">
            <div class="row">
              <div class="col-md-4 mb-3">
                <label class="form-label">Username</label>
                <input type="text" class="form-control" [(ngModel)]="newUser.username" 
                       name="username" required>
              </div>
              <div class="col-md-4 mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" [(ngModel)]="newUser.email" 
                       name="email" required>
              </div>
              <div class="col-md-4 mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" [(ngModel)]="newUser.password" 
                       name="password" required>
              </div>
            </div>
            <button type="submit" class="btn btn-primary" [disabled]="!userForm.form.valid">
              Add User
            </button>
          </form>
        </div>
      </div>

      <!-- Search Users -->
      <div class="card mb-4">
        <div class="card-header">
          <h5>Search Users</h5>
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-md-4 mb-3">
              <label class="form-label">Username</label>
              <input type="text" class="form-control" [(ngModel)]="searchFilter.username" 
                     placeholder="Search by username">
            </div>
            <div class="col-md-4 mb-3">
              <label class="form-label">Email</label>
              <input type="email" class="form-control" [(ngModel)]="searchFilter.email" 
                     placeholder="Search by email">
            </div>
            <div class="col-md-4 mb-3 d-flex align-items-end">
              <button class="btn btn-primary me-2" (click)="searchUsers()">Search</button>
              <button class="btn btn-secondary me-2" (click)="clearSearch()">Clear</button>
              <button class="btn btn-info" (click)="loadAllUsers()">Load All</button>
            </div>
          </div>
        </div>
      </div>

      <!-- Users Table -->
      <div class="card">
        <div class="card-header">
          <h5>Users ({{users.length}})</h5>
        </div>
        <div class="card-body">
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let user of users">
                  <td>{{user.username}}</td>
                  <td>{{user.email}}</td>
                  <td>
                    <button class="btn btn-sm btn-danger" (click)="deleteUser(user)">
                      Delete
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          
          <div *ngIf="users.length === 0" class="text-center mt-3">
            <p class="text-muted">No users found.</p>
          </div>
        </div>
      </div>
    </div>
  `
})
export class UserManagementComponent implements OnInit {
  users: User[] = [];
  newUser: User = { username: '', email: '', password: '' };
  searchFilter: UserSearchFilter = {};

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.loadAllUsers();
  }

  saveUser() {
    this.apiService.saveUser(this.newUser).subscribe({
      next: (user) => {
        console.log('User saved:', user);
        this.newUser = { username: '', email: '', password: '' };
        this.loadAllUsers();
        alert('User added successfully!');
      },
      error: (error) => {
        console.error('Error saving user:', error);
        alert('Error adding user. Please try again.');
      }
    });
  }

  loadAllUsers() {
    this.apiService.getUsers().subscribe({
      next: (users) => this.users = users,
      error: (error) => console.error('Error loading users:', error)
    });
  }

  searchUsers() {
    this.apiService.searchUsers(this.searchFilter).subscribe({
      next: (users) => this.users = users,
      error: (error) => console.error('Error searching users:', error)
    });
  }

  clearSearch() {
    this.searchFilter = {};
    this.loadAllUsers();
  }

  deleteUser(user: User) {
    if (confirm(`Are you sure you want to delete user ${user.username}?`)) {
      // Note: We need the user ID for deletion, but it's not in the User model
      // This is a limitation we'll need to address in the backend
      console.log('Delete functionality needs user ID from backend');
      alert('Delete functionality requires user ID. Please check backend response.');
    }
  }
}