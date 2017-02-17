package info.alkor.fuelusagecalculator.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marlena on 2017-02-07.
 */

public class OptionalTest {

	@Test
	public void testNullable() {
		String value = null;

		Iterable<String> iterable = Optional.ofNullable(value);
		assertNotNull(iterable);

		Iterator<String> it1 = iterable.iterator();
		assertNotNull(it1);

		assertFalse(it1.hasNext());
		try {
			it1.next();
			Assert.fail("expected exception");
		} catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void testNotNull() {
		String value = "test";

		Iterable<String> iterable = Optional.ofNullable(value);
		assertNotNull(iterable);

		Iterator<String> it1 = iterable.iterator();
		assertNotNull(it1);

		assertTrue(it1.hasNext());
		assertEquals(value, it1.next());

		assertFalse(it1.hasNext());
		try {
			it1.next();
			Assert.fail("expected exception");
		} catch (UnsupportedOperationException e) {
		}
	}
}
