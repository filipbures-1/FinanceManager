import java.util.ArrayList;
import java.util.List;

class FinanceManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            if ("Příjem".equals(t.getType())) {
                balance += t.getAmount();
            } else if ("Výdaj".equals(t.getType())) {
                balance -= t.getAmount();
            }
        }
        return balance;
    }

}