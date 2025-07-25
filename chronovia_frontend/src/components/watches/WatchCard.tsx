import React, { useState, useEffect, useCallback } from 'react';
import { Heart, ShoppingCart, Eye } from 'lucide-react';
import type { Watch } from '../../types';
import { useCart } from '../../hooks/useCart.ts';
import { watchAPI } from '../../services/api.ts';

interface WatchCardProps {
    watch: Watch;
}

const WatchCard: React.FC<WatchCardProps> = React.memo(({ watch }) => {
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const [isLiked, setIsLiked] = useState(false);
    const [isHovered, setIsHovered] = useState(false);
    const [pictureUrls, setPictureUrls] = useState<string[]>([]);
    const [picturesLoading, setPicturesLoading] = useState(false);
    const [imageLoadError, setImageLoadError] = useState(false);
    const { addToCart, loading } = useCart();

    // Memoized loadPictures function
    const loadPictures = useCallback(async () => {
        setPicturesLoading(true);
        try {
            const urls = await watchAPI.getWatchPictures(watch.id);
            console.log("the url "+urls);
            setPictureUrls(urls);
        } catch (error) {
            console.error('Error loading pictures:', error);
            setPictureUrls([]);
        } finally {
            setPicturesLoading(false);
        }
    }, [watch.id]);

    // Load pictures on mount
    useEffect(() => {
        loadPictures();
    }, [loadPictures]);

    // Load pictures on mount and when watch.id changes
    useEffect(() => {
        const abortController = new AbortController();

        loadPictures();

        return () => {
            abortController.abort();
        };
    }, [loadPictures]);

    const formatPrice = (price: number) =>
        new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
        }).format(price);

    const handleAddToCart = async (e: React.MouseEvent) => {
        e.stopPropagation();
        await addToCart(watch.id, 1);
    };

    const handleImageChange = (index: number) => setCurrentImageIndex(index);

    const handleImageError = (e: React.SyntheticEvent<HTMLImageElement>) => {
        const fallbackSrc = '/placeholder-watch.jpg';
        if (e.currentTarget.src.endsWith(fallbackSrc)) return; // Prevent infinite loop
        e.currentTarget.src = fallbackSrc;
    };
    return (
        <div
            className="group relative bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden border border-gray-100"
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            {/* Image Container */}
            <div className="relative aspect-square overflow-hidden bg-gradient-to-br from-gray-50 to-gray-100">
                {picturesLoading ? (
                    <div className="w-full h-full bg-gradient-to-br from-gray-200 to-gray-300 flex items-center justify-center">
                        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-400"></div>
                    </div>
                ) : imageLoadError ? (
                    <div className="w-full h-full bg-gradient-to-br from-gray-200 to-gray-300 flex items-center justify-center flex-col">
                        <Eye className="w-12 h-12 text-gray-400" />
                        <span className="mt-2 text-gray-500">Error loading images</span>
                    </div>
                ) : pictureUrls.length > 0 ? (
                    <>
                        <img
                            src={pictureUrls[currentImageIndex]}
                            alt={watch.modelName}
                            className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                            onError={handleImageError}
                            loading="lazy"
                        />
                        {pictureUrls.length > 1 && (
                            <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-1">
                                {pictureUrls.map((_, index) => (
                                    <button
                                        key={index}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleImageChange(index);
                                        }}
                                        className={`w-2 h-2 rounded-full transition-all ${
                                            index === currentImageIndex ? 'bg-white shadow-md' : 'bg-white/50 hover:bg-white/75'
                                        }`}
                                    />
                                ))}
                            </div>
                        )}
                    </>
                ) : (
                    <div className="w-full h-full bg-gradient-to-br from-gray-200 to-gray-300 flex items-center justify-center flex-col">
                        <Eye className="w-12 h-12 text-gray-400" />
                        <span className="mt-2 text-gray-500">No Image Available</span>
                    </div>
                )}

                {/* Badges */}
                <div className="absolute top-3 left-3 flex flex-col space-y-2">
                    <span className="bg-blue-600 text-white text-xs px-2 py-1 rounded-full">
                        {watch.watchType}
                    </span>
                    {watch.modelYear && (
                        <span className="bg-gray-800 text-white text-xs px-2 py-1 rounded-full">
                            {watch.modelYear}
                        </span>
                    )}
                </div>

                {/* Action Buttons */}
                <div
                    className={`absolute top-3 right-3 flex flex-col space-y-2 transition-opacity duration-200 ${
                        isHovered ? 'opacity-100' : 'opacity-0'
                    }`}
                >
                    <button
                        onClick={(e) => {
                            e.stopPropagation();
                            setIsLiked(!isLiked);
                        }}
                        className={`p-2 rounded-full backdrop-blur-sm transition-all ${
                            isLiked ? 'bg-red-500 text-white' : 'bg-white/80 text-gray-700 hover:bg-white'
                        }`}
                    >
                        <Heart className={`w-4 h-4 ${isLiked ? 'fill-current' : ''}`} />
                    </button>

                    <button className="p-2 bg-white/80 backdrop-blur-sm rounded-full text-gray-700 hover:bg-white transition-all">
                        <Eye className="w-4 h-4" />
                    </button>
                </div>

                {/* Quick Add to Cart Overlay */}
                <div
                    className={`absolute inset-x-0 bottom-0 bg-gradient-to-t from-black/60 to-transparent p-4 transition-transform duration-300 ${
                        isHovered ? 'translate-y-0' : 'translate-y-full'
                    }`}
                >
                    <button
                        onClick={handleAddToCart}
                        disabled={loading}
                        className="w-full bg-white text-gray-900 py-2 px-4 rounded-lg font-medium hover:bg-gray-100 transition-colors disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center space-x-2"
                    >
                        {loading ? (
                            <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-gray-900"></div>
                        ) : (
                            <>
                                <ShoppingCart className="w-4 h-4" />
                                <span>Add to Cart</span>
                            </>
                        )}
                    </button>
                </div>
            </div>

            {/* Content */}
            <div className="p-4">
                <div className="mb-2">
                    <p className="text-sm text-gray-500 font-medium">{watch.brandName}</p>
                    <h3 className="font-semibold text-gray-900 line-clamp-2 group-hover:text-blue-600 transition-colors">
                        {watch.modelName}
                    </h3>
                </div>

                <p className="text-xs text-gray-400 mb-2">Ref: {watch.referenceNumber}</p>
                <p className="text-sm text-gray-600 mb-3 line-clamp-2">{watch.modelDescription}</p>
                <div className="text-lg font-bold text-gray-900 mb-3">{formatPrice(watch.price)}</div>

                <div className="space-y-1 text-xs text-gray-500">
                    <div className="flex justify-between">
                        <span>Material:</span>
                        <span className="font-medium">{watch.watchMaterial.replace('_', ' ')}</span>
                    </div>
                    {watch.caseWidth && (
                        <div className="flex justify-between">
                            <span>Case:</span>
                            <span className="font-medium">{watch.caseWidth}mm</span>
                        </div>
                    )}
                    <div className="flex justify-between">
                        <span>Movement:</span>
                        <span className="font-medium">{watch.movementCaliber}</span>
                    </div>
                    {watch.watchType === 'MECHANICAL' && watch.powerReserveHours && (
                        <div className="flex justify-between">
                            <span>Power Reserve:</span>
                            <span className="font-medium">{watch.powerReserveHours}h</span>
                        </div>
                    )}
                    {watch.watchType === 'QUARTZ' && watch.batteryType && (
                        <div className="flex justify-between">
                            <span>Battery:</span>
                            <span className="font-medium">{watch.batteryType}</span>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
});

export default WatchCard;