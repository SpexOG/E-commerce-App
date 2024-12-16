package com.itschool.jpa;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionsExample {
    public static void main(String... args) {
//        scannerExample1();
//        scannerExample2();
        stringExample();
    }

    private static void stringExample() {
        String s = "Salut";
        int validIndex = 2;
        int invalidIndex = 8;
        printCharAt(s, validIndex);
        printCharAt(s, invalidIndex);

    }

    private static void printCharAt(String text, int index) {
        try {
            System.out.println("Char at index " + index + " is " + text.charAt(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void scannerExample2() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter two numbers: ");
            int num1 = scanner.nextInt();
            int num2 = scanner.nextInt();

            int result = num1 / num2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException ex) { // 4/0
            System.out.println("Error: cannot divide by 0!");
        } catch (InputMismatchException ex) {
            System.out.println("Error: Please insert a valid number!");
        } finally {
            System.out.println("End of program");
        }

    }

    private static void scannerExample1() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 2 integers:");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();

        try {
            int rez = num1 / num2; // pt a obtine exceptia 4/0
            System.out.println("Result = " + rez);
        } catch (ArithmeticException e) {
            System.out.println("Error: cannot divide by 0!");
        }

        scanner.close();
    }
}
