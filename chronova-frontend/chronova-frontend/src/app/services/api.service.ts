import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User, UserSearchFilter } from '../models/user.model';
import { Watch, WatchSearchFilter } from '../models/watch.model';
import { Cart, CartItem, Order } from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // User endpoints
  saveUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/save/user`, user);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/get/user`);
  }

  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/search/user/${userId}`);
  }

  searchUsers(filter: UserSearchFilter): Observable<User[]> {
    return this.http.post<User[]>(`${this.baseUrl}/search/users`, filter);
  }

  deleteUser(userId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/delete/user/${userId}`);
  }

  // Watch endpoints
  saveWatch(watch: Watch): Observable<Watch> {
    return this.http.post<Watch>(`${this.baseUrl}/save/watch`, watch);
  }

  getWatches(): Observable<Watch[]> {
    return this.http.get<Watch[]>(`${this.baseUrl}/get/watch`);
  }

  getWatchById(watchId: number): Observable<Watch> {
    return this.http.get<Watch>(`${this.baseUrl}/get/watch/${watchId}`);
  }

  searchWatches(filter: WatchSearchFilter): Observable<Watch[]> {
    return this.http.post<Watch[]>(`${this.baseUrl}/search/watch`, filter);
  }

  deleteWatch(watchId: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.baseUrl}/delete/watch/${watchId}`);
  }

  // Cart endpoints
  getCart(userId: number): Observable<Cart> {
    return this.http.get<Cart>(`${this.baseUrl}/get/cart/${userId}`);
  }

  addToCart(userId: number, cartItem: CartItem): Observable<Cart> {
    return this.http.post<Cart>(`${this.baseUrl}/add/product/${userId}`, cartItem);
  }

  // Order endpoints
  confirmOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.baseUrl}/order/confirm`, order);
  }

  getUserOrders(userId: number): Observable<Order[]> {
    return this.http.post<Order[]>(`${this.baseUrl}/orders/${userId}`, {});
  }

  confirmCart(userId: number): Observable<Order> {
    return this.http.get<Order>(`${this.baseUrl}/order/confirm/${userId}`);
  }
}