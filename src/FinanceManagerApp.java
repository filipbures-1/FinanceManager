import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FinanceManagerApp extends JFrame {
    private FinanceManager manager = new FinanceManager();
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel balanceLabel;

    public FinanceManagerApp() {
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
        List<Transaction> transactions = manager.getTransactions();
        for (Transaction t : transactions) {
            tableModel.addRow(new Object[]{t.getAmount(), t.getType(), t.getCategory(), t.getDate()});
        }
    }

}