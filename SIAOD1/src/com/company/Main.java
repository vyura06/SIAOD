package com.company;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LinkedList<Integer> list = new LinkedList<>();
    private static final LinkedList<Integer> sortedList = new SortedLinkedList<>(Integer::compareTo);

    public static void main(String[] args) {
        String message = "Enter operation\nAdd caller number = 1\nAdd special number = 2\n" +
                "Move in sorted list = 3\nPrint list = 4\nPrint sorted list = 5\nExit = 6";
        boolean inLoop = true;
        do {
            System.out.println(message);
            switch (scanner.nextLine()) {
                case "1" -> addCallerNumber();
                case "2" -> addSpecialNumber();
                case "3" -> retainAll();
                case "4" -> printList();
                case "5" -> printSortedList();
                case "6" -> inLoop = false;
                default -> System.out.println("Line is ont correct");
            }
        } while (inLoop);
    }

    private static void addCallerNumber() {
        System.out.println("Enter caller number");
        int value = scanInt();
        if (isCallerNumber(value)) {
            list.add(value);
        } else {
            System.out.println("Is not correct number");
        }
    }
    private static void addSpecialNumber() {
        System.out.println("Enter special number");
        int value = scanInt();
        if (isSpecialNumber(value)) {
            list.add(value);
        } else {
            System.out.println("Is not correct number");
        }
    }

    private static void retainAll() {
        sortedList.clear();
        list.descending(number -> {
            if (isCallerNumber(number))
                sortedList.add(number);
        });
    }

    private static void printList() {
        System.out.println("List");
        list.forEach(System.out::println);
    }
    private static void printSortedList() {
        System.out.println("SortedList");
        sortedList.forEach(System.out::println);
    }

    private static int scanInt() {
        int result = 0;
        if (scanner.hasNextInt())
            result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    private static boolean isCallerNumber(int number) {
        return number >= 10_000_000 && number <= 99_999_999;
    }
    private static boolean isSpecialNumber(int number) {
        return number >= 100 && number <= 999;
    }
}