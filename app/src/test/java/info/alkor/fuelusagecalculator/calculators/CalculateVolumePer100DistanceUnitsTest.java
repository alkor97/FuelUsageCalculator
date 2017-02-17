package info.alkor.fuelusagecalculator.calculators;

import org.junit.Test;

import java.util.Date;

import info.alkor.fuelusagecalculator.model.Entry;
import static org.junit.Assert.*;

/**
 * Created by Marlena on 2016-11-13.
 */
public class CalculateVolumePer100DistanceUnitsTest {
    @Test
    public void testTypicalCase() throws Exception {
        CalculateVolumePer100DistanceUnits c = new CalculateVolumePer100DistanceUnits();
        final Entry a = new Entry(new Date(1000), 0, 0.0);
        final Entry b = new Entry(new Date(2000), 100, 10.0);
        final Entry result = new Entry(b, b.getVolume());
        assertEquals(result, c.calculateFuelUsage(a, b));
    }
}
