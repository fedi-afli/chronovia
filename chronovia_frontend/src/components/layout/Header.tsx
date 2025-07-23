import React from 'react';
import { Search, ShoppingBag, User, Heart, Bell } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import logo from '../../assets/favicon.png'; // ✅ Your logo

interface HeaderProps {
    cartItemCount: number;
    onCartClick: () => void;
    searchQuery: string;
    onSearchChange: (query: string) => void;
}

const Header: React.FC<HeaderProps> = ({
                                           cartItemCount,
                                           onCartClick,
                                           searchQuery,
                                           onSearchChange,
                                       }) => {
    const { user, signOut } = useAuth();

    return (
        <header className="bg-white border-b border-gray-200 sticky top-0 z-30">
            <div className="px-6 py-4">
                <div className="flex items-center justify-between">
                    {/* Logo + Title */}
                    <div className="flex items-center space-x-3">
                        <img src={logo} alt="Chronova Logo" className="w-19 h-19 rounded" />
                        <h1 className="text-xl font-bold text-gray-900">Chronova Store</h1>
                    </div>

                    {/* Search Bar */}
                    <div className="flex-1 max-w-2xl mx-8">
                        <div className="relative">
                            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
                            <input
                                type="text"
                                placeholder="Search for watches, brands, or categories..."
                                value={searchQuery}
                                onChange={(e) => onSearchChange(e.target.value)}
                                className="w-full pl-10 pr-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                            />
                        </div>
                    </div>

                    {/* Right Actions */}
                    <div className="flex items-center space-x-4">
                        {/* Notifications */}
                        <button className="p-2 text-gray-600 hover:text-gray-900 hover:bg-gray-100 rounded-lg transition-colors relative">
                            <Bell className="w-5 h-5" />
                            <span className="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full"></span>
                        </button>

                        {/* Wishlist */}
                        <button className="p-2 text-gray-600 hover:text-gray-900 hover:bg-gray-100 rounded-lg transition-colors">
                            <Heart className="w-5 h-5" />
                        </button>

                        {/* Cart */}
                        <button
                            onClick={onCartClick}
                            className="p-2 text-gray-600 hover:text-gray-900 hover:bg-gray-100 rounded-lg transition-colors relative"
                        >
                            <ShoppingBag className="w-5 h-5" />
                            {cartItemCount > 0 && (
                                <span className="absolute -top-1 -right-1 bg-blue-600 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center font-medium">
                  {cartItemCount > 99 ? '99+' : cartItemCount}
                </span>
                            )}
                        </button>

                        {/* User Dropdown */}
                        <div className="relative group">
                            <button className="flex items-center space-x-2 p-2 text-gray-600 hover:text-gray-900 hover:bg-gray-100 rounded-lg transition-colors">
                                <User className="w-5 h-5" />
                                {user && (
                                    <span className="text-sm font-medium hidden md:block">
                    {user.email?.split('@')[0]}
                  </span>
                                )}
                            </button>

                            {/* Dropdown menu */}
                            <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-200 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 z-20">
                                <div className="py-2">
                                    {user ? (
                                        <>
                                            <div className="px-4 py-2 border-b border-gray-100">
                                                <p className="text-sm font-medium text-gray-900">{user.email}</p>
                                                <p className="text-xs text-gray-500 capitalize">{user.role}</p>
                                            </div>
                                            <a href="/dashboard" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                                Dashboard
                                            </a>
                                            <a href="/orders" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                                My Orders
                                            </a>
                                            <a href="/profile" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                                Profile Settings
                                            </a>
                                            <hr className="my-1" />
                                            <button
                                                onClick={signOut}
                                                className="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50"
                                            >
                                                Sign Out
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                            <a href="/" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                                Sign In
                                            </a>
                                            <a href="/signup" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                                Sign Up
                                            </a>
                                        </>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    );
};

export default Header;
