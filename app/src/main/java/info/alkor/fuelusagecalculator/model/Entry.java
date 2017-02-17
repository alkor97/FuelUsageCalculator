package info.alkor.fuelusagecalculator.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

import info.alkor.fuelusagecalculator.adapters.DateAdapter;

/**
 * Created by Marlena on 2016-11-13.
 */

public class Entry implements Comparable<Entry>, Serializable {

	private final
	@NonNull
	Date date;
	private final
	@NonNull
	Integer odometer;
	private final
	@NonNull
	Double volume;
	private final
	@Nullable
	Double fuelUsage;

	public Entry(@NonNull Date date, @NonNull Integer odometer, @NonNull Double volume, Double fuelUsage) {
		this.date = date;
		this.odometer = odometer;
		this.volume = volume;
		this.fuelUsage = fuelUsage;
	}

	public Entry(@NonNull Date date, @NonNull Integer odometer, @NonNull Double volume) {
		this(date, odometer, volume, null);
	}

	public Entry(@NonNull Entry entry, Double fuelUsage) {
		this.date = entry.date;
		this.odometer = entry.odometer;
		this.volume = entry.volume;
		this.fuelUsage = fuelUsage;
	}

	@NonNull
	public Date getDate() {
		return date;
	}

	@NonNull
	public Integer getOdometer() {
		return odometer;
	}

	@NonNull
	public Double getVolume() {
		return volume;
	}

	@Nullable
	public Double getFuelUsage() {
		return fuelUsage;
	}

	@Override
	public int compareTo(@NonNull Entry o) {
		return odometer.compareTo(o.odometer);
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Entry entry = (Entry) o;

		DateAdapter dateAdapter = new DateAdapter();

		if (!dateAdapter.format(this).equals(dateAdapter.format(entry)))
			return false;
		if (!getOdometer().equals(entry.getOdometer()))
			return false;
		if (!getVolume().equals(entry.getVolume()))
			return false;
		return getFuelUsage() != null ? getFuelUsage().equals(entry.getFuelUsage()) : entry
                .getFuelUsage() == null;
	}

	@Override
	public int hashCode() {
		int result = getDate().hashCode();
		result = 31 * result + getOdometer().hashCode();
		result = 31 * result + getVolume().hashCode();
		result = 31 * result + (getFuelUsage() != null ? getFuelUsage().hashCode() : 0);
		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "Entry{" +
				"date=" + date +
				", odometer=" + odometer +
				", volume=" + volume +
				", fuelUsage=" + fuelUsage +
				'}';
	}
}
