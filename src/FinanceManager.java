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
    public void loadFromFile() {
        transactions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souboru.");
        }
    }

    public void saveToFile() {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (Transaction t : transactions) {
                fw.write(t.toString() + "\n") ;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Chyba při ukládání souboru.");
        }
    }
    public List<Transaction> filterByCategory(String category) {
        if (category == null || "Vše".equals(category)) {
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