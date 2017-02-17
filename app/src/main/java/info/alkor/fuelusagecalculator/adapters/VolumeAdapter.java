package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.NumberFormat;
import java.text.ParseException;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-30.
 */

public class VolumeAdapter extends AttributeAdapter<Double> {

    @NonNull
    private final NumberFormat format;

    public VolumeAdapter(@NonNull String unit) {
        this(unit, NumberFormat.getNumberInstance());
        this.format.setMaximumFractionDigits(2);
    }

    public VolumeAdapter(@NonNull String unit, @NonNull NumberFormat format) {
        super(unit);
        this.format = format;
    }

    @NonNull
    @Override
    protected Double getValue(@NonNull Entry entry) {
        return entry.getVolume();
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
