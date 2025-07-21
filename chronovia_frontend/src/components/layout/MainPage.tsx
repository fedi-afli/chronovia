import React, { useEffect, useState } from 'react';
import Header from './Header';
import CategorySidebar from './CategorySidebar';
import WatchGrid from './watches/WatchGrid';
import Cart from './Cart';
import { watchAPI } from '../../services/api';
import { useCart } from '../../hooks/useCart';
import type { Watch } from '../../types';

const MainPage: React.FC = () => {
    const [watches, setWatches] = useState<Watch[]>([]);
    const [filteredWatches, setFilteredWatches] = useState<Watch[]>([]);
    const [searchQuery, setSearchQuery] = useState('');
    const [selectedCategory, setSelectedCategory] = useState<'all' | 'MECHANICAL' | 'QUARTZ'>('all');
    const [quickFilters, setQuickFilters] = useState<string[]>([]);
    const [priceRanges, setPriceRanges] = useState<string[]>([]);
    const [brands, setBrands] = useState<string[]>([]);
    const [isCartOpen, setIsCartOpen] = useState(false);
    const [watchesLoading, setWatchesLoading] = useState(false);
    const [watchesError, setWatchesError] = useState<string | null>(null);

    const {
        cart,
        loading: cartLoading,
    } = useCart();

    useEffect(() => {
        const fetchWatches = async () => {
            try {
                setWatchesLoading(true);
                const data = await watchAPI.getAllWatches();
                setWatches(data);
            } catch (error) {
                setWatchesError('Failed to load watches. Please check your connection and try again.'+error);
            } finally {
                setWatchesLoading(false);
            }
        };
        fetchWatches();
    }, []);

    useEffect(() => {
        console.log('Filtering watches. Total watches:', watches.length);
        console.log('Selected category:', selectedCategory);
        console.log('Search query:', searchQuery);


        const filtered = watches.filter((watch) => {
            const matchCategory = selectedCategory === 'all' || watch.watchType === selectedCategory;

            const matchSearch =
                watch.modelName.toLowerCase().includes(searchQuery.toLowerCase()) ||
                watch.brandName.toLowerCase().includes(searchQuery.toLowerCase()) ||
                watch.referenceNumber.toLowerCase().includes(searchQuery.toLowerCase());

            return matchCategory && matchSearch;
        });

        console.log('Filtered watches:', filtered.length);
        setFilteredWatches(filtered);
    }, [searchQuery, selectedCategory, watches]);

    const handleSearchChange = (query: string) => {
        setSearchQuery(query);
    };

    const handleCategoryChange = (category: 'all' | 'MECHANICAL' | 'QUARTZ') => {
        setSelectedCategory(category);
    };

    const handleCheckout = () => {
        alert('Checkout feature coming soon!');
    };

    // Show error state if watches failed to load
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
        <div className="min-h-screen bg-gray-50">
            {/* Header - Full width, always visible */}
            <Header
                cartItemCount={cart ? cart.cartItems.reduce((sum, item) => sum + item.quantity, 0) : 0}
                onCartClick={() => setIsCartOpen(true)}
                searchQuery={searchQuery}
                onSearchChange={handleSearchChange}
            />

            {/* Content area below header */}
            <div className="flex">
                {/* Sidebar - Fixed position, smaller width, starts below header */}
                <div className="w-64 fixed left-0 top-16 h-[calc(100vh-4rem)] border-r border-gray-200 bg-white z-20 overflow-y-auto">
                    <CategorySidebar
                        category={selectedCategory}
                        setCategory={handleCategoryChange}
                        quickFilters={quickFilters}
                        setQuickFilters={setQuickFilters}
                        priceRanges={priceRanges}
                        setPriceRanges={setPriceRanges}
                        brands={brands}
                        setBrands={setBrands}
                    />
                </div>

                {/* Main content - Offset by sidebar width */}
                <div className="flex-1 ml-64">
                    <main className="p-6">
                        {/* Stats */}
                        <div className="mb-6">
                            <h1 className="text-2xl font-bold text-gray-900 mb-2">Premium Timepieces</h1>
                            <p className="text-gray-600">
                                Showing {filteredWatches.length} of {watches.length} watches
                                {searchQuery && ` for "${searchQuery}"`}
                                {selectedCategory !== 'all' && ` in ${selectedCategory.toLowerCase()} category`}
                            </p>
                        </div>

                        <WatchGrid
                            watches={filteredWatches}
                            loading={watchesLoading}
                            error={watchesError}
                        />
                    </main>
                </div>
            </div>

            <Cart
                cart={cart}
                isOpen={isCartOpen}
                onClose={() => setIsCartOpen(false)}
                loading={cartLoading}
                onCheckout={handleCheckout}
            />
        </div>
    );
};

export default MainPage;