import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceManagerApp extends JFrame {
    private FinanceManager manager = new FinanceManager();
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel balanceLabel;
    private JComboBox<String> categoryFilter;

    /**
     * Creates and initializes the main application window.
     * Loads saved transactions, sets up the GUI.
     */
    public FinanceManagerApp() {
        manager.loadFromFile();
        setTitle("Finance Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        balanceLabel = new JLabel("Balance: " + manager.getBalance());
        topPanel.add(balanceLabel);

        JButton addButton = new JButton("Add Transaction");
        addButton.addActionListener(e -> AddTransactionFunction());
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Amount", "Type", "Category", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JLabel("Filter by category:"));
        categoryFilter = new JComboBox<>(getCategories());
        categoryFilter.addActionListener(e -> refreshTable());
        bottomPanel.add(categoryFilter);

        JButton statsButton = new JButton("Statistics");
        statsButton.addActionListener(e -> openStatisticsWindow());
        topPanel.add(statsButton);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Updates the balance label with the current calculated balance
     */
    private void updateBalance() {
        balanceLabel.setText("Balance: " + manager.getBalance());
    }

    /**
     * Opens the Add Transaction Function and refreshes UI after closing
     */
    private void AddTransactionFunction() {
        new AddTransaction(this, manager).setVisible(true);
        updateBalance();
        refreshTable();
    }

    /**
     * Refreshes the table content based on the selected category, or after a new transaction is added
     */
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Transaction> filtered = manager.filterByCategory((String) categoryFilter.getSelectedItem());
        for (Transaction t : filtered) {
            tableModel.addRow(new Object[]{t.getAmount(), t.getType(), t.getCategory(), t.getDate()});
        }
    }

    /**
     * Creates a list of all available categories
     *
     * @return Array of all categories
     */
    private String[] getCategories() {
        List<String> cats = new ArrayList<>();
        cats.add("All");

        String[] incomeCategories = {"Salary", "Bonus", "Investment", "Other"};
        String[] expenseCategories = {"Food", "Transportation", "Housing", "Entertainment", "Shopping", "Other"};

        for (String c : incomeCategories) {
            if (!cats.contains(c)) {
                cats.add(c);
            }
        }
        for (String c : expenseCategories) {
            if (!cats.contains(c)) {
                cats.add(c);
            }
        }
        return cats.toArray(new String[0]);
    }

    /**
     * Opens the statistics window
     */
    private void openStatisticsWindow() {
        new StatisticsWindow(manager.getTransactions()).setVisible(true);
    }
}