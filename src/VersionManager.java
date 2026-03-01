import java.io.*;
import java.util.*;

public class VersionManager {

    public static List<BackupData> listAll(String dir) throws Exception {
        List<BackupData> list = new ArrayList<>();
        File folder = new File(dir);

        File[] files = folder.listFiles((d, n) -> n.endsWith(".dat"));
        if (files == null) return list;

        for (File f : files) {
            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(f))) {
                list.add((BackupData) ois.readObject());
            }
        }

        list.sort((a, b) -> b.getBackupTime().compareTo(a.getBackupTime()));
        return list;
    }
}