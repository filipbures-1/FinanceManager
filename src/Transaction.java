public class Transaction {
    private double amount;
    private String type;
    private String category;
    private String date;

    public Transaction(double amount, String type, String category, String date) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        return new Transaction(Double.parseDouble(parts[0]), parts[1], parts[2], parts[3]);
    }
}
