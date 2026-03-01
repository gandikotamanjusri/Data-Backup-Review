import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackupManager {

    private static final String DIR = "backups/";

    public BackupManager() {
        FileUtils.ensureDir(DIR);
    }

    public String createBackup(String description, Map<String, Object> data) throws Exception {
        BackupData backup = new BackupData(description, data);

        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String base = DIR + "backup_" + ts;

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(base + ".dat"))) {
            oos.writeObject(backup);
        }

        CompressionUtils.compress(base + ".dat", base + ".dat.gz");
        CSVHandler.exportToCSV(data, base + ".csv");

        return backup.getBackupId();
    }

    public BackupData restoreBinary(String path) throws Exception {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(path))) {
            return (BackupData) ois.readObject();
        }
    }

    public BackupData restoreCompressed(String path) throws Exception {
        try (ObjectInputStream ois =
                     CompressionUtils.compressedInput(path)) {
            return (BackupData) ois.readObject();
        }
    }

    public void cleanup(int days) {
        File dir = new File(DIR);
        long cutoff = System.currentTimeMillis() - days * 24L * 60 * 60 * 1000;

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            if (f.lastModified() < cutoff) {
                System.out.println("Deleted old backup: " + f.getName());
                f.delete();
            }
        }
    }

    public void printStats() {
        File dir = new File(DIR);
        long total = FileUtils.folderSize(dir);
        System.out.println("Total Backup Size: " + FileUtils.formatSize(total));
    }
}