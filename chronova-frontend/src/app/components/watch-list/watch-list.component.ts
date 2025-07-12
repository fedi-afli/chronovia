import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { Watch, WatchSearchFilter, WatchMaterial, WatchType } from '../../models/watch.model';

@Component({
  selector: 'app-watch-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <h2 class="mb-4">Watch Collection</h2>
      
      <!-- Search Filters -->
      <div class="card mb-4">
        <div class="card-header">
          <h5>Search Filters</h5>
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-md-3 mb-3">
              <label class="form-label">Brand Name</label>
              <input type="text" class="form-control" [(ngModel)]="searchFilter.brandName" placeholder="Enter brand">
            </div>
            <div class="col-md-3 mb-3">
              <label class="form-label">Model Name</label>
              <input type="text" class="form-control" [(ngModel)]="searchFilter.modelName" placeholder="Enter model">
            </div>
            <div class="col-md-3 mb-3">
              <label class="form-label">Min Price</label>
              <input type="number" class="form-control" [(ngModel)]="searchFilter.minPrice" placeholder="Min price">
            </div>
            <div class="col-md-3 mb-3">
              <label class="form-label">Max Price</label>
              <input type="number" class="form-control" [(ngModel)]="searchFilter.maxPrice" placeholder="Max price">
            </div>
          </div>
          <div class="row">
            <div class="col-md-3 mb-3">
              <label class="form-label">Watch Type</label>
              <select class="form-select" [(ngModel)]="searchFilter.watchType">
                <option value="">All Types</option>
                <option value="MECHANICAL">Mechanical</option>
                <option value="QUARTZ">Quartz</option>
              </select>
            </div>
            <div class="col-md-3 mb-3">
              <label class="form-label">Material</label>
              <select class="form-select" [(ngModel)]="searchFilter.watchMaterial">
                <option value="">All Materials</option>
                <option value="GOLD">Gold</option>
                <option value="STAINLESS_STEEL">Stainless Steel</option>
                <option value="TITANIUM">Titanium</option>
                <option value="CERAMIC">Ceramic</option>
              </select>
            </div>
            <div class="col-md-3 mb-3">
              <label class="form-label">Model Year</label>
              <input type="number" class="form-control" [(ngModel)]="searchFilter.modelYear" placeholder="Year">
            </div>
            <div class="col-md-3 mb-3 d-flex align-items-end">
              <button class="btn btn-primary me-2" (click)="searchWatches()">Search</button>
              <button class="btn btn-secondary" (click)="clearFilters()">Clear</button>
            </div>
          </div>
        </div>
      </div>

      <!-- Watch Grid -->
      <div class="row">
        <div class="col-md-4 mb-4" *ngFor="let watch of watches">
          <div class="card h-100">
            <div class="card-body">
              <h5 class="card-title">{{watch.brandName}} {{watch.modelName}}</h5>
              <p class="card-text">{{watch.modelDescription}}</p>
              <div class="mb-2">
                <small class="text-muted">Ref: {{watch.referenceNumber}}</small>
              </div>
              <div class="mb-2">
                <span class="badge bg-primary me-1">{{watch.watchType}}</span>
                <span class="badge bg-secondary">{{watch.watchMaterial}}</span>
              </div>
              <div class="mb-2">
                <strong>Case: {{watch.caseWidth}}mm x {{watch.caseHeight}}mm</strong>
              </div>
              <div class="mb-2">
                <small>Year: {{watch.modelYear}} | Movement: {{watch.movementCaliber}}</small>
              </div>
              
              <!-- Quartz specific info -->
              <div *ngIf="watch.watchType === 'QUARTZ'" class="mb-2">
                <small class="text-info">
                  Battery: {{watch.batteryType}} | 
                  Solar: {{watch.isSolar ? 'Yes' : 'No'}} |
                  Accuracy: ±{{watch.accuracy}}s/month
                </small>
              </div>
              
              <!-- Mechanical specific info -->
              <div *ngIf="watch.watchType === 'MECHANICAL'" class="mb-2">
                <small class="text-info">
                  {{watch.isSelfWind ? 'Automatic' : 'Manual'}} | 
                  Power Reserve: {{watch.powerReserveHours}}h |
                  Jewels: {{watch.jewelCount}}
                </small>
              </div>
            </div>
            <div class="card-footer">
              <div class="d-flex justify-content-between align-items-center">
                <h4 class="text-success mb-0">\${{watch.price | number:'1.2-2'}}</h4>
                <button class="btn btn-outline-primary" (click)="addToCart(watch)">
                  Add to Cart
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div *ngIf="watches.length === 0" class="text-center mt-5">
        <p class="text-muted">No watches found. Try adjusting your search criteria.</p>
      </div>
    </div>
  `
})
export class WatchListComponent implements OnInit {
  watches: Watch[] = [];
  searchFilter: WatchSearchFilter = {};

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.loadWatches();
  }

  loadWatches() {
    this.apiService.getWatches().subscribe({
      next: (watches) => this.watches = watches,
      error: (error) => console.error('Error loading watches:', error)
    });
  }

  searchWatches() {
    this.apiService.searchWatches(this.searchFilter).subscribe({
      next: (watches) => this.watches = watches,
      error: (error) => console.error('Error searching watches:', error)
    });
  }

  clearFilters() {
    this.searchFilter = {};
    this.loadWatches();
  }

  addToCart(watch: any) {
    // For now, we'll use a hardcoded user ID of 1
    // In a real app, this would come from authentication
    const userId = 1;
    const cartItem = {
      quantity: 1,
      watchId: watch.id || 1 // Assuming watch has an id field
    };

    this.apiService.addToCart(userId, cartItem).subscribe({
      next: (cart) => {
        console.log('Added to cart:', cart);
        alert('Watch added to cart!');
      },
      error: (error) => {
        console.error('Error adding to cart:', error);
        alert('Error adding to cart. Please try again.');
      }
    });
  }
}