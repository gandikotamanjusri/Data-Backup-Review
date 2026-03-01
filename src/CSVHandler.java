import java.io.*;
import java.util.*;

public class CSVHandler {

    public static void exportToCSV(Map<String, Object> data, String path) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("Key,Value,Type\n");
            for (Map.Entry<String, Object> e : data.entrySet()) {
                bw.write(e.getKey() + "," + e.getValue() + "," +
                        e.getValue().getClass().getSimpleName() + "\n");
            }
        }
    }

    public static Map<String, Object> importFromCSV(String path) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                map.put(p[0], convert(p[1], p[2]));
            }
        }
        return map;
    }

    private static Object convert(String val, String type) {
        return switch (type) {
            case "Integer" -> Integer.parseInt(val);
            case "Double" -> Double.parseDouble(val);
            case "Boolean" -> Boolean.parseBoolean(val);
            default -> val;
        };
    }
}