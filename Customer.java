import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Customer implements Serializable {
    private String name;
    private double totalSpent;
    private Map<Product, Integer> purchaseHistory;

    public Customer(String n) {
        this.name = n;
        this.totalSpent = 0;
        this.purchaseHistory = new HashMap<>();
    }

    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double amount) { totalSpent = amount; }

    public String getName() { return name; }


    public Map<Product, Integer> getPurchaseHistory() { return purchaseHistory; }

    public void setPurchaseHistory(Product p, int amount) { this.purchaseHistory.put(p, amount); }

    public void printPurchaseHistory() {
        for (Product p : purchaseHistory.keySet()) {
            int count = purchaseHistory.get(p);
            System.out.println(count + " x " + p);
        }
    }





    public String toString() {
        return name + " who has spent $" + totalSpent;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Customer customer)) {
            return false;
        }

        return this.name.equals(customer.name);
    }

}
