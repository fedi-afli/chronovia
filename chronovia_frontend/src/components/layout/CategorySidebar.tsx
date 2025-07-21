import React from 'react';
import { Clock, Zap, Settings, Star, TrendingUp, Award } from 'lucide-react';

interface CategorySidebarProps {
    category: 'all' | 'MECHANICAL' | 'QUARTZ';
    setCategory: (category: 'all' | 'MECHANICAL' | 'QUARTZ') => void;

    quickFilters: string[];
    setQuickFilters: (filters: string[]) => void;

    priceRanges: string[];
    setPriceRanges: (ranges: string[]) => void;

    brands: string[];
    setBrands: (brands: string[]) => void;
}

const CategorySidebar: React.FC<CategorySidebarProps> = ({ category, setCategory }) => {
    const categories = [
        { id: 'all' as const, name: 'All Watches', icon: Clock, count: 156 },
        { id: 'MECHANICAL' as const, name: 'Mechanical', icon: Settings, count: 89 },
        { id: 'QUARTZ' as const, name: 'Quartz', icon: Zap, count: 67 },
    ];

    const filters = [
        { name: 'Featured', icon: Star },
        { name: 'Best Sellers', icon: TrendingUp },
        { name: 'Premium', icon: Award },
    ];

    const priceRanges = [
        { label: 'Under $500', min: 0, max: 500 },
        { label: '$500 - $1,000', min: 500, max: 1000 },
        { label: '$1,000 - $2,500', min: 1000, max: 2500 },
        { label: '$2,500 - $5,000', min: 2500, max: 5000 },
        { label: 'Over $5,000', min: 5000, max: Infinity },
    ];

    const brands = [
        'Rolex', 'Omega', 'Seiko', 'Casio', 'TAG Heuer',
        'Breitling', 'Tissot', 'Citizen', 'Hamilton'
    ];

    return (
        <div className="h-full bg-white overflow-y-auto">
            <div className="p-4">
                {/* Logo/Title */}
                <div className="mb-6">
                    <h2 className="text-base font-semibold text-gray-900 mb-1">Filters</h2>
                    <p className="text-xs text-gray-500">Find your perfect timepiece</p>
                </div>

                {/* Categories */}
                <div className="mb-6">
                    <h3 className="text-xs font-medium text-gray-900 mb-3">Categories</h3>
                    <div className="space-y-1">
                        {categories.map((cat) => {
                            const Icon = cat.icon;
                            return (
                                <button
                                    key={cat.id}
                                    onClick={() => setCategory(cat.id)}
                                    className={`w-full flex items-center justify-between p-2 rounded-md text-left transition-all ${
                                        category === cat.id
                                            ? 'bg-blue-50 text-blue-700 border border-blue-200'
                                            : 'text-gray-700 hover:bg-gray-50'
                                    }`}
                                >
                                    <div className="flex items-center space-x-2">
                                        <Icon className="w-3 h-3" />
                                        <span className="text-xs font-medium">{cat.name}</span>
                                    </div>
                                    <span className="text-xs text-gray-500">{cat.count}</span>
                                </button>
                            );
                        })}
                    </div>
                </div>

                {/* Quick Filters */}
                <div className="mb-6">
                    <h3 className="text-xs font-medium text-gray-900 mb-3">Quick Filters</h3>
                    <div className="space-y-1">
                        {filters.map((filter) => {
                            const Icon = filter.icon;
                            return (
                                <button
                                    key={filter.name}
                                    className="w-full flex items-center space-x-2 p-2 text-left text-gray-700 hover:bg-gray-50 rounded-md transition-colors"
                                >
                                    <Icon className="w-3 h-3" />
                                    <span className="text-xs">{filter.name}</span>
                                </button>
                            );
                        })}
                    </div>
                </div>

                {/* Price Range */}
                <div className="mb-6">
                    <h3 className="text-xs font-medium text-gray-900 mb-3">Price Range</h3>
                    <div className="space-y-1">
                        {priceRanges.map((range) => (
                            <label key={range.label} className="flex items-center space-x-2 cursor-pointer">
                                <input
                                    type="checkbox"
                                    className="rounded border-gray-300 text-blue-600 focus:ring-blue-500 w-3 h-3"
                                />
                                <span className="text-xs text-gray-700">{range.label}</span>
                            </label>
                        ))}
                    </div>
                </div>

                {/* Brands */}
                <div className="mb-6">
                    <h3 className="text-xs font-medium text-gray-900 mb-3">Brands</h3>
                    <div className="space-y-1 max-h-32 overflow-y-auto">
                        {brands.map((brand) => (
                            <label key={brand} className="flex items-center space-x-2 cursor-pointer">
                                <input
                                    type="checkbox"
                                    className="rounded border-gray-300 text-blue-600 focus:ring-blue-500 w-3 h-3"
                                />
                                <span className="text-xs text-gray-700">{brand}</span>
                            </label>
                        ))}
                    </div>
                </div>

                {/* Clear Filters */}
                <button className="w-full py-2 px-3 text-xs text-gray-600 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
                    Clear All Filters
                </button>
            </div>
        </div>
    );
};

export default CategorySidebar;