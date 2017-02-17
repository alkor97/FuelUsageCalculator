package info.alkor.fuelusagecalculator.storage;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.alkor.fuelusagecalculator.adapters.EntryAdapter;
import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-12-06.
 */
public class SharedPreferencesStorage implements DataStorage {

	private final SharedPreferences storage;
	private final TSVParser parser = new TSVParser();

	public SharedPreferencesStorage(SharedPreferences storage) {
		this.storage = storage;
	}

	@Override
	public void save(List<Entry> entries) {
		Set<String> set = new HashSet<>();
		for (Entry entry : entries) {
			set.add(parser.toString(entry));
		}
		SharedPreferences.Editor editor = storage.edit();
		editor.putStringSet("data", set);
		editor.commit();
	}

	@Override
	public void load(List<Entry> entries) {
		Set<String> set = storage.getStringSet("data", new HashSet<String>());
		for (String value : set) {
			for (Entry entry : parser.fromString(value)) {
				entries.add(entry);
			}
		}
	}
}
