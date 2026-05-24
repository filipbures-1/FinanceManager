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

    public FinanceManagerApp() {
        manager.loadFromFile();
        setTitle("Finanční Manažer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        balanceLabel = new JLabel("Aktuální zůstatek: " + manager.getBalance());
        topPanel.add(balanceLabel);

        JButton addButton = new JButton("Přidat transakci");
        addButton.addActionListener(e -> AddTransactionFunction());
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Částka", "Typ", "Kategorie", "Datum"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JLabel("Filtrovat podle kategorie:"));
        categoryFilter = new JComboBox<>(getCategories());
        categoryFilter.addActionListener(e -> refreshTable());
        bottomPanel.add(categoryFilter);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable();
    }


    private void updateBalance() {
        balanceLabel.setText("Aktuální zůstatek: " + manager.getBalance());
    }
    private void AddTransactionFunction() {
        new AddTransaction(this, manager).setVisible(true);
        updateBalance();
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Transaction> filtered = manager.filterByCategory((String) categoryFilter.getSelectedItem());
        for (Transaction t : filtered) {
            tableModel.addRow(new Object[]{t.getAmount(), t.getType(), t.getCategory(), t.getDate()});
        }
    }
    private String[] getCategories() {
        List<String> cats = new ArrayList<>();
        cats.add("Vše");

        String[] incomeCategories = {"Plat", "Bonus", "Investice", "Jiné"};
        String[] expenseCategories = {"Jídlo", "Doprava", "Bydlení", "Zábava", "Nákupy", "Jiné"};

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
}