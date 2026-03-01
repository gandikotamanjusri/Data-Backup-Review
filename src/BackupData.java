import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackupData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String backupId;
    private Date backupTime;
    private String description;
    private Map<String, Object> data;

    public BackupData(String description, Map<String, Object> data) {
        this.backupId = UUID.randomUUID().toString();
        this.backupTime = new Date();
        this.description = description;
        this.data = new HashMap<>(data);
    }

    public String getBackupId() { return backupId; }
    public Date getBackupTime() { return backupTime; }
    public String getDescription() { return description; }
    public Map<String, Object> getData() { return data; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Backup ID: " + backupId +
               "\nTime: " + sdf.format(backupTime) +
               "\nDescription: " + description +
               "\nData Items: " + data.size();
    }
}