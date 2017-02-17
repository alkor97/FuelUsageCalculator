package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-24.
 */

public abstract class AttributeAdapter<E> implements Serializable {

    protected static final String EMPTY_VALUE = "–";
    protected static final String EMPTY_UNIT = "";
    @NonNull
    private final String unit;

    protected AttributeAdapter(@NonNull String unit) {
        this.unit = unit;
    }

    @NonNull
    public String format(@NonNull Entry entry) {
        E value = getValue(entry);
        return isValueValid(value) ? formatValue(value) : formatEmpty();
    }

    protected boolean isValueValid(@Nullable E value) {
        return value != null;
    }

    public boolean hasValidValue(@NonNull Entry entry) {
	    return isValueValid(getValue(entry));
    }

    @Nullable
    protected abstract E getValue(@NonNull Entry entry);

    @NonNull
    public String getUnit() {
        return unit;
    }

    protected abstract String formatValue(@NonNull E value);

    @NonNull
    protected String formatEmpty() {
        return EMPTY_VALUE;
    }

    @NonNull
    public String formatUnit(@NonNull Entry entry) {
        E value = getValue(entry);
        return isValueValid(value) ? unit : EMPTY_UNIT;
    }

    public abstract @Nullable E parse(@NonNull String text);
}
