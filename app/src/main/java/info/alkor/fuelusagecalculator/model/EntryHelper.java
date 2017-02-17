package info.alkor.fuelusagecalculator.model;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import info.alkor.fuelusagecalculator.calculators.FuelUsageCalculator;

/**
 * Created by Marlena on 2016-11-22.
 */

public class EntryHelper {

	public int insert(@NonNull List<Entry> list, Entry current, @NonNull FuelUsageCalculator
			calculator) {
		if (!list.isEmpty()) {
			final int idx = Collections.binarySearch(list, current);
			final int insertPosition = idx < 0 ? -idx - 1 : idx;
			if (idx < 0) {
				list.add(insertPosition, current);
			} else {
				list.set(insertPosition, current);
			}
			recomputeFromPosition(list, insertPosition, calculator);
			return insertPosition;
		} else {
			list.add(current);
			return 0;
		}
	}

	public int remove(@NonNull List<Entry> list, Entry current, @NonNull FuelUsageCalculator
			calculator) {
		if (!list.isEmpty()) {
			final int idx = Collections.binarySearch(list, current);
			final int deletePosition = idx < 0 ? -idx - 1 : idx;
			if (deletePosition < list.size() && null != list.remove(deletePosition)) {
				recomputeFromPosition(list, deletePosition, calculator);
				return deletePosition;
			}
		}
		return -1;
	}

	private void recomputeFromPosition(@NonNull List<Entry> list, int fromPosition, @NonNull
			FuelUsageCalculator calculator) {
		for (int i = fromPosition; i < list.size(); ++i) {
			compute(list, i, list.get(i), calculator);
		}
	}

	private void compute(@NonNull List<Entry> list, int insertPosition, @NonNull Entry current,
	                     @NonNull FuelUsageCalculator calculator) {
		if (insertPosition > 0) {
			Entry reference = list.get(insertPosition - 1);
			Entry entry = calculator.calculateFuelUsage(reference, current);
			list.set(insertPosition, entry);
		}
	}
}
