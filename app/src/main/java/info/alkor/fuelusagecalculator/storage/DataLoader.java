package info.alkor.fuelusagecalculator.storage;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-07.
 */

public class DataLoader extends AsyncTask<Void, Void, List<Entry>> {

	private final DataStorage storage;
	private Exception exception;

	public DataLoader(DataStorage storage) {
		this.storage = storage;
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected List<Entry> doInBackground(Void... params) {
		List<Entry> entries = new ArrayList<>();
		try {
			storage.load(entries);
		} catch (IOException e) {
			exception = e;
		}
		return entries;
	}

	public Exception getException() {
		return exception;
	}
}
