package info.alkor.fuelusagecalculator.calculators;


import android.support.annotation.NonNull;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-13.
 */

public class CalculateVolumePer100DistanceUnits extends FuelUsageCalculator {
    @NonNull
    @Override
    public Entry calculateFuelUsage(@NonNull Entry reference, @NonNull Entry current) {
        final double distance = Math.abs(reference.getOdometer() - current.getOdometer()) / 100.0;
        final Entry last = max(reference, current);
        return new Entry(last, last.getVolume() / distance);
    }

    @NonNull
    @Override
    public String getResultUnit() {
        return "l/100 km";
    }

    @NonNull
    @Override
    public String getVolumeUnit() {
        return "l";
    }

    @NonNull
    @Override
    public String getOdometerUnit() {
        return "km";
    }
}
