export interface CartItem {
  quantity: number;
  watchId: number;
}

export interface Cart {
  cartLigns: CartItem[];
  total: number;
  userID: number;
}

export interface Order {
  userId: number;
  orderDate: string;
  total: number;
}