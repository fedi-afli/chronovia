import React from 'react';
import { X, ShoppingCart, Minus, Plus, Trash2 } from 'lucide-react';
import type { Cart as CartType } from '../../types';

interface CartProps {
    cart: CartType | null;
    isOpen: boolean;
    onClose: () => void;
    loading: boolean;
    onCheckout: () => void;
}

const Cart: React.FC<CartProps> = ({ cart, isOpen, onClose, loading, onCheckout }) => {
    if (!isOpen) return null;

    const formatPrice = (price: number) =>
        new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(price);

    return (
        <>
            {/* Backdrop */}
            <div
                className="fixed inset-0 bg-black bg-opacity-50 z-50"
                onClick={onClose}
            />

            {/* Cart Sidebar */}
            <div className="fixed right-0 top-0 h-full w-96 bg-white shadow-xl z-50 flex flex-col">
                {/* Header */}
                <div className="flex items-center justify-between p-4 border-b">
                    <h2 className="text-lg font-semibold flex items-center">
                        <ShoppingCart className="w-5 h-5 mr-2" />
                        Shopping Cart
                    </h2>
                    <button
                        onClick={onClose}
                        className="p-2 hover:bg-gray-100 rounded-full transition-colors"
                    >
                        <X className="w-5 h-5" />
                    </button>
                </div>

                {/* Content */}
                <div className="flex-1 overflow-y-auto">
                    {loading ? (
                        <div className="flex items-center justify-center py-20">
                            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
                            <span className="ml-2 text-gray-600">Loading cart...</span>
                        </div>
                    ) : !cart || cart.cartItems.length === 0 ? (
                        <div className="flex flex-col items-center justify-center py-20 text-center">
                            <ShoppingCart className="w-16 h-16 text-gray-300 mb-4" />
                            <h3 className="text-lg font-medium text-gray-900 mb-2">Your cart is empty</h3>
                            <p className="text-gray-500">Add some watches to get started</p>
                        </div>
                    ) : (
                        <div className="p-4 space-y-4">
                            {cart.cartItems.map((item, index) => (
                                <div key={`${item.watchId}-${index}`} className="flex space-x-4 bg-gray-50 rounded-lg p-4">
                                    {/* Watch Image Placeholder */}
                                    <div className="w-16 h-16 bg-gray-200 rounded-lg flex items-center justify-center">
                                        <ShoppingCart className="w-6 h-6 text-gray-400" />
                                    </div>

                                    {/* Watch Details */}
                                    <div className="flex-1">
                                        {item.watch ? (
                                            <>
                                                <h4 className="font-medium text-gray-900">{item.watch.modelName}</h4>
                                                <p className="text-sm text-gray-500">{item.watch.brandName}</p>
                                                <p className="text-xs text-gray-400">Ref: {item.watch.referenceNumber}</p>
                                            </>
                                        ) : (
                                            <p className="text-sm text-gray-500">Watch ID: {item.watchId}</p>
                                        )}

                                        <div className="flex items-center justify-between mt-2">
                                            <div className="flex items-center space-x-2">
                                                <button className="p-1 hover:bg-gray-200 rounded">
                                                    <Minus className="w-4 h-4" />
                                                </button>
                                                <span className="text-sm font-medium">{item.quantity}</span>
                                                <button className="p-1 hover:bg-gray-200 rounded">
                                                    <Plus className="w-4 h-4" />
                                                </button>
                                            </div>

                                            <div className="flex items-center space-x-2">
                                                <span className="font-medium">{formatPrice(item.price)}</span>
                                                <button className="p-1 hover:bg-red-100 text-red-600 rounded">
                                                    <Trash2 className="w-4 h-4" />
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>

                {/* Footer */}
                {cart && cart.cartItems.length > 0 && (
                    <div className="border-t p-4 space-y-4">
                        <div className="flex justify-between items-center">
                            <span className="text-lg font-semibold">Total:</span>
                            <span className="text-xl font-bold text-blue-600">{formatPrice(cart.total)}</span>
                        </div>

                        <button
                            onClick={onCheckout}
                            disabled={loading}
                            className="w-full bg-blue-600 text-white py-3 px-4 rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                        >
                            {loading ? 'Processing...' : 'Proceed to Checkout'}
                        </button>
                    </div>
                )}
            </div>
        </>
    );
};

export default Cart;