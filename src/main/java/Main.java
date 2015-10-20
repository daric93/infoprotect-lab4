import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by darya on 19.10.15.
 */
class Range {
    private double low;
    private double high;

    Range(double low, double high) {
        this.low = low;
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public double getHigh() {
        return high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "Range{" +
                "low=" + low +
                ", high=" + high +
                '}';
    }
}

public class Main {
    private Map<Character, Range> rangeTable = new HashMap<>();

    Map<Character, Double> getProbabilities(String text) {
        Map<Character, Double> probabilityMap = new HashMap<>();
        char[] chars = text.toCharArray();
        for (char ch : chars) {
            if (probabilityMap.containsKey(ch))
                probabilityMap.replace(ch, probabilityMap.get(ch) + 1.0);
            else
                probabilityMap.put(ch, 1.0);
        }
        probabilityMap.forEach((k, v) -> probabilityMap.replace(k, v / text.length()));
        return probabilityMap;
    }

    Map<Character, Range> getTable(String text) {
        double low = 0;
        Map<Character, Double> probabilities = getProbabilities(text);
        for (Map.Entry<Character, Double> map : probabilities.entrySet()) {
            rangeTable.put(map.getKey(), new Range(low, low + map.getValue()));
            low += map.getValue();
        }
        return rangeTable;
    }

    void encodeChar(Range rangeChar, Range range) {
        /*Range = high - low
        High = low + range *  high_range of the symbol being coded
                Low = low + range * low_range of the symbol being coded*/
        Double r = range.getHigh() - range.getLow();
        Double high = range.getLow() + r * rangeChar.getHigh();
        Double low = range.getLow() + r * rangeChar.getLow();
        range.setHigh(high);
        range.setLow(low);
    }

    Range encode(String text) {
        rangeTable = getTable(text);
        Range range = new Range(0, 1);
        char[] chars = text.toCharArray();
        for (char ch : chars) {
            encodeChar(rangeTable.get(ch), range);
        }
        return range;
    }

    String decode(Integer textLength, Double number) {
        String str = "";
        NavigableMap<Double, Character> sortedMap = new TreeMap<>();
        rangeTable.forEach((k, v) -> sortedMap.put(v.getLow(), k));
        for (int i = 0; i < textLength; i++) {
            Character value = sortedMap.floorEntry(number).getValue();
            str = str.concat(String.valueOf(value));
            Range range = rangeTable.get(value);
            number = (number - range.getLow()) / (range.getHigh() - range.getLow());
        }
        return str;
    }

    public static void main(String[] args) {
        Main obj = new Main();
        String text = "Hello world!";
        Range range = obj.encode(text);
        System.out.println(range.getLow() + " " + range.getHigh());
        System.out.println(obj.decode(text.length(), (range.getLow()+range.getHigh())/2));
    }
}
