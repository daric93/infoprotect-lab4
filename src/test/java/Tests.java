import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by darya on 20.10.15.
 */
public class Tests {
    @Test
    public void testGetProbabilities() {
        Main obj = new Main();
        String str = "catt";
        Map<Character, Double> map = new HashMap<>();
        map.put('c', 0.25);
        map.put('a', 0.25);
        map.put('t', 0.5);
        assertEquals(map, obj.getProbabilities(str));
    }

    @Test
    public void testGetTable() {
        Main obj = new Main();
        String str = "catt";
        Map<Character, Range> table = obj.getTable(str);
        double t = table.get('t').getHigh() - table.get('t').getLow();
        assertEquals(0.5, t, 0);
        double c = table.get('c').getHigh() - table.get('c').getLow();
        assertEquals(0.25, c, 0);
    }

    @Test
    public void testEncodeChar() {
        Main obj = new Main();
        Range rangeChar = new Range(0.24, 0.36);
        Range range = new Range(0.96, 1);
        obj.encodeChar(rangeChar, range);
        assertEquals(range.getHigh(), 0.9744, 1e-15);
        assertEquals(range.getLow(), 0.9696, 1e-15);
    }

}
