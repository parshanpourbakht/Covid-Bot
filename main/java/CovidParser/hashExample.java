package CovidParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class hashExample {
    public static class Customer {
        String name;
        double salary;

        public Customer(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        public String toString() {
            return this.name + " " + this.salary;
        }
    }

    public static void main(String[] args) throws IOException {

        HashMap<String, HashMap<String,String>> CustomerSalaries = new HashMap<>();
//
//        Scanner input = new Scanner(System.in);
//        String quit;
//        do {
//
//            System.out.println("Enter your name: ");
//            String name = input.nextLine();
//            System.out.println("Enter your salary");
//            double salary = input.nextDouble();
//            CustomerSalaries.put(name, new Customer(name, salary));
//            System.out.println(CustomerSalaries);
//            input.nextLine();
//            System.out.println("Quit? y/n");
//            quit = input.nextLine();
//        } while ((!quit.equals("y")));

    }

}
