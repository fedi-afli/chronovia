import { useState, useEffect } from 'react';
import type { Cart, CartLignRequestDTO } from '../types';
import { cartAPI } from '../services/api';
import { useAuth } from './useAuth';

export const useCart = () => {
  const [cart, setCart] = useState<Cart | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { user } = useAuth();

  useEffect(() => {
    if (user) {
      loadCart();
    } else {
      setCart(null);
    }
  }, [user]);

  const loadCart = async () => {
    if (!user) return;

    setLoading(true);
    setError(null);

    try {
      const cartData = await cartAPI.getCart(user.id);
      setCart(cartData);
    } catch (err) {
      setError('Failed to load cart');
    } finally {
      setLoading(false);
    }
  };

  const addToCart = async (watchId: number, quantity: number = 1) => {
    if (!user) {
      setError('Please login to add items to cart');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const cartItem: CartLignRequestDTO = { watchId, quantity };
      const updatedCart = await cartAPI.addToCart(user.id, cartItem);
      setCart(updatedCart);
    } catch {
      setError('Failed to add item to cart');
    } finally {
      setLoading(false);
    }
  };

  const removeItem = async (watchId: number) => {
    if (!user) return;

    setLoading(true);
    setError(null);

    try {
      const updatedCart = await cartAPI.removeItem(user.id, watchId);
      setCart(updatedCart);
    } catch {
      setError('Failed to remove item');
    } finally {
      setLoading(false);
    }
  };

  const changeQuantity = async (watchId: number, quantity: number) => {
    if (!user) return;

    setLoading(true);
    setError(null);

    try {
      const updatedCart = await cartAPI.changeQuantity(user.id, watchId, quantity);
      setCart(updatedCart);
    } catch {
      setError('Failed to update quantity');
    } finally {
      setLoading(false);
    }
  };

  const getTotalItems = () => {
    return cart?.cartItems.reduce((total, item) => total + item.quantity, 0) || 0;
  };

  const getTotalPrice = () => {
    return cart?.total || 0;
  };

  return {
    cart,
    loading,
    error,
    addToCart,
    removeItem,
    changeQuantity,
    getTotalItems,
    getTotalPrice,
    loadCart,
  };
};
