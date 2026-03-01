import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        BackupManager manager = new BackupManager();

        while (true) {
            System.out.println("\n=== DATA BACKUP SYSTEM ===");
            System.out.println("1. Create New Backup");
            System.out.println("2. Restore from Binary");
            System.out.println("3. Restore from Compressed");
            System.out.println("4. Import from CSV");
            System.out.println("5. List All Versions");
            System.out.println("6. Cleanup Old Backups");
            System.out.println("7. Backup Statistics");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1 -> {
                    System.out.print("Enter backup description: ");
                    String desc = sc.nextLine();
                    Map<String,Object> data = new HashMap<>();

                    while (true) {
                        System.out.print("Enter key: ");
                        String key = sc.nextLine();
                        System.out.print("Enter value: ");
                        String val = sc.nextLine();
                        System.out.print("Enter type (String/Integer/Double/Boolean): ");
                        String type = sc.nextLine();

                        Object v = switch (type) {
                            case "Integer" -> Integer.parseInt(val);
                            case "Double" -> Double.parseDouble(val);
                            case "Boolean" -> Boolean.parseBoolean(val);
                            default -> val;
                        };
                        data.put(key, v);

                        System.out.print("Add more items? (y/n): ");
                        if (!sc.nextLine().equalsIgnoreCase("y")) break;
                    }

                    String id = manager.createBackup(desc, data);
                    System.out.println("Backup ID: " + id);
                }

                case 2 -> {
                    System.out.print("Enter binary file path: ");
                    BackupData b = manager.restoreBinary(sc.nextLine());
                    System.out.println(b);
                    b.getData().forEach((k,v) ->
                        System.out.println(k + " = " + v + " (" + v.getClass().getSimpleName() + ")"));
                }

                case 3 -> {
                    System.out.print("Enter compressed file path: ");
                    BackupData b = manager.restoreCompressed(sc.nextLine());
                    System.out.println(b);
                }

                case 4 -> {
                    System.out.print("Enter CSV path: ");
                    CSVHandler.importFromCSV(sc.nextLine())
                            .forEach((k,v)->System.out.println(k+"="+v));
                }

                case 5 -> {
                    VersionManager.listAll("backups/")
                            .forEach(System.out::println);
                }

                case 6 -> {
                    System.out.print("Enter days to keep backups: ");
                    manager.cleanup(Integer.parseInt(sc.nextLine()));
                }

                case 7 -> manager.printStats();
                case 8 -> System.exit(0);
            }
        }
    }
}