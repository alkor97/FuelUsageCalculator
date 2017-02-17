package info.alkor.fuelusagecalculator.calculators;

import android.support.annotation.NonNull;

import java.io.Serializable;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-13.
 */

public abstract class FuelUsageCalculator implements Serializable {

    @NonNull
    public abstract Entry calculateFuelUsage(@NonNull Entry reference, @NonNull Entry current);

    @NonNull
    public abstract String getResultUnit();

    @NonNull
    public abstract String getVolumeUnit();

    @NonNull
    public abstract String getOdometerUnit();

    @NonNull
    protected final Entry max(@NonNull Entry a, @NonNull Entry b) {
        return a.compareTo(b) < 0 ? b : a;
    }
}
