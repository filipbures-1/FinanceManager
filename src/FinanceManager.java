import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FinanceManager {
    private List<Transaction> transactions = new ArrayList<>();
    private String fileName = "transactions.csv";

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Calculates and updates current balance
     * Income increases balance, Expense decreases balance
     * @return calculated balance
     */
    public double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            if ("Income".equals(t.getType())) {
                balance += t.getAmount();
            } else if ("Expense".equals(t.getType())) {
                balance -= t.getAmount();
            }
        }
        return balance;
    }

    /**
     * Loads transactions from CSV file
     * Shows error if loading fails
     */
    public void loadFromFile() {
        transactions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
        } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading file.");
        }
    }

    /**
     * Saves transactions to CSV file
     * Shows error if saving fails
     */
    public void saveToFile() {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (Transaction t : transactions) {
                fw.write(t.toString() + "\n") ;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading file.");
        }
    }

    /**
     * Filters transactions by category
     *
     * @param category category to filter by, or "All" to return everything
     * @return list of transactions based on the selected category
     */
    public List<Transaction> filterByCategory(String category) {
        if (category == null || "All".equals(category)) {
            return transactions;
        }
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (category.equals(t.getCategory())) {
                filtered.add(t);
            }
        }
        return filtered;
    }
}