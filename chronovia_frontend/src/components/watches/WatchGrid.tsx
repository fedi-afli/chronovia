import React from 'react';
import type {Watch} from '../../types';
import WatchCard from './WatchCard.tsx';
import { Loader } from 'lucide-react';

interface WatchGridProps {
  watches: Watch[];
  loading: boolean;
  error: string | null;
}

const WatchGrid: React.FC<WatchGridProps> = ({
                                               watches,
                                               loading,
                                               error
                                             }) => {
  if (loading) {
    return (
        <div className="flex items-center justify-center py-20">
          <Loader className="h-8 w-8 animate-spin text-blue-600" />
          <span className="ml-2 text-gray-600">Loading watches...</span>
        </div>
    );
  }

  if (error) {
    return (
        <div className="text-center py-20">
          <p className="text-red-600 text-lg">{error}</p>
          <button
              onClick={() => window.location.reload()}
              className="mt-4 bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg transition-colors"
          >
            Try Again
          </button>
        </div>
    );
  }

  if (watches.length === 0) {
    return (
        <div className="text-center py-20">
          <p className="text-gray-600 text-lg">No watches found matching your criteria.</p>
        </div>
    );
  }

  return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {watches.map((watch) => (
            <WatchCard
                key={watch.id}
                watch={watch}
            />
        ))}
      </div>
  );
};

export default WatchGrid;