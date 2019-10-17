package edu.auburn;

import java.sql.*;

public class SQLiteDataAdapter implements IDataAccess{
    Connection conn = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" +path ;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;
          //  System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
            case CUSTOMER_LOAD_FAILED: return "Cannot load the customer!";
            case PRODUCT_SAVE_FAILED: return "Cannot save the product!";
            case CUSTOMER_SAVE_FAILED: return "Cannot save the customer!";
        };
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductID, Name, Price, Quantity FROM Products WHERE ProductID = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductID");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;


        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }

    }
    public boolean saveProduct(ProductModel product) {

        try{
            String sql = "INSERT INTO Products(ProductID, Name, Price, Quantity) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, product.mProductID);
            pstmt.setString(2, product.mName);
            pstmt.setDouble(3,product.mPrice);
            pstmt.setInt(4, (int) product.mQuantity);
            pstmt.executeUpdate();
            return true;


        }catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_SAVE_FAILED;

        }

        return false;
    }




    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerId, Name, Phone, Address FROM Customers WHERE CustomerId = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerID");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getString("Phone");


        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
        return customer;
    }

    public boolean saveCustomer(CustomerModel customer) {
        try{
            String sql = "INSERT INTO Customers(CustomerID,Name,Phone,Address) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customer.mCustomerID);
            pstmt.setString(2, customer.mName);
            pstmt.setString(3,customer.mPhone);
            pstmt.setString(4,customer.mAddress);
            pstmt.executeUpdate();
            return true;


        }catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_SAVE_FAILED;

        }

        return false;

    }

    public boolean savePurchase(PurchaseModel purchase) {
        try{
            String sql = "INSERT INTO Orders(OrderID,CustomerID,ProductID,Quantity,Price,TotalTax,TotalCost) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, purchase.mPurchaseID);
            pstmt.setInt(2, purchase.mCustomerID);
            pstmt.setInt(3, purchase.mProductID);
            pstmt.setDouble(4, purchase.mQuantity);
            pstmt.setDouble(5, purchase.mPrice);
            pstmt.setDouble(6, purchase.mTax);
            pstmt.setDouble(7, purchase.mTotalCost);
            pstmt.executeUpdate();
            return true;


        }catch (SQLException e) {
            System.out.println(e.getMessage());


        }

        return false;

    }
}
