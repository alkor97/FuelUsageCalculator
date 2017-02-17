package info.alkor.fuelusagecalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.alkor.fuelusagecalculator.adapters.EntryAdapter;
import info.alkor.fuelusagecalculator.adapters.EntryLongClickObserver;
import info.alkor.fuelusagecalculator.adapters.EntrySwipeCallback;
import info.alkor.fuelusagecalculator.calculators.CalculateVolumePer100DistanceUnits;
import info.alkor.fuelusagecalculator.calculators.FuelUsageCalculator;
import info.alkor.fuelusagecalculator.model.Entry;
import info.alkor.fuelusagecalculator.storage.CombinedStorage;
import info.alkor.fuelusagecalculator.storage.DataLoader;
import info.alkor.fuelusagecalculator.storage.DataSaver;
import info.alkor.fuelusagecalculator.storage.DataStorage;
import info.alkor.fuelusagecalculator.storage.PersistenceManager;
import info.alkor.fuelusagecalculator.storage.SharedPreferencesStorage;

public class MainActivity extends AppCompatActivity implements PersistenceManager {

	public static final int ADD_ENTRY = 1;
	public static final int EDIT_ENTRY = 2;
	private final List<Entry> entries = new ArrayList<>();
	@NonNull
	private FuelUsageCalculator calculator = new CalculateVolumePer100DistanceUnits();
	private RecyclerView view;
	private EntryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		view = (RecyclerView) findViewById(R.id.table);
		view.setHasFixedSize(true);
		view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		view.setItemAnimator(new DefaultItemAnimator());

		adapter = new EntryAdapter(entries, calculator, this);
		view.setAdapter(adapter);
		loadEntries();

		new ItemTouchHelper(new EntrySwipeCallback(view)).attachToRecyclerView(view);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addEntry);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
				put(intent, adapter.getDateAdapter());
				put(intent, adapter.getOdometerAdapter());
				put(intent, adapter.getVolumeAdapter());
				put(intent, new Entry(new Date(), null, null));
				startActivityForResult(intent, ADD_ENTRY);
			}
		});
		adapter.setLongClickObserver(new EntryLongClickObserver() {
			@Override
			public void onLongClickOnEntry(Entry entry) {
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
				put(intent, adapter.getDateAdapter());
				put(intent, adapter.getOdometerAdapter());
				put(intent, adapter.getVolumeAdapter());
				put(intent, entry);
				startActivityForResult(intent, EDIT_ENTRY);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == ADD_ENTRY) {
				Entry entry = (Entry) data.getSerializableExtra(Entry.class.getSimpleName());
				adapter.add(entry);
				saveEntries();
			} else if (requestCode == EDIT_ENTRY) {
				Entry entry = (Entry) data.getSerializableExtra(Entry.class.getSimpleName());
				Entry original = (Entry) data.getSerializableExtra("original" + Entry.class
						.getSimpleName());
				if (original != null && original.getOdometer() != entry.getOdometer()) {
					adapter.remove(original);
				}
				adapter.add(entry);
				saveEntries();
			}
		}
	}

	private <E extends Serializable> void put(@NonNull Intent intent, @NonNull E value) {
		intent.putExtra(value.getClass().getSimpleName(), value);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private DataStorage getDataStorage() throws IOException {
		return new CombinedStorage(new URL(this.getString(R.string.entries_tsv_url)), new
				SharedPreferencesStorage(getPreferences(0)));
	}

	private void loadEntries() {
		try {
			new DataLoader(getDataStorage()) {
				@Override
				protected void onPostExecute(List<Entry> entries) {
					checkErrorLoadingEntries(getException());
					adapter.addAll(entries);
				}
			}.execute();
		} catch (IOException e) {
			checkErrorLoadingEntries(e);
		}
	}

	private void checkErrorLoadingEntries(Exception exception) {
		if (exception != null) {
			Snackbar.make(view, this.getString(R.string.error_loading_entries), Snackbar
					.LENGTH_LONG).setAction("Action", null).show();
		}
	}

	@Override
	public void saveEntries() {
		try {
			new DataSaver(getDataStorage()) {
				@Override
				protected void onPostExecute(Void aVoid) {
					checkErrorSavingEntries(getException());
				}
			}.execute(entries);
			getDataStorage().save(entries);
		} catch (IOException e) {
			checkErrorSavingEntries(e);
		}
	}

	private void checkErrorSavingEntries(Exception exception) {
		if (exception != null) {
			Snackbar.make(view, this.getString(R.string.error_saving_entries), Snackbar
					.LENGTH_LONG).setAction("Action", null).show();
		}
	}
}
