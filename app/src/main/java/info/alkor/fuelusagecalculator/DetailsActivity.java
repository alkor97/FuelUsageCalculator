package info.alkor.fuelusagecalculator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import info.alkor.fuelusagecalculator.adapters.AttributeAdapter;
import info.alkor.fuelusagecalculator.adapters.DateAdapter;
import info.alkor.fuelusagecalculator.adapters.OdometerAdapter;
import info.alkor.fuelusagecalculator.adapters.VolumeAdapter;
import info.alkor.fuelusagecalculator.model.Entry;

public class DetailsActivity extends AppCompatActivity {

	private Calendar date = Calendar.getInstance();
	private Integer odometer;
	private Double volume;
	private Entry original;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		Intent intent = getIntent();

		DateAdapter dateAdapter = get(intent, DateAdapter.class);
		OdometerAdapter odometerAdapter = get(intent, OdometerAdapter.class);
		VolumeAdapter volumeAdapter = get(intent, VolumeAdapter.class);

		original = get(intent, Entry.class);
		if (original != null) {
			date.setTime(original.getDate());
			odometer = original.getOdometer();
			volume = original.getVolume();
		}

		setupDatePicker(dateAdapter);
		setupOdometer(odometerAdapter);
		setupVolume(volumeAdapter);
		updateAddEntryButtonVisibility();
		setupAddButton();
	}

	@NonNull
	private <E extends Serializable> E get(@NonNull Intent intent, @NonNull Class<E> clazz) {
		return (E) intent.getSerializableExtra(clazz.getSimpleName());
	}

	private void setupDatePicker(@NonNull final DateAdapter dateAdapter) {
		final TextView editDate = (TextView) findViewById(R.id.editDate);
		editDate.setText(dateAdapter.format(buildEntry()));

		final DatePickerDialog.OnDateSetListener onDateSelected = new DatePickerDialog
				.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				date.set(Calendar.YEAR, year);
				date.set(Calendar.MONTH, month);
				date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				editDate.setText(dateAdapter.format(buildEntry()));
			}
		};

		editDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(DetailsActivity.this, onDateSelected, date.get(Calendar.YEAR)
						, date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	private <E, A extends AttributeAdapter<E>> void setupParameter(@NonNull final A adapter,
	                                                               @IdRes int id, @NonNull final
	                                                               Saver<E> saver) {
		final EditText edit = (EditText) findViewById(id);
		Entry entry = buildEntry();
		if (adapter.hasValidValue(entry)) {
			edit.setText(adapter.format(buildEntry()));
		}

		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				saver.save(adapter.parse(s.toString()));
				updateAddEntryButtonVisibility();
			}
		});
	}

	private void setupOdometer(@NonNull final OdometerAdapter adapter) {
		setupParameter(adapter, R.id.editOdometer, new Saver<Integer>() {
			@Override
			public void save(Integer value) {
				odometer = value;
			}
		});
	}

	private void setupVolume(@NonNull final VolumeAdapter adapter) {
		setupParameter(adapter, R.id.editVolume, new Saver<Double>() {
			@Override
			public void save(Double value) {
				volume = value;
			}
		});
		String value = adapter.format(new Entry(new Date(), 1, 0.25)).replaceAll("\\d+", "");
		EditText editVolume = (EditText) findViewById(R.id.editVolume);
		editVolume.setKeyListener(DigitsKeyListener.getInstance("0123456789" + value));
	}

	private void setupAddButton() {
		Button button = (Button) findViewById(R.id.saveEntry);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Entry entry = buildEntry();
				intent.putExtra(Entry.class.getSimpleName(), entry);
				intent.putExtra("original" + Entry.class.getSimpleName(), original);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}

	private boolean canAddEntry() {
		return date != null && odometer != null && volume != null;
	}

	private void updateAddEntryButtonVisibility() {
		Button button = (Button) findViewById(R.id.saveEntry);
		button.setClickable(canAddEntry());
	}

	@NonNull
	private Entry buildEntry() {
		return new Entry(date.getTime(), odometer, volume, null);
	}

	private interface Saver<E> {
		void save(@Nullable E value);
	}
}
