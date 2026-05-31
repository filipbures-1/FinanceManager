import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class StatisticsWindow extends JFrame {
    /**
     * Creates a statistics window and calculates all financial summaries
     *
     * @param transactions list of all transactions to be analyzed
     */
    public StatisticsWindow(List<Transaction> transactions) {
        setTitle("Statistics");
        setSize(500, 350);
        setLayout(new BorderLayout());

        String[] incomeCategories = {"Salary", "Bonus", "Investment", "Other"};
        String[] expenseCategories = {"Food", "Transportation", "Housing", "Entertainment", "Shopping", "Other"};

        double[] incomeSums = new double[incomeCategories.length];
        double[] expenseSums = new double[expenseCategories.length];
        double incomeSum = 0.0;
        double expenseSum = 0.0;

        for (Transaction t : transactions) {
            String type = t.getType();
            String category = t.getCategory();
            double amount = t.getAmount();
            if ("Income".equals(type)) {
                for (int i = 0; i < incomeCategories.length; i++) {
                    if (incomeCategories[i].equals(category)) {
                        incomeSums[i] += amount;
                        break;
                    }
                }
                incomeSum += amount;
            } else if ("Expense".equals(type)) {
                for (int i = 0; i < expenseCategories.length; i++) {
                    if (expenseCategories[i].equals(category)) {
                        expenseSums[i] += amount;
                        break;
                    }
                }
                expenseSum += amount;
            }
        }

        String[] columns = {"Type", "Category", "Amount"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (int i = 0; i < incomeCategories.length; i++) {
            model.addRow(new Object[]{"Income", incomeCategories[i], incomeSums[i]});
        }

        model.addRow(new Object[]{"---", "---", "---"});

        for (int i = 0; i < expenseCategories.length; i++) {
            model.addRow(new Object[]{"Expense", expenseCategories[i], expenseSums[i]});
        }

        model.addRow(new Object[]{"---", "---", "---"});

        model.addRow(new Object[]{"---", "Total Income:", incomeSum});
        model.addRow(new Object[]{"---", "Total Expenses:", expenseSum});
        model.addRow(new Object[]{"---", "Balance:", incomeSum - expenseSum});

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener(e -> setVisible(false));
        add(closeButton, BorderLayout.SOUTH);
    }
}