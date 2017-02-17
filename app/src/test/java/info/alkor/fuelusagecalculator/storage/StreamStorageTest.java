package info.alkor.fuelusagecalculator.storage;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import info.alkor.fuelusagecalculator.model.Entry;

import static org.junit.Assert.*;

/**
 * Created by Marlena on 2017-02-07.
 */
public class StreamStorageTest {

	private List<Entry> entries() {
		return Arrays.asList(
				new Entry(new Date(), 12345, 67.8),
				new Entry(new Date(), 23456, 78.9)
		);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Test
	public void test() throws Exception {
		List<Entry> writtenEntries = entries();

		ByteArrayOutputStream o;
		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			StreamStorage writeOnlyStorage = new StreamStorage(null, output);
			writeOnlyStorage.save(writtenEntries);
			o = output;
		}

		ByteArrayInputStream input = new ByteArrayInputStream(o.toByteArray());
		StreamStorage readOnlyStorage = new StreamStorage(input, null);
		List<Entry> readEntries = new ArrayList<>();
		readOnlyStorage.load(readEntries);

		assertEquals(writtenEntries, readEntries);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Test
	public void testRemoteLoading() throws IOException {
		List<Entry> actual = new ArrayList<>();
		{
			try (InputStream input = new URL("http://alkor.info/entries.tsv").openStream()) {
				StreamStorage storage = new StreamStorage(input, null);
				storage.load(actual);
			}
		}

		List<Entry> expected = new ArrayList<>();
		{
			try (InputStream input = getClass().getResourceAsStream("/entries.tsv")) {
				StreamStorage storage = new StreamStorage(input, null);
				storage.load(expected);
			}
		}

		assertEquals(expected, actual);
	}
}