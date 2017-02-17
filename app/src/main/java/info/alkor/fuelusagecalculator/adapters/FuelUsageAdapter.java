package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.NumberFormat;
import java.text.ParseException;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-30.
 */

public class FuelUsageAdapter extends AttributeAdapter<Double> {

    @NonNull
    private final NumberFormat format;

    public FuelUsageAdapter(@NonNull String unit) {
        this(unit, NumberFormat.getNumberInstance());
        this.format.setMaximumFractionDigits(1);
    }

    public FuelUsageAdapter(@NonNull String unit, @NonNull NumberFormat format) {
        super(unit);
        this.format = format;
    }

    @Nullable
    @Override
    protected Double getValue(@NonNull Entry entry) {
        return entry.getFuelUsage();
    }

    @Override
    protected String formatValue(@NonNull Double value) {
        return format.format(value);
    }

    @Nullable
    @Override
    public Double parse(@NonNull String text) {
        try {
            return format.parse(text).doubleValue();
        } catch (ParseException e) {
            return null;
        }
    }
}
