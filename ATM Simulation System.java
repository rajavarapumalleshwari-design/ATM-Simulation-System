import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;
    private int pin;
    private int failedAttempts;

    public BankAccount(String accountNumber, String holderName,
                       double balance, int pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
        this.failedAttempts = 0;
    }

    public boolean verifyPin(int enteredPin) {
        if (pin == enteredPin) {
            failedAttempts = 0;
            return true;
        }

        failedAttempts++;
        return false;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public double getBalance() {
        return balance;
    }

    public String getHolderName() {
        return holderName;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful.");
            System.out.printf("Amount deposited: ₹%.2f%n", amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            System.out.println("Please collect your cash.");
            System.out.printf("Amount withdrawn: ₹%.2f%n", amount);
        }
    }
}

enum ATMState {
    WELCOME,
    PIN_VERIFICATION,
    MAIN_MENU,
    TRANSACTION,
    SESSION_END,
    CARD_BLOCKED
}

public class ATMSimulation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankAccount account = new BankAccount(
                "ACC1001",
                "Malleshwari",
                10000.00,
                1234
        );

        ATMState state = ATMState.WELCOME;

        System.out.println("================================");
        System.out.println("       WELCOME TO JAVA ATM      ");
        System.out.println("================================");

        state = ATMState.PIN_VERIFICATION;

        // PIN Verification
        int attempts = 0;

        while (state == ATMState.PIN_VERIFICATION) {

            System.out.print("Enter your 4-digit PIN: ");
            int enteredPin = sc.nextInt();

            if (account.verifyPin(enteredPin)) {

                System.out.println("PIN verified successfully.");
                System.out.println("Welcome, " + account.getHolderName() + "!");

                state = ATMState.MAIN_MENU;

            } else {

                attempts++;

                System.out.println("Incorrect PIN.");

                if (attempts >= 3) {
                    System.out.println(
                            "Too many incorrect attempts."
                    );
                    System.out.println("Your card has been blocked.");

                    state = ATMState.CARD_BLOCKED;
                } else {
                    System.out.println(
                            "Attempts remaining: " + (3 - attempts)
                    );
                }
            }
        }

        // ATM Main Session
        while (state == ATMState.MAIN_MENU) {

            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Exit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    state = ATMState.TRANSACTION;

                    System.out.printf(
                            "Current Balance: ₹%.2f%n",
                            account.getBalance()
                    );

                    state = ATMState.MAIN_MENU;
                    break;

                case 2:
                    state = ATMState.TRANSACTION;

                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmount = sc.nextDouble();

                    account.withdraw(withdrawAmount);

                    state = ATMState.MAIN_MENU;
                    break;

                case 3:
                    state = ATMState.TRANSACTION;

                    System.out.print("Enter deposit amount: ₹");
                    double depositAmount = sc.nextDouble();

                    account.deposit(depositAmount);

                    state = ATMState.MAIN_MENU;
                    break;

                case 4:
                    state = ATMState.SESSION_END;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }

        // Session End
        if (state == ATMState.SESSION_END) {

            System.out.println("\n================================");
            System.out.println("Thank you for using Java ATM.");
            System.out.println("Please take your card.");
            System.out.println("Have a nice day!");
            System.out.println("================================");

        } else if (state == ATMState.CARD_BLOCKED) {

            System.out.println("\n================================");
            System.out.println("       CARD BLOCKED             ");
            System.out.println("Please contact your bank.");
            System.out.println("================================");
        }

        sc.close();
    }
}