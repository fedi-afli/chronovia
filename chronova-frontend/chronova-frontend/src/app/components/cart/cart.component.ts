import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { Cart, Order } from '../../models/cart.model';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <h2 class="mb-4">Shopping Cart</h2>
      
      <div *ngIf="cart" class="card">
        <div class="card-header">
          <h5>Cart Items</h5>
        </div>
        <div class="card-body">
          <div *ngIf="cart.cartLigns.length === 0" class="text-center">
            <p class="text-muted">Your cart is empty.</p>
          </div>
          
          <div *ngIf="cart.cartLigns.length > 0">
            <div class="row mb-3" *ngFor="let item of cart.cartLigns">
              <div class="col-md-8">
                <h6>Watch ID: {{item.watchId}}</h6>
                <p class="text-muted">Quantity: {{item.quantity}}</p>
              </div>
              <div class="col-md-4 text-end">
                <!-- Price would be calculated on backend -->
              </div>
            </div>
            
            <hr>
            
            <div class="row">
              <div class="col-md-8">
                <h5>Total:</h5>
              </div>
              <div class="col-md-4 text-end">
                <h5 class="text-success">\${{cart.total | number:'1.2-2'}}</h5>
              </div>
            </div>
            
            <div class="mt-3">
              <button class="btn btn-success btn-lg" (click)="confirmOrder()">
                Confirm Order
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Orders History -->
      <div class="card mt-4">
        <div class="card-header">
          <h5>Order History</h5>
        </div>
        <div class="card-body">
          <div *ngIf="orders.length === 0" class="text-center">
            <p class="text-muted">No orders found.</p>
          </div>
          
          <div *ngIf="orders.length > 0">
            <div class="table-responsive">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Order Date</th>
                    <th>Total</th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let order of orders">
                    <td>{{order.orderDate | date:'medium'}}</td>
                    <td class="text-success">\${{order.total | number:'1.2-2'}}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class CartComponent implements OnInit {
  cart: Cart | null = null;
  orders: Order[] = [];
  userId = 1; // Hardcoded for demo - would come from auth in real app

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.loadCart();
    this.loadOrders();
  }

  loadCart() {
    this.apiService.getCart(this.userId).subscribe({
      next: (cart) => this.cart = cart,
      error: (error) => console.error('Error loading cart:', error)
    });
  }

  loadOrders() {
    this.apiService.getUserOrders(this.userId).subscribe({
      next: (orders) => this.orders = orders,
      error: (error) => console.error('Error loading orders:', error)
    });
  }

  confirmOrder() {
    this.apiService.confirmCart(this.userId).subscribe({
      next: (order) => {
        console.log('Order confirmed:', order);
        alert('Order confirmed successfully!');
        this.loadCart(); // Reload cart (should be empty now)
        this.loadOrders(); // Reload orders to show new order
      },
      error: (error) => {
        console.error('Error confirming order:', error);
        alert('Error confirming order. Please try again.');
      }
    });
  }
}