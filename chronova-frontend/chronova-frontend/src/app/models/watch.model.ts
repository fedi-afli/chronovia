export enum WatchMaterial {
  GOLD = 'GOLD',
  STAINLESS_STEEL = 'STAINLESS_STEEL',
  CARBON = 'CARBON',
  TITANIUM = 'TITANIUM',
  CERAMIC = 'CERAMIC',
  LEATHER = 'LEATHER',
  RUBBER = 'RUBBER'
}

export enum WatchType {
  MECHANICAL = 'MECHANICAL',
  QUARTZ = 'QUARTZ'
}

export interface Watch {
  id?: number;
  referenceNumber: string;
  price: number;
  modelName: string;
  brandName: string;
  modelDescription: string;
  caseWidth: number;
  caseHeight: number;
  watchMaterial: WatchMaterial;
  watchType: WatchType;
  modelYear: number;
  movementCaliber: string;
  
  // Quartz specific fields
  batteryType?: string;
  isSolar?: boolean;
  accuracy?: number;
  
  // Mechanical specific fields
  isSelfWind?: boolean;
  powerReserveHours?: number;
  jewelCount?: number;
}

export interface WatchSearchFilter {
  referenceNumber?: string;
  minPrice?: number;
  maxPrice?: number;
  modelName?: string;
  brandName?: string;
  caseWidth?: number;
  watchMaterial?: WatchMaterial;
  watchType?: WatchType;
  modelYear?: number;
  movementCaliber?: string;
  batteryType?: string;
  isSolar?: boolean;
  isSelfWind?: boolean;
  jewelCount?: number;
}