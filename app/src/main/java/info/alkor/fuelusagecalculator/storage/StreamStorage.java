package info.alkor.fuelusagecalculator.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-07.
 */

public class StreamStorage implements DataStorage {

	private final InputStream input;
	private final OutputStream output;
	private final TSVParser parser = new TSVParser();
	private static final String ENTRY_SEPARATOR = "\n";

	public StreamStorage(InputStream input, OutputStream output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void save(List<Entry> entries) throws IOException {
		if (output == null) {
			throw new UnsupportedOperationException();
		}
		for (Entry entry : entries) {
			output.write(parser.toString(entry).getBytes());
			output.write(ENTRY_SEPARATOR.getBytes());
		}
	}

	@Override
	public void load(List<Entry> entries)  throws IOException {
		if (input == null) {
			throw new UnsupportedOperationException();
		}
		Scanner scanner = new Scanner(input);
		scanner.useDelimiter(ENTRY_SEPARATOR);
		while (scanner.hasNext()) {
			String line = scanner.next();
			for (Entry entry : parser.fromString(line)) {
				entries.add(entry);
			}
		}
	}
}
