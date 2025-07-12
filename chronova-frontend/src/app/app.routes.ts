import { Routes } from '@angular/router';
import { WatchListComponent } from './components/watch-list/watch-list.component';
import { UserManagementComponent } from './components/user-management/user-management.component';
import { CartComponent } from './components/cart/cart.component';

export const routes: Routes = [
  { path: '', redirectTo: '/watches', pathMatch: 'full' },
  { path: 'watches', component: WatchListComponent },
  { path: 'users', component: UserManagementComponent },
  { path: 'cart', component: CartComponent },
  { path: '**', redirectTo: '/watches' }
];