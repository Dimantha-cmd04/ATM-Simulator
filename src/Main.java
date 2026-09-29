public class Main {
    public static void main(String[] args) {
        Account account = new Account(
                "ACC1001",
                "1234",
                50000.00
        );

        ATM atm = new ATM(account);
        atm.start();
    }
}