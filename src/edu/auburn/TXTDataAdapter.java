package edu.auburn;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TXTDataAdapter {// implements IDataAccess {
    Map<Integer, ProductModel> products = new HashMap<>();
    public void connect(String path) {
        try {

            Scanner scanner = new Scanner(new FileReader(new File(path)));
            ProductModel product = new ProductModel();
            while (scanner.hasNext()) {
                product.mProductID = scanner.nextInt(); scanner.nextLine();
                product.mName = scanner.nextLine();
                product.mPrice = scanner.nextDouble();
                product.mQuantity = scanner.nextDouble();

                System.out.println(product);
                products.put(product.mProductID, product);
            }

            scanner.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

  //  @Override
    public ProductModel loadProduct(int id) {
        if (products.containsKey(id))
            return products.get(id);
        return null;
    }

 //   @Override
    public boolean saveProduct(ProductModel product) {
        return false;
    }

  //  @Override
    public CustomerModel loadCustomer(int d) {
        return null;
    }

  //  @Override
    public boolean saveCustomer(CustomerModel customer) {
        return false;
    }


}
