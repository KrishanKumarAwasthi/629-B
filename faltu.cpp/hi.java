import java.io.*;
import java.util.*;


class Student implements Serializable {
    private int studentID;
    private String name;
    private String grade;

    public Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student [ID=" + studentID + ", Name=" + name + ", Grade=" + grade + "]";
    }
}


class Employee {
    private int id;
    private String name;
    private String designation;
    private double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + designation + " | " + salary;
    }
}


public class IntegratedApp {
    private static final String EMP_FILE = "employees.txt";
    private static final String STUDENT_FILE = "student.ser";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Sum of Integers (Autoboxing/Unboxing)");
            System.out.println("2. Serialize & Deserialize Student");
            System.out.println("3. Employee Management System");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sumOfIntegers(sc);
                    break;
                case 2:
                    studentSerializationDemo();
                    break;
                case 3:
                    employeeMenu(sc);
                    break;
                case 4:
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);

        sc.close();
    }


    private static void sumOfIntegers(Scanner sc) {
        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("Enter integers (type 'stop' to finish):");

        while (true) {
            String input = sc.next();
            if (input.equalsIgnoreCase("stop")) break;

            Integer num = Integer.parseInt(input); // Autoboxing
            numbers.add(num);
        }

        int sum = 0;
        for (Integer n : numbers) {
            sum += n; // Unboxing
        }

        System.out.println("Sum of entered integers: " + sum);
    }


    private static void studentSerializationDemo() {
        Student s1 = new Student(101, "Alice", "A");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            oos.writeObject(s1);
            System.out.println("Student object serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

      
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {
            Student s2 = (Student) ois.readObject();
            System.out.println("Deserialized Student: " + s2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

  
    private static void employeeMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 3);
    }

    private static void addEmployee(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Designation: ");
        String designation = sc.nextLine();
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        Employee emp = new Employee(id, name, designation, salary);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMP_FILE, true))) {
            writer.write(emp.toString());
            writer.newLine();
            System.out.println("Employee added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayEmployees() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMP_FILE))) {
            String line;
            System.out.println("\n--- Employee Records ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("------------------------");
        } catch (IOException e) {
            System.out.println("No employee records found.");
        }
    }
}
