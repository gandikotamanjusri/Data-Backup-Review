import java.io.*;
import java.util.zip.*;

public class CompressionUtils {

    public static void compress(String src, String dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(src);
             GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(dest))) {
            fis.transferTo(gos);
        }
    }

    public static ObjectInputStream compressedInput(String path) throws IOException {
        return new ObjectInputStream(
                new GZIPInputStream(new FileInputStream(path))
        );
    }
}