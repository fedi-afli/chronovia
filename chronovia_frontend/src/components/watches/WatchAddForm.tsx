import React, { useState } from 'react';
import type { Watch } from '../../types';
import { watchAPI } from '../../services/api.ts';

const AddWatchForm: React.FC = () => {
    const [formData, setFormData] = useState<Partial<Watch>>({
        watchType: 'MECHANICAL',
        watchMaterial: 'STAINLESS_STEEL',
    });
    const [imageFiles, setImageFiles] = useState<FileList | null>(null);
    const [message, setMessage] = useState('');

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
    ) => {
        const target = e.target;
        const name = target.name;

        let value: string | boolean;

        if (target instanceof HTMLInputElement && target.type === 'checkbox') {
            value = target.checked;
        } else {
            value = target.value;
        }

        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };


    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setImageFiles(e.target.files);
    };

    // Helper function to convert formData fields to correct types expected by backend
    const normalizeFormData = (data: Partial<Watch>) => ({
        ...data,
        price: data.price !== undefined ? Number(data.price) : 0,
        modelYear: data.modelYear !== undefined ? Number(data.modelYear) : null,
        caseWidth: data.caseWidth !== undefined ? Number(data.caseWidth) : null,
        caseHeight: data.caseHeight !== undefined ? Number(data.caseHeight) : null,
        powerReserveHours: data.powerReserveHours !== undefined ? Number(data.powerReserveHours) : null,
        jewelCount: data.jewelCount !== undefined ? Number(data.jewelCount) : null,
        accuracy: data.accuracy !== undefined ? Number(data.accuracy) : null,
        isSelfWind: Boolean(data.isSelfWind),
        isSolar: Boolean(data.isSolar),
        watchMaterial: data.watchMaterial || 'STAINLESS_STEEL',
        watchType: data.watchType || 'MECHANICAL',
    });

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            setMessage('');

            const normalizedData = normalizeFormData(formData);

            // 1. Save watch data only
            const savedWatch = await watchAPI.saveWatchData(normalizedData);

            // 2. Upload images if any
            if (imageFiles && savedWatch?.id) {
                await watchAPI.uploadWatchPictures(savedWatch.id, imageFiles);
            }

            setMessage('Watch saved successfully!');
        } catch (error) {
            console.error(error);
            setMessage('Failed to save watch.');
        }
    };



    return (
        <div className="p-6 max-w-2xl mx-auto">
            <h2 className="text-xl font-bold mb-4">Add New Watch</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <input
                    name="referenceNumber"
                    placeholder="Reference Number"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />
                <input
                    name="modelName"
                    placeholder="Model Name"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />
                <input
                    name="brandName"
                    placeholder="Brand Name"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />
                <textarea
                    name="modelDescription"
                    placeholder="Description"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />
                <input
                    name="price"
                    type="number"
                    placeholder="Price"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />
                <input
                    name="modelYear"
                    type="number"
                    placeholder="Model Year"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />
                <input
                    name="movementCaliber"
                    placeholder="Movement Caliber"
                    className="w-full p-2 border"
                    onChange={handleChange}
                />

                <select name="watchType" className="w-full p-2 border" onChange={handleChange} value={formData.watchType}>
                    <option value="MECHANICAL">Mechanical</option>
                    <option value="QUARTZ">Quartz</option>
                </select>

                <select name="watchMaterial" className="w-full p-2 border" onChange={handleChange} value={formData.watchMaterial}>
                    <option value="STAINLESS_STEEL">Stainless Steel</option>
                    <option value="GOLD">Gold</option>
                    <option value="TITANIUM">Titanium</option>
                    <option value="CARBON">Carbon</option>
                    <option value="CERAMIC">Ceramic</option>
                    <option value="LEATHER">Leather</option>
                    <option value="RUBBER">Rubber</option>
                </select>

                {formData.watchType === 'MECHANICAL' ? (
                    <>
                        <label className="block">
                            Self Winding
                            <input type="checkbox" name="isSelfWind" onChange={handleChange} checked={!!formData.isSelfWind} />
                        </label>
                        <input
                            name="powerReserveHours"
                            type="number"
                            placeholder="Power Reserve (h)"
                            className="w-full p-2 border"
                            onChange={handleChange}
                            value={formData.powerReserveHours ?? ''}
                        />
                        <input
                            name="jewelCount"
                            type="number"
                            placeholder="Jewel Count"
                            className="w-full p-2 border"
                            onChange={handleChange}
                            value={formData.jewelCount ?? ''}
                        />
                    </>
                ) : (
                    <>
                        <input
                            name="batteryType"
                            placeholder="Battery Type"
                            className="w-full p-2 border"
                            onChange={handleChange}
                            value={formData.batteryType ?? ''}
                        />
                        <label className="block">
                            Solar Powered
                            <input type="checkbox" name="isSolar" onChange={handleChange} checked={!!formData.isSolar} />
                        </label>
                        <input
                            name="accuracy"
                            type="number"
                            placeholder="Accuracy (s/month)"
                            className="w-full p-2 border"
                            onChange={handleChange}
                            value={formData.accuracy ?? ''}
                        />
                    </>
                )}

                <input
                    name="caseWidth"
                    type="number"
                    placeholder="Case Width (mm)"
                    className="w-full p-2 border"
                    onChange={handleChange}
                    value={formData.caseWidth ?? ''}
                />
                <input
                    name="caseHeight"
                    type="number"
                    placeholder="Case Height (mm)"
                    className="w-full p-2 border"
                    onChange={handleChange}
                    value={formData.caseHeight ?? ''}
                />

                <label className="block">
                    Upload Images
                    <input type="file" multiple onChange={handleFileChange} />
                </label>

                <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">
                    Save Watch
                </button>
            </form>
            {message && <p className="mt-4 text-red-600">{message}</p>}
        </div>
    );
};

export default AddWatchForm;
