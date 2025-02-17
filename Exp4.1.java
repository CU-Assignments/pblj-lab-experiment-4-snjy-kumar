Experiment 4.1: Employee Management System
-----------------------------------------------------------------------------------
import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID=" + id + ", Name=" + name + ", Salary=" + salary;
    }
}

public class EmployeeManagementSystem {

    private static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Remove Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Display All Employees");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    updateEmployee(scanner);
                    break;
                case 3:
                    removeEmployee(scanner);
                    break;
                case 4:
                    searchEmployee(scanner);
                    break;
                case 5:
                    displayEmployees();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }

    private static void addEmployee(Scanner scanner) {
        System.out.println("Enter Employee ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter Employee Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Employee Salary:");
        double salary = scanner.nextDouble();

        // Check if employee with this ID already exists
        for (Employee emp : employees) {
            if (emp.id == id) {
                System.out.println("Error: Employee with ID " + id + " already exists.");
                return;
            }
        }

        Employee newEmployee = new Employee(id, name, salary);
        employees.add(newEmployee);
        System.out.println("Employee Added: " + newEmployee);
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.println("Enter Employee ID to update:");
        int id = scanner.nextInt();

        for (Employee emp : employees) {
            if (emp.id == id) {
                System.out.println("Enter New Salary:");
                double newSalary = scanner.nextDouble();
                emp.salary = newSalary;
                System.out.println("Employee ID " + id + " updated successfully.");
                return;
            }
        }

        System.out.println("Employee with ID " + id + " not found.");
    }

    private static void removeEmployee(Scanner scanner) {
        System.out.println("Enter Employee ID to remove:");
        int id = scanner.nextInt();

        for (Employee emp : employees) {
            if (emp.id == id) {
                employees.remove(emp);
                System.out.println("Employee ID " + id + " removed successfully.");
                return;
            }
        }

        System.out.println("Employee with ID " + id + " not found.");
    }

    private static void searchEmployee(Scanner scanner) {
        System.out.println("Enter Employee ID or Name to search:");
        String searchTerm = scanner.nextLine();

        boolean found = false;

        // Search by ID or Name
        for (Employee emp : employees) {
            if (String.valueOf(emp.id).equals(searchTerm) || emp.name.equalsIgnoreCase(searchTerm)) {
                System.out.println("Employee Found: " + emp);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee not found.");
        }
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }
}

  
---------------------------------------------------------------------------------------------

Test Cases

Test Case 1: Adding Employees (No Employees Initially)
Display Employees
Expected Output:
No employees found.
  
Test Case 2: Add Employees
Input:
Add Employee (ID=101, Name="Anish", Salary=50000)
Add Employee (ID=102, Name="Bobby", Salary=60000)
Expected Output:
Employee Added: ID=101, Name=Anish, Salary=50000
Employee Added: ID=102, Name=Bobby, Salary=60000

Test Case 3: Update Employee Salary
Input:
Update Employee (ID=101, New Salary=55000)
Expected Output:
Employee ID 101 updated successfully.

Test Case 4: Search Employee by ID
Input:
Search Employee by ID=102
Expected Output:
Employee Found: ID=102, Name=Bobby, Salary=60000

Test Case 5: Remove Employee
Input:
Remove Employee (ID=101)
Expected Output:
Employee ID 101 removed successfully.

Test Case 6: Display All Employees
Input:
Display Employees
Expected Output:
ID: 102, Name: Bobby, Salary: 60000

Test Case 7: Adding Duplicate Employee ID
Input:
Add Employee (ID=101, Name="Charlie", Salary=70000)
Expected Output:
Error: Employee with ID 101 already exists.


