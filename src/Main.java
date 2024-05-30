import java.util.Scanner;

// make login and signup menu
public class Main {
    public static void main(String[] args) {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        SignUp signUp = new SignUp(); // Creating an instance of SignUp
        Login login = new Login(); // Creating an instance of Login

        do {
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login.loginEmployee(); // Call method from Login
                    break;
                case 2:
                    signUp.createNewEmployee(); // Call method from SignUp
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while (choice != 3);

        scanner.close();
    }
}
