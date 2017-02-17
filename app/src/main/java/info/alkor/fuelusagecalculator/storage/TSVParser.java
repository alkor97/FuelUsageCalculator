package info.alkor.fuelusagecalculator.storage;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.alkor.fuelusagecalculator.model.Entry;
import info.alkor.fuelusagecalculator.model.Optional;

/**
 * Created by Marlena on 2017-02-07.
 */
public class TSVParser {
	private static final String SEPARATOR = "\t";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@NonNull
	public String toString(@NonNull Entry entry) {
		StringBuilder sb = new StringBuilder();
		sb.append(DATE_FORMAT.format(entry.getDate())).append(SEPARATOR)
				.append(entry.getOdometer()).append(SEPARATOR)
				.append(entry.getVolume());
		return sb.toString();
	}

	@NonNull
	public Optional<Entry> fromString(@NonNull String value) {
		String[] entries = value.split("\\s+");
		if (entries.length == 3) {
			Date date = parseDate(entries[0].trim());
			Integer odometer = parseInteger(entries[1].trim());
			Double volume = parseDouble(entries[2].trim().replaceAll(",", "."));
			if (date != null && odometer != null && volume != null) {
				return Optional.ofNullable(new Entry(date, odometer, volume));
			}
		}
		return Optional.ofNullable(null);
	}

	private Date parseDate(String value) {
		try {
			return DATE_FORMAT.parse(value);
		} catch (ParseException e) {
			return null;
		}
	}

	private Double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Integer parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
