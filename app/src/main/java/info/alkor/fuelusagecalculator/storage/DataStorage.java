package info.alkor.fuelusagecalculator.storage;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2016-12-06.
 */

public interface DataStorage {
	void save(@NonNull List<Entry> entries) throws IOException;
	void load(@NonNull List<Entry> entries) throws IOException;
}
