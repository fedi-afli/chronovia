import React, { useEffect, useState } from 'react';
import { useCart } from '../../hooks/useCart';
import {watchAPI, orderAPI, authAPI, cartAPI} from '../../services/api';  // import orderAPI too
import type { Watch, User } from '../../types';
import emptyCartImg from '../../assets/emptycart.png';
interface CartProps {
    isOpen: boolean;
    onClose: () => void;
}

const Cart: React.FC<CartProps> = ({ isOpen, onClose }) => {
    const { cart, loading, error, getTotalItems, getTotalPrice } = useCart();

    const [pictures, setPictures] = useState<Record<number, string>>({});
    const [watchDetailsMap, setWatchDetailsMap] = useState<Record<number, Watch>>({});
    const [loadingDetails, setLoadingDetails] = useState(false);

    // Get current user from token
    const user: User | null = authAPI.getCurrentUser();

    useEffect(() => {
        if (!cart || cart.cartItems.length === 0) return;

        let isMounted = true;

        const fetchDetailsAndPictures = async () => {
            setLoadingDetails(true);

            const pictureMap: Record<number, string> = {};
            for (const item of cart.cartItems) {
                try {
                    const urls = await watchAPI.getWatchPictures(item.watchId);
                    if (urls && urls.length > 0) pictureMap[item.watchId] = urls[0];
                } catch (err) {
                    console.error(`Failed to load picture for watch ${item.watchId}`, err);
                }
            }

            const detailsMap: Record<number, Watch> = {};
            for (const item of cart.cartItems) {
                try {
                    const watch = await watchAPI.getWatchById(item.watchId);
                    detailsMap[item.watchId] = watch;
                } catch (err) {
                    console.error(`Failed to load details for watch ${item.watchId}`, err);
                }
            }

            if (isMounted) {
                setPictures(pictureMap);
                setWatchDetailsMap(detailsMap);
                setLoadingDetails(false);
            }
        };

        fetchDetailsAndPictures();

        return () => {
            isMounted = false;
        };
    }, [cart]);

    const handleCheckout = async () => {
        if (!user || !user.id) {
            alert("You must be logged in to checkout.");
            return;
        }
        try {
            // Call backend endpoint to confirm cart and send receipt email
            await orderAPI.confirmCart(user.id);
            alert("Order confirmed! Receipt sent to your email.");
            onClose();
            // optionally clear cart here if you have a method
        } catch (error) {
            console.error("Checkout failed:", error);
            alert("Failed to confirm order. Please try again.");
        }
    };

    if (!isOpen) return null;

    if (loading || loadingDetails) return <div className="p-6">Loading...</div>;

    if (error) return <div className="p-6 text-red-600">{error}</div>;

    if (!cart || cart.cartItems.length === 0) {
        return (
            <div className="flex flex-col items-center justify-center p-10 text-center text-gray-600">
                <img
                    src={emptyCartImg}
                    alt="Empty Cart"
                    className="w-56 h-56 mb-6 object-contain rounded-xl shadow-sm"
                />
                <h2 className="text-2xl font-semibold mb-2">Your cart is empty</h2>
                <p className="mb-6 text-sm text-gray-500">
                    Looks like you haven’t added anything to your cart yet.
                </p>
                <button
                    onClick={onClose}
                    className="px-6 py-2 bg-blue-600 text-white font-medium rounded-lg hover:bg-blue-700 transition"
                >
                    Continue Shopping
                </button>
            </div>
        );
    }


    return (
        <div className="p-6 max-w-7xl mx-auto">
            <div className="flex justify-end mb-4">
                <button
                    onClick={onClose}
                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400 transition"
                    aria-label="Close cart"
                >
                    Close Cart
                </button>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                {/* Watch list */}
                <div className="lg:col-span-2 space-y-4">
                    {cart.cartItems.map((item) => {
                        const watch = watchDetailsMap[item.watchId];

                        const handleQuantityChange = async (newQty: number) => {
                            if (!user) return;
                            if (newQty <= 0) return;

                            try {
                                await cartAPI.updateQuantity(user.id, {
                                    watchId: item.watchId,
                                    quantity: newQty,
                                });
                                window.location.reload(); // or refetch cart if you have a method
                            } catch (error) {
                                alert("Failed to update quantity.");
                                console.error(error);
                            }
                        };

                        const handleRemove = async () => {
                            if (!user) return;

                            try {
                                await cartAPI.removeItem(user.id, {
                                    watchId: item.watchId,
                                    quantity: item.quantity,
                                });
                                window.location.reload(); // or refetch cart if you have a method
                            } catch (error) {
                                alert("Failed to remove item.");
                                console.error(error);
                            }
                        };

                        return (
                            <div key={item.watchId} className="flex items-start gap-4 bg-white shadow rounded-xl p-4">
                                <img
                                    src={pictures[item.watchId] || '/placeholder.jpg'}
                                    alt={watch?.modelName || 'Watch Image'}
                                    className="w-24 h-24 object-cover rounded-lg border"
                                />
                                <div className="flex-1">
                                    <h3 className="text-lg font-semibold">
                                        {watch?.brandName || 'Unknown Brand'} – {watch?.modelName || 'Unnamed Model'}
                                    </h3>
                                    <div className="mt-2 text-sm text-gray-500">
                                        Material: {watch?.watchMaterial || 'N/A'} | Type: {watch?.watchType || 'N/A'}
                                    </div>

                                    {/* Quantity change */}
                                    <div className="mt-3 flex items-center gap-2">
                                        <span className="text-sm">Qty:</span>
                                        <input
                                            type="number"
                                            min="1"
                                            value={item.quantity}
                                            onChange={(e) => handleQuantityChange(Number(e.target.value))}
                                            className="w-16 px-2 py-1 border rounded text-sm"
                                        />
                                    </div>
                                </div>

                                {/* Price + Delete */}
                                <div className="flex flex-col items-end justify-between h-full">
                                    <div className="text-sm font-medium">${watch?.price?.toFixed(2) ?? 'N/A'}</div>
                                    <button
                                        onClick={handleRemove}
                                        className="text-red-500 hover:text-red-700 text-sm mt-2"
                                        aria-label="Remove from cart"
                                    >
                                        ❌
                                    </button>
                                </div>
                            </div>
                        );
                    })}

                </div>

                {/* Total & Checkout */}
                <div className="bg-white shadow rounded-xl p-6 h-fit">
                    <h2 className="text-xl font-bold mb-4">Summary</h2>
                    <div className="flex justify-between text-gray-700 mb-2">
                        <span>Total Items:</span>
                        <span>{getTotalItems()}</span>
                    </div>
                    <div className="flex justify-between text-gray-800 font-semibold text-lg">
                        <span>Total:</span>
                        <span>${getTotalPrice().toFixed(2)}</span>
                    </div>
                    <button
                        onClick={handleCheckout}
                        className="mt-6 w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-xl"
                    >
                        Checkout
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Cart;
