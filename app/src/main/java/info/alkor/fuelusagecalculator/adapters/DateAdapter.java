package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-11-30.
 */

public class DateAdapter extends AttributeAdapter<Date> {

    @NonNull
    private final DateFormat format;

    public DateAdapter() {
        this(DateFormat.getDateInstance(DateFormat.MEDIUM));
    }

    public DateAdapter(@NonNull DateFormat format) {
        super("");
        this.format = format;
    }

    @NonNull
    @Override
    protected Date getValue(@NonNull Entry entry) {
        return entry.getDate();
    }

    @Override
    protected String formatValue(@NonNull Date value) {
        return format.format(value);
    }

    @Nullable
    @Override
    public Date parse(@NonNull String text) {
        try {
            return format.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }
}
