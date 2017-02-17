package info.alkor.fuelusagecalculator.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import info.alkor.fuelusagecalculator.calculators.FuelUsageCalculator;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Marlena on 2016-11-24.
 */
@RunWith(MockitoJUnitRunner.class)
public class EntryHelperTest {

	private final EntryHelper instance = new EntryHelper();

	@Mock
	private FuelUsageCalculator calculator;

	@Test
	public void oneElementInserted() {
		final List<Entry> list = new ArrayList<>();
		final Entry expected = new Entry(new Date(), 0, 35.0);

		assertEquals(0, instance.insert(list, expected, calculator));
		verify(calculator, times(0)).calculateFuelUsage(any(Entry.class), any(Entry.class));
		assertEquals(Arrays.asList(expected), list);
	}

	@Test
	public void twoElementsInsertedAndSortedByInOdometer() {
		final List<Entry> list = new ArrayList<>();

		final Entry expected1 = new Entry(new Date(), 0, 10.0);
		final Entry input2 = new Entry(new Date(), 200, 10.0);

		final Entry expected2 = new Entry(input2.getDate(), 1, 2.0, 3.0);

		when(calculator.calculateFuelUsage(expected1, input2)).thenReturn(expected2);

		assertEquals(0, instance.insert(list, input2, calculator));
		verify(calculator, times(0)).calculateFuelUsage(any(Entry.class), any(Entry.class));
		assertEquals(0, instance.insert(list, expected1, calculator));
		verify(calculator).calculateFuelUsage(expected1, input2);

		assertEquals(Arrays.asList(expected1, expected2), list);
	}

	@Test
	public void elementOverwritten() {
		final List<Entry> list = new ArrayList<>();

		final Entry expected0 = new Entry(new Date(), 0, 10.0);
		final Entry expected1 = new Entry(new Date(), 1, 20.0);
		final Entry expected2 = new Entry(new Date(), 1, 30.0);
		final Entry expected3 = new Entry(new Date(), 2, 40.0);

		when(calculator.calculateFuelUsage(any(Entry.class), any(Entry.class))).then
				(returnsSecondArg());

		assertEquals(0, instance.insert(list, expected0, calculator));
		verify(calculator, times(0)).calculateFuelUsage(any(Entry.class), any(Entry.class));
		assertEquals(1, instance.insert(list, expected1, calculator));
		verify(calculator).calculateFuelUsage(expected0, expected1);
		assertEquals(1, instance.insert(list, expected2, calculator));
		verify(calculator).calculateFuelUsage(expected0, expected2);
		assertEquals(2, instance.insert(list, expected3, calculator));
		verify(calculator).calculateFuelUsage(expected2, expected3);

		assertEquals(Arrays.asList(expected0, expected2, expected3), list);
	}

	@Test
	public void testDeleting() {
		final List<Entry> list = new ArrayList<>();

		final Entry expected0 = new Entry(new Date(), 0, 10.0);
		final Entry expected1 = new Entry(new Date(), 1, 20.0);
		final Entry expected2 = new Entry(new Date(), 2, 30.0);

		when(calculator.calculateFuelUsage(any(Entry.class), any(Entry.class))).then
				(returnsSecondArg());

		assertEquals(0, instance.insert(list, expected0, calculator));
		assertEquals(1, instance.insert(list, expected1, calculator));
		assertEquals(2, instance.insert(list, expected2, calculator));

		assertEquals(Arrays.asList(expected0, expected1, expected2), list);

		assertEquals(1, instance.remove(list, expected1, calculator));
		verify(calculator).calculateFuelUsage(expected0, expected2);
		assertEquals(Arrays.asList(expected0, expected2), list);

		assertEquals(0, instance.remove(list, expected0, calculator));
		assertEquals(Arrays.asList(expected2), list);

		assertEquals(-1, instance.remove(list, new Entry(new Date(), 100, 0.0), calculator));
		assertEquals(Arrays.asList(expected2), list);

		assertEquals(0, instance.remove(list, expected2, calculator));
		assertEquals(new ArrayList<>(), list);
	}

	@Test
	public void testUnorderedInsertion() {
		final List<Entry> list = new ArrayList<>();

		final Entry expected0 = new Entry(new Date(), 0, 10.0);
		final Entry expected1 = new Entry(new Date(), 1, 20.0);

		when(calculator.calculateFuelUsage(any(Entry.class), any(Entry.class))).then
				(returnsSecondArg());

		assertEquals(0, instance.insert(list, expected1, calculator));
		verify(calculator, times(0)).calculateFuelUsage(any(Entry.class), any(Entry.class));
		assertEquals(0, instance.insert(list, expected0, calculator));
		verify(calculator).calculateFuelUsage(expected0, expected1);

		assertEquals(Arrays.asList(expected0, expected1), list);
	}
}