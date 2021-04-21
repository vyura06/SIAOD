package com.company;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LinkedList<Integer> list = new LinkedList<>();
    private static final LinkedList<Integer> sortedList = new SortedLinkedList<>(Integer::compareTo);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter operation");
            switch (scanner.nextLine()) {
                case "1":
                    addCallerNumber();
                    break;
                case "2":
                    addSpecialNumber();
                    break;
                case "3":
                    retainAll();
                    break;
                case "4":
                    printList();
                    break;
                case "5":
                    printSortedList();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Line is ont correct");
            }
        }
    }

    private static void addCallerNumber() {
        int callerNumber;
        do {
            callerNumber = scanInt();
        } while (!isCallerNumber(callerNumber));
        scanner.nextLine();
        list.add(callerNumber);
    }
    private static void addSpecialNumber() {
        int specialNumber;
        do {
            specialNumber = scanInt();
        } while (!isSpecialNumber(specialNumber));
        scanner.nextLine();
        list.add(specialNumber);
    }

    private static void retainAll() {
        sortedList.clear();
        list.descending(number -> {
            if (isCallerNumber(number))
                sortedList.add(number);
        });
    }

    private static void printList() {
        System.out.println("\nList");
        list.forEach(System.out::println);
    }
    private static void printSortedList() {
        System.out.println("\nSortedList");
        sortedList.forEach(System.out::println);
    }

    private static int scanInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Error");
            scanner.next();
        }
        return scanner.nextInt();
    }
    private static boolean isCallerNumber(int number) {
        return number >= 10_000_000 && number <= 99_999_999;
    }
    private static boolean isSpecialNumber(int number) {
        return number >= 100 && number <= 999;
    }
}