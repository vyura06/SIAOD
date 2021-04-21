import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> map = new Map<>();

    public static void main(String[] args) {
        String message = "Enter operation :\n" +
                "Add = 1\n" +
                "Get = 2\n" +
                "Remove = 3\n" +
                "Contains key = 4\n" +
                "Print map = 5\n" +
                "Exit = 6";
        while (true) {
            System.out.println(message);
            switch (scanner.nextLine()) {
                case "1":
                    put();
                    break;
                case "2":
                    get();
                    break;
                case "3":
                    remove();
                    break;
                case "4":
                    containsKey();
                    break;
                case "5":
                    printMap();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Line is not correct");
            }
        }
    }

    private static void put() {
        System.out.println("Enter key");
        String key = scanner.nextLine();
        System.out.println("Enter value");
        String value = scanner.nextLine();

        String oldValue = map.put(key, value);
        System.out.println("Saved");
        if (oldValue != null)
            System.out.println("Old value is: \"" + oldValue + "\"");
    }
    private static void get() {
        System.out.println("Enter key");
        String key = scanner.nextLine();
        System.out.println(map.get(key));
    }
    private static void remove() {
        System.out.println("Enter key");
        String key = scanner.nextLine();
        String oldValue = map.remove(key);
        if (oldValue == null) {
            System.out.println("Key already deleted");
        } else {
            System.out.println("Removed\nOld value is: \"" + oldValue + "\"");
        }
    }
    private static void containsKey() {
        System.out.println("Enter key");
        String key = scanner.nextLine();
        System.out.println(map.containsKey(key) ? "Yes" : "No");
    }
    private static void printMap() {
        while (true) {
            System.out.println("Print to console = 1\n to file = 2\ncancel = 3");
            switch (scanner.nextLine()) {
                case "1":
                    forEachEntry(System.out::println);
                    break;
                case "2":
                    System.out.println("Enter path to file");
                    String path = scanner.nextLine();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                        Consumer<String> consumer = s -> {
                            try {
                                writer.write(s);
                                writer.write('\r');
                                writer.write('\n');
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        };
                        forEachEntry(consumer);
                        System.out.println("Saved");
                    } catch (IOException e) {
                        System.out.println("File not found");
                    }
                    break;
                case "3":
                    return;
                default:
            }
        }
    }
    private static void forEachEntry(Consumer<String> consumer) {
        map.forEach((key, value) -> consumer.accept("Key = " + key + ", value = " + value));
    }
}