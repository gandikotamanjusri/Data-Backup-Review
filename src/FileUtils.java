import java.io.File;
import java.text.DecimalFormat;

public class FileUtils {

    public static void ensureDir(String dir) {
        new File(dir).mkdirs();
    }

    public static long folderSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;

        for (File f : files) {
            if (f.isFile()) size += f.length();
        }
        return size;
    }

    public static String formatSize(long bytes) {
        return new DecimalFormat("#.##")
                .format(bytes / (1024.0 * 1024.0)) + " MB";
    }
}