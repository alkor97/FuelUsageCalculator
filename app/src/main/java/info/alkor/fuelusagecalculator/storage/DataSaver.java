package info.alkor.fuelusagecalculator.storage;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-07.
 */

public class DataSaver extends AsyncTask<List<Entry>,Void,Void> {

	private final DataStorage storage;
	private Exception exception;

	public DataSaver(DataStorage storage) {
		this.storage = storage;
	}

	public Exception getException() {
		return exception;
	}

	@Override
	protected Void doInBackground(List<Entry>... params) {
		try {
			for (List<Entry> entries : params) {
				storage.save(entries);
			}
		} catch (IOException e) {
			exception = e;
		}
		return null;
	}
}
