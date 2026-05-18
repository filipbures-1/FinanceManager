import javax.swing.*;
import java.awt.*;

class AddTransaction extends JDialog {
    private JTextField amountField, dateField;
    private JComboBox<String> typeCombo, categoryCombo;
    private FinanceManager manager;
    private String[] incomeCategories = {"Plat", "Bonus", "Investice", "Jiné"};
    private String[] expenseCategories = {"Jídlo", "Doprava", "Bydlení", "Zábava", "Nákupy", "Jiné"};

    public AddTransaction(JFrame parent, FinanceManager manager) {
        super(parent, "Přidat transakci", true);
        this.manager = manager;
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Částka:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Typ:"));
        typeCombo = new JComboBox<>(new String[]{"Příjem", "Výdaj"});
        typeCombo.addActionListener(e -> updateCategoryCombo());
        add(typeCombo);

        add(new JLabel("Kategorie:"));
        categoryCombo = new JComboBox<>();
        updateCategoryCombo();
        add(categoryCombo);

        add(new JLabel("Datum (DD-MM-YYYY):"));
        dateField = new JTextField();
        add(dateField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String type = (String) typeCombo.getSelectedItem();
                String category = (String) categoryCombo.getSelectedItem();
                String date = dateField.getText();
                manager.addTransaction(new Transaction(amount, type, category, date));
                setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Neplatná částka.");
            }
        });
        add(okButton);

        JButton cancelButton = new JButton("Zrušit");
        cancelButton.addActionListener(e -> setVisible(false));
        add(cancelButton);
    }

    private void updateCategoryCombo() {
        String selectedType = (String) typeCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        String[] categories;
        if ("Příjem".equals(selectedType)) {
            categories = incomeCategories;
        } else {
            categories = expenseCategories;
        }
        for (String cat : categories) {
            categoryCombo.addItem(cat);
        }
    }
}