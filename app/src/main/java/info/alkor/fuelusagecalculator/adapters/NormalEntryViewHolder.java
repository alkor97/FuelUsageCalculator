package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import info.alkor.fuelusagecalculator.R;
import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-09.
 */
public class NormalEntryViewHolder extends EntryViewHolder {

	private final DateAdapter dateAdapter;
	private final OdometerAdapter odometerAdapter;
	private final VolumeAdapter volumeAdapter;
	private final FuelUsageAdapter fuelUsageAdapter;
	private final EntryLongClickObserver longClickObserver;

	private final TextView date, odometer, volume, fuelUsage;
	private final TextView odometerUnit, volumeUnit, fuelUsageUnit;

	public NormalEntryViewHolder(@NonNull View view, @NonNull DateAdapter dateAdapter, @NonNull
			OdometerAdapter odometerAdapter, @NonNull VolumeAdapter volumeAdapter, @NonNull
			                             FuelUsageAdapter fuelUsageAdapter, @NonNull EntryLongClickObserver longClickObserver) {
		super(view, true);

		this.dateAdapter = dateAdapter;
		this.odometerAdapter = odometerAdapter;
		this.volumeAdapter = volumeAdapter;
		this.fuelUsageAdapter = fuelUsageAdapter;
		this.longClickObserver = longClickObserver;

		date = (TextView) view.findViewById(R.id.date);
		odometer = (TextView) view.findViewById(R.id.odometer);
		odometerUnit = (TextView) view.findViewById(R.id.odometerUnit);
		volume = (TextView) view.findViewById(R.id.volume);
		volumeUnit = (TextView) view.findViewById(R.id.volumeUnit);
		fuelUsage = (TextView) view.findViewById(R.id.fuelUsage);
		fuelUsageUnit = (TextView) view.findViewById(R.id.fuelUsageUnit);
	}

	public void fillWith(final Entry entry) {
		super.fillWith(entry);
		date.setText(dateAdapter.format(entry));
		odometer.setText(odometerAdapter.format(entry));
		odometerUnit.setText(odometerAdapter.formatUnit(entry));
		volume.setText(volumeAdapter.format(entry));
		volumeUnit.setText(volumeAdapter.formatUnit(entry));
		fuelUsage.setText(fuelUsageAdapter.format(entry));
		fuelUsageUnit.setText(fuelUsageAdapter.formatUnit(entry));

		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (longClickObserver != null) {
					longClickObserver.onLongClickOnEntry(entry);
					return true;
				}
				return false;
			}
		});
	}
}
