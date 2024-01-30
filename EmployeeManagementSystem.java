import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: " + salary;
    }
}

class EmployeeFileHandler {
    private static final String FILE_NAME = "employees.dat";

    public static void saveEmployees(List<Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                employees = (List<Employee>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            // File does not exist or other errors, ignore and return an empty list
        }
        return employees;
    }
}

public class EmployeeManagementSystem {

    private static List<Employee> employees;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        employees = EmployeeFileHandler.loadEmployees();

        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    EmployeeFileHandler.saveEmployees(employees);
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addEmployee() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();

        Employee employee = new Employee(id, name, salary);
        employees.add(employee);
        System.out.println("Employee added successfully!");
    }

    private static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("List of Employees:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                System.out.print("Enter new salary for " + employee.getName() + ": ");
                double newSalary = scanner.nextDouble();
                employee = new Employee(employee.getId(), employee.getName(), newSalary);
                System.out.println("Employee updated successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private static void searchEmployee() {
        // TODO: Implement searching for an employee
    }
}
