package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerUI {
    public JFrame view;
    //  public IDataAccess dataAdapter;
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtCustomerName = new JTextField(20);
    public JTextField txtPhone = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);

    public JButton btnAdd = new JButton("Add Customer");
    public JButton btnCancel = new JButton("Cancel");


    public AddCustomerUI() {
        view = new JFrame();
        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("CustomerID:"));
        line1.add(txtCustomerID);
        pane.add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name:"));
        line2.add(txtCustomerName);
        pane.add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Phone:"));
        line3.add(txtPhone);
        pane.add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Address:"));
        line4.add(txtAddress);
        pane.add(line4);

        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.add(btnAdd);
        buttonPane.add(btnCancel);
        pane.add(buttonPane);


        //   this.dataAdapter = adapter;

        btnAdd.addActionListener(new AddCustomerUI.AddButtonController());
        btnCancel.addActionListener(
                (actionEvent) -> view.dispose()
        );


    }
    public void run(){
        view.setVisible(true);


    }

    class AddButtonController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            String s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                customer.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID is INVALID (not a number)!!!");
                return;
            }

            s = txtCustomerName.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Customer Name could not be EMPTY!!!");
                return;
            }
            customer.mName = s;

            s = txtPhone.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Phone could not be EMPTY!!!");
                return;
            }
            customer.mPhone = s;


            s = txtAddress.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Address could not be EMPTY!!!");
                return;
            }
            customer.mAddress = s;



            IDataAccess adapter = StoreManager.getInstance().getDataAccess();

            if (adapter.saveCustomer(customer))
                JOptionPane.showMessageDialog(null,
                        "Customer is added successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }
        }
    }
}
