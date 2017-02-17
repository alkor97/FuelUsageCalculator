package info.alkor.fuelusagecalculator.storage;

import org.junit.Test;

import java.util.Date;

import info.alkor.fuelusagecalculator.model.Entry;
import info.alkor.fuelusagecalculator.storage.TSVParser;

import static org.junit.Assert.*;

/**
 * Created by Marlena on 2017-02-07.
 */
public class TSVParserTest {

	@Test
	public void test() throws Exception {
		TSVParser instance = new TSVParser();

		Entry e = new Entry(new Date(), 12345, 67.8);
		for (Entry a : instance.fromString(instance.toString(e))) {
			assertEquals(e, a);
		}
	}
}