package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.NumberFormat;
import java.text.ParseException;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-24.
 */

public class OdometerAdapter extends AttributeAdapter<Integer> {

    @NonNull
    private final NumberFormat format;

    public OdometerAdapter(@NonNull String unit) {
        this(unit, NumberFormat.getIntegerInstance());
    }

    public OdometerAdapter(@NonNull String unit, @NonNull NumberFormat format) {
        super(unit);
        this.format = format;
    }

    @NonNull
    @Override
    protected Integer getValue(@NonNull Entry entry) {
        return entry.getOdometer();
    }

    @Override
    protected String formatValue(@NonNull Integer value) {
        return format.format(value);
    }

    @Nullable
    @Override
    public Integer parse(@NonNull String text) {
        try {
            return format.parse(text).intValue();
        } catch (ParseException e) {
            return null;
        }
    }
}
