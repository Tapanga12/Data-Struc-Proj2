// Tapanga Witt
// CIS 2353
// Summer 2025
// Project 2

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class PolynomialMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Polynomial> polynomials = new ArrayList<>();

        // Read polynomials from file
        try {
            File polynoms = new File("polynomials.txt");
            Scanner pfileread = new Scanner(polynoms);
            
            while (pfileread.hasNextLine()) {
                String line = pfileread.nextLine();
                if (!line.isEmpty()) {
                    Polynomial poly = new Polynomial(line);
                    polynomials.add(poly);
                }
            }
            pfileread.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: polynomials.txt file not found.");
            return;
        }

        // Main program loop
        while (true) {
            System.out.println("\nList of Polynomials:");
            for (int i = 0; i < polynomials.size(); i++) {
                System.out.print(i + ": ");
                polynomials.get(i).print();
            }

            System.out.println("\nWhich do you wish to add? Press -1 to Exit.");
            String input = scanner.nextLine();

            if (input.equals("-1")) {
                System.out.println("Goodbye.");
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input. Enter two numbers like: 0 2");
                continue;
            }

            try {
                int index1 = Integer.parseInt(parts[0]);
                int index2 = Integer.parseInt(parts[1]);

                if (index1 < 0 || index2 < 0 || index1 >= polynomials.size() || index2 >= polynomials.size()) {
                    System.out.println("Invalid indices. Try again.");
                    continue;
                }

                Polynomial sum = Polynomial.add(polynomials.get(index1), polynomials.get(index2));
                polynomials.add(sum);

            } catch (NumberFormatException e) {
                System.out.println("Input must be two numbers.");
            }
        }

        scanner.close();
    }
}
