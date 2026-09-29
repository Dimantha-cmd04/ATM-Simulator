import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    private Account account;
    private ArrayList<Transaction> transactions;
    private Scanner scanner;

    public ATM(Account account) {

        this.account = account;
        transactions = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {

        System.out.println("================================");
        System.out.println("        WELCOME TO ATM");
        System.out.println("================================");

        if (!login()) {
            System.out.println("Too many incorrect attempts.");
            System.out.println("Account locked.");
            return;
        }

        showMenu();
    }

    private boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter your PIN: ");
            String enteredPin = scanner.nextLine();

            if (account.checkPin(enteredPin)) {

                System.out.println("\nLogin successful!");
                return true;

            } else {

                attempts++;

                System.out.println("Incorrect PIN.");

                if (attempts < 3) {
                    System.out.println("Attempts remaining: "
                            + (3 - attempts));
                }
            }
        }

        return false;
    }

    private void showMenu() {

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("           ATM MENU");
            System.out.println("================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Change PIN");
            System.out.println("6. Transaction History");
            System.out.println("7. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        checkBalance();
                        break;

                    case 2:
                        depositMoney();
                        break;

                    case 3:
                        withdrawMoney();
                        break;

                    case 4:
                        transferMoney();
                        break;

                    case 5:
                        changePin();
                        break;

                    case 6:
                        showTransactions();
                        break;

                    case 7:
                        System.out.println("\nThank you for using our ATM.");
                        break;

                    default:
                        System.out.println("Invalid choice.");

                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");

                choice = 0;
            }

        } while (choice != 7);
    }

    private void checkBalance() {

        System.out.println("\n---------- BALANCE ----------");

        System.out.printf("Current Balance: Rs. %.2f%n",
                account.getBalance());
    }

    private void depositMoney() {

        try {

            System.out.print("\nEnter deposit amount: Rs. ");

            double amount = Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {

                System.out.println("Amount must be greater than 0.");
                return;
            }

            account.deposit(amount);

            transactions.add(
                    new Transaction("Deposit", amount)
            );

            System.out.println("Deposit successful!");
            System.out.printf("New Balance: Rs. %.2f%n",
                    account.getBalance());

        } catch (NumberFormatException e) {

            System.out.println("Invalid amount.");
        }
    }

    private void withdrawMoney() {

        try {

            System.out.print("\nEnter withdrawal amount: Rs. ");

            double amount = Double.parseDouble(scanner.nextLine());

            if (account.withdraw(amount)) {

                transactions.add(
                        new Transaction("Withdrawal", amount)
                );

                System.out.println("Withdrawal successful!");

                System.out.printf("Remaining Balance: Rs. %.2f%n",
                        account.getBalance());

            } else {

                System.out.println(
                        "Withdrawal failed. Check the amount and your balance."
                );
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid amount.");
        }
    }

    private void transferMoney() {

        try {

            System.out.print("\nEnter receiver account number: ");

            String receiver =
                    scanner.nextLine();

            System.out.print("Enter transfer amount: Rs. ");

            double amount =
                    Double.parseDouble(scanner.nextLine());

            if (receiver.isEmpty()) {

                System.out.println(
                        "Account number cannot be empty."
                );

                return;
            }

            if (account.withdraw(amount)) {

                transactions.add(
                        new Transaction(
                                "Transfer to " + receiver,
                                amount
                        )
                );

                System.out.println("Transfer successful!");

                System.out.printf(
                        "Remaining Balance: Rs. %.2f%n",
                        account.getBalance()
                );

            } else {

                System.out.println(
                        "Transfer failed. Insufficient balance or invalid amount."
                );
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid amount.");
        }
    }

    private void changePin() {

        System.out.print("\nEnter current PIN: ");
        String oldPin = scanner.nextLine();

        System.out.print("Enter new PIN: ");
        String newPin = scanner.nextLine();

        if (newPin.length() != 4) {

            System.out.println(
                    "PIN must contain exactly 4 digits."
            );

            return;
        }

        if (!newPin.matches("\\d{4}")) {

            System.out.println(
                    "PIN must contain numbers only."
            );

            return;
        }

        if (account.changePin(oldPin, newPin)) {

            System.out.println("PIN changed successfully!");

        } else {

            System.out.println("Current PIN is incorrect.");
        }
    }

    private void showTransactions() {

        System.out.println("\n------- TRANSACTION HISTORY -------");

        if (transactions.isEmpty()) {

            System.out.println("No transactions yet.");

            return;
        }

        for (Transaction transaction : transactions) {

            System.out.println(transaction);
        }
    }
}