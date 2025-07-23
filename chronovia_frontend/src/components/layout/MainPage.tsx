import React, { useEffect, useState } from 'react';
import Header from './Header';
import CategorySidebar from './CategorySidebar';
import WatchGrid from '../watches/WatchGrid';
import Cart from './Cart';
import { watchAPI } from '../../services/api';
import { useCart } from '../../hooks/useCart';
import type { Watch } from '../../types';

const MainPage: React.FC = () => {
    const [watches, setWatches] = useState<Watch[]>([]);
    const [filteredWatches, setFilteredWatches] = useState<Watch[]>([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [selectedCategory, setSelectedCategory] = useState<'all' | 'MECHANICAL' | 'QUARTZ'>('all');
    const [isCartOpen, setIsCartOpen] = useState(false);
    const [watchesLoading, setWatchesLoading] = useState(false);
    const [watchesError, setWatchesError] = useState<string | null>(null);

    const { cart} = useCart();

    // Fetch watches on mount
    useEffect(() => {
        const fetchWatches = async () => {
            try {
                setWatchesLoading(true);
                const data = await watchAPI.getAllWatches();
                setWatches(data);
            } catch (error) {
                setWatchesError('Failed to load watches. Please check your connection and try again.');
            } finally {
                setWatchesLoading(false);
            }
        };
        fetchWatches();
    }, []);

    // Filter watches on changes
    useEffect(() => {
        const filtered = watches.filter((watch) => {
            const matchCategory = selectedCategory === 'all' || watch.watchType === selectedCategory;
            const matchSearch =
                watch.modelName.toLowerCase().includes(searchQuery.toLowerCase()) ||
                watch.brandName.toLowerCase().includes(searchQuery.toLowerCase()) ||
                watch.referenceNumber.toLowerCase().includes(searchQuery.toLowerCase());

            return matchCategory && matchSearch;
        });
        setFilteredWatches(filtered);
    }, [searchQuery, selectedCategory, watches]);

    const handleSearchChange = (query: string) => setSearchQuery(query);
    const handleCategoryChange = (category: 'all' | 'MECHANICAL' | 'QUARTZ') => setSelectedCategory(category);

    const handleCheckout = () => alert('Checkout feature coming soon!');

    // Error state for watches loading failure
    if (watchesError && !watchesLoading) {
        return (
            <div className="min-h-screen flex items-center justify-center bg-gray-50">
                <div className="text-center">
                    <div className="text-red-500 text-xl mb-4">⚠️ Error Loading Watches</div>
                    <p className="text-gray-600 mb-4">{watchesError}</p>
                    <button
                        onClick={() => window.location.reload()}
                        className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                    >
                        Try Again
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50 relative">
            <Header
                cartItemCount={cart ? cart.cartItems.reduce((sum, item) => sum + item.quantity, 0) : 0}
                onCartClick={() => setIsCartOpen(true)}
                searchQuery={searchQuery}
                onSearchChange={handleSearchChange}
            />

            <div className="flex">
                <div className="w-64 fixed left-0 top-16 h-[calc(100vh-4rem)] border-r border-gray-200 bg-white z-20 overflow-y-auto">
                    <CategorySidebar
                        category={selectedCategory}
                        setCategory={handleCategoryChange}
                        quickFilters={[]}
                        setQuickFilters={() => {}}
                        priceRanges={[]}
                        setPriceRanges={() => {}}
                        brands={[]}
                        setBrands={() => {}}
                    />
                </div>

                <div className="flex-1 ml-64">
                    <main className="p-6">
                        <h1 className="text-2xl font-bold text-gray-900 mb-2">Premium Timepieces</h1>
                        <p className="text-gray-600 mb-6">
                            Showing {filteredWatches.length} of {watches.length} watches
                            {searchQuery && ` for "${searchQuery}"`}
                            {selectedCategory !== 'all' && ` in ${selectedCategory.toLowerCase()} category`}
                        </p>

                        <WatchGrid watches={filteredWatches} loading={watchesLoading} error={watchesError} />
                    </main>
                </div>
            </div>

            {/* Cart drawer overlay */}
            {isCartOpen && (
                <>
                    {/* Backdrop */}
                    <div
                        className="fixed inset-0 bg-black bg-opacity-40 z-40"
                        onClick={() => setIsCartOpen(false)}
                    />

                    {/* Cart drawer */}
                    <div
                        className="fixed top-16 left-0 right-0 max-w-5xl mx-auto bg-white shadow-lg rounded-b-xl z-50 transform transition-transform duration-300"
                        style={{ animation: 'slideDown 0.3s ease forwards' }}
                    >
                        <Cart
                            isOpen={true}
                            onClose={() => setIsCartOpen(false)}
                           // onCheckout={handleCheckout}
                        />
                    </div>
                </>
            )}

            <style>
                {`
          @keyframes slideDown {
            from { transform: translateY(-100%); }
            to { transform: translateY(0); }
          }
        `}
            </style>
        </div>
    );
};

export default MainPage;
