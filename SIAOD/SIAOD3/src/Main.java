import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeightingFairQueue<String> queue = new WeightingFairQueue<>();
        String message = "Enter operation:\n" +
                "Add in max priority queue = 1\n" +
                "Peek = 2\n" +
                "Poll = 3\n" +
                "Contains = 4\n" +
                "Remove = 5\n" +
                "Clear = 6\n" +
                "Create new weight queue = 7\n" +
                "Offer in weight queue = 8\n" +
                "Remove weight queue = 9\n" +
                "Print = 10\n" +
                "Exit = 0";
        boolean running = true;
        do {
            System.out.println(message);
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.println("Enter line");
                    queue.offer(scanner.nextLine());
                }
                case "2" -> System.out.println(queue.peek());
                case "3" -> System.out.println(queue.poll());
                case "4" -> {
                    System.out.println("Enter line");
                    System.out.println(queue.contains(scanner.nextLine()));
                }
                case "5" -> {
                    System.out.println("Enter line");
                    if (queue.remove(scanner.nextLine())) {
                        System.out.println("Removed");
                    } else {
                        System.out.println("Element not found");
                    }
                }
                case "6" -> {
                    queue.clear();
                    System.out.println("Cleared");
                }
                case "7" -> {
                    System.out.println("Enter weight");
                    int weight = getInt(scanner);
                    if (weight < 1) {
                        System.out.println("Illegal weight");
                    } else {
                        queue.createNewWQ(weight);
                        System.out.println("Complete");
                    }
                }
                case "8" -> {
                    System.out.println("Size weight queues = " + queue.sizeWQ());
                    int index = getInt(scanner);
                    if (index < 0 || index >= queue.sizeWQ()) {
                        System.out.println("Index out of bounds");
                    } else {
                        System.out.println("Enter line");
                        queue.offerInWQ(scanner.nextLine(), index);
                        System.out.println("Complete");
                    }
                }
                case "9" -> {
                    System.out.println("Size weight queues = " + queue.sizeWQ());
                    int index = getInt(scanner);
                    if (index < 0 || index >= queue.sizeWQ()) {
                        System.out.println("Index out of bounds");
                    } else {
                        queue.removeWQ(index);
                        System.out.println("Removed");
                    }
                }
                case "10" -> System.out.println(queue.toString());
                case "0" -> running = false;
                default -> System.out.println("Error");
            }
        } while (running);
    }
    private static int getInt(Scanner scanner) {
        int i = 0;
        if (scanner.hasNextInt())
            i = scanner.nextInt();
        scanner.nextLine();
        return i;
    }
}