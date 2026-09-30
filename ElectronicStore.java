//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.io.*;
import java.util.*;

public class ElectronicStore implements Serializable {

  //Defining
  private int curProducts;
  private String name;
  private Set<Product> stock; //Array to hold all products
  private double revenue;
  private Set<Customer> customers;
  
  public ElectronicStore(String initName){
    this.revenue = 0.0;
    this.name = initName;
    this.stock = new HashSet<>();
    this.customers = new HashSet<>();
  }
  
  public String getName(){
    return name;
  }
  
  //Adds a product and returns true if there is space in the array
  //Returns false otherwise
  public boolean addProduct(Product newProduct){
    if (stock.add(newProduct)) {
     curProducts++;
     return true;
    }
    return false;
  }

  public List<Customer> getCustomers() { return new ArrayList<>(customers); }

  public boolean registerCustomer(Customer c) {
    return customers.add(c);
  }

  public List<Product> searchProducts(String x) {
    List<Product> results = new ArrayList<>();

    String search = x.toLowerCase();

    for (Product p : stock) {
      String productString = p.toString().toLowerCase();

      if (productString.contains(search)) {
        results.add(p);
      }

    }
    return results;
  }

  public List<Product> searchProducts(String x, double minPrice, double maxPrice) {
    List<Product> results = new ArrayList<>();

    String search = x.toLowerCase();

    if (minPrice < 0 && maxPrice < 0) {
      results = searchProducts(x);
    } else {
      for (Product p : stock) {
        String productString = p.toString().toLowerCase();
        if (productString.contains(search)) {
          if (minPrice < 0 && p.getPrice() <= maxPrice) {
            results.add(p);
          } else if (maxPrice < 0 && p.getPrice() >= minPrice) {
            results.add(p);
          } else if (minPrice <= p.getPrice() && maxPrice >= p.getPrice()) {
            results.add(p);
          }
        }

    }
    }
    return results;
  }

  public boolean addStock(Product p, int amount) {
    if (stock.contains(p)) {
      p.setStockQuantity(p.getStockQuantity() + amount);
      return true;
    }
    return false;
  }

  public boolean sellProduct(Product p, Customer c, int amount) {
    if (stock.contains(p) && customers.contains(c) &&
            p.getStockQuantity() >= amount) {
      p.sellUnits(amount);
      c.setTotalSpent(c.getTotalSpent() + (p.getPrice() * amount));

      if (c.getPurchaseHistory().containsKey(p)) {
        c.setPurchaseHistory(p, c.getPurchaseHistory().get(p) + amount);
      } else {
        c.setPurchaseHistory(p, amount);
      }
      return true;
    }
    return false;
  }

  public List<Customer> getTopXCustomers(int x) {
    List<Customer> topCustomers = new ArrayList<>();
    List<Customer> customerList = new ArrayList<>(customers);

    if (x <= 0) {
      return  topCustomers;
    }

    if (x > customerList.size()) {
      x = customers.size();
    }

    for (int i = 0; i < x; i++) {
      Customer highestSpender = customerList.getFirst();
      for (Customer c : customerList) {
        if (c.getTotalSpent() > highestSpender.getTotalSpent()) {
          highestSpender = c;
        }
      }
      topCustomers.add(highestSpender);
      customerList.remove(highestSpender);
    }
    return topCustomers;
  }

  public boolean saveToFile(String filename) throws IOException {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));

      out.writeObject(this);
      out.close();

      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static ElectronicStore loadFromFile(String filename) throws IOException, ClassNotFoundException, NullPointerException {
    ElectronicStore store;

    try {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream((fileIn));

      store = (ElectronicStore) in.readObject();

      in.close();
      fileIn.close();

      return store;

    } catch (IOException e) {

      return null;

    }
  }

} 