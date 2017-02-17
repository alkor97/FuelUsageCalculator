package info.alkor.fuelusagecalculator.storage;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-07.
 */

public class CombinedStorage implements DataStorage {

	private final URL url;
	private final SharedPreferencesStorage sharedStorage;

	public CombinedStorage(URL url, SharedPreferencesStorage sharedStorage) {
		this.url = url;
		this.sharedStorage = sharedStorage;
	}

	@Override
	public void save(@NonNull List<Entry> entries) throws IOException {
		sharedStorage.save(entries);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public void load(@NonNull List<Entry> entries) throws IOException {
		try (InputStream input = url.openStream()) {
			StreamStorage storage = new StreamStorage(input, null);
			storage.load(entries);
		}
		sharedStorage.load(entries);
	}
}
