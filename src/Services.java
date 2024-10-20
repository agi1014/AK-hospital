import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class Services extends javax.swing.JFrame {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Constructor
    public Services() {
        initComponents();
        con = Connect.ConnectDB();  // Establish database connection
        Get_Data();  // Load data into the table
    }

    // Method to fetch and display service data
    private void Get_Data() {
        String sql = "SELECT service_id AS 'Service ID', service_name AS 'Service Name', service_charges AS 'Service Charges', "
                   + "service_date AS 'Service Date', patient_id AS 'Patient ID' FROM services";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            servicesTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // GUI Components
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        servicesTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtServiceID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtServiceName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtServiceCharges = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtServiceDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPatientID = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Service Management");

        servicesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Service ID", "Service Name", "Service Charges", "Service Date", "Patient ID"
            }
        ));
        servicesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servicesTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(servicesTable);

        jLabel1.setText("Service ID:");

        jLabel2.setText("Service Name:");

        jLabel3.setText("Service Charges:");

        jLabel4.setText("Service Date (YYYY-MM-DD):");

        jLabel5.setText("Patient ID:");

        // Add Service Button
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        // Edit Service Button
        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);  // Disable until a record is selected
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        // Delete Service Button
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);  // Disable until a record is selected
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        // Clear Fields Button
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        // Layout for the components
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtServiceID)
                            .addComponent(txtServiceName)
                            .addComponent(txtServiceCharges)
                            .addComponent(txtServiceDate)
                            .addComponent(txtPatientID)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtServiceID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtServiceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtServiceCharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtServiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPatientID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addContainerGap())
        );

        pack();
    }

    // Add Service
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "INSERT INTO services (service_id, service_name, service_charges, service_date, patient_id) "
                       + "VALUES (?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtServiceID.getText());
            pst.setString(2, txtServiceName.getText());
            pst.setString(3, txtServiceCharges.getText());
            pst.setString(4, txtServiceDate.getText());
            pst.setString(5, txtPatientID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Service Added Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Edit Service
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "UPDATE services SET service_name = ?, service_charges = ?, service_date = ?, patient_id = ? "
                       + "WHERE service_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtServiceName.getText());
            pst.setString(2, txtServiceCharges.getText());
            pst.setString(3, txtServiceDate.getText());
            pst.setString(4, txtPatientID.getText());
            pst.setString(5, txtServiceID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Service Updated Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Delete Service
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "DELETE FROM services WHERE service_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtServiceID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Service Deleted Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Clear Input Fields
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    // Method to clear all fields
    private void clearFields() {
        txtServiceID.setText("");
        txtServiceName.setText("");
        txtServiceCharges.setText("");
        txtServiceDate.setText("");
        txtPatientID.setText("");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    // When a row is clicked, load data into the fields
    private void servicesTableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = servicesTable.getSelectedRow();
        String serviceID = servicesTable.getModel().getValueAt(row, 0).toString();
        String sql = "SELECT * FROM services WHERE service_id = '" + serviceID + "'";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtServiceID.setText(rs.getString("service_id"));
                txtServiceName.setText(rs.getString("service_name"));
                txtServiceCharges.setText(rs.getString("service_charges"));
                txtServiceDate.setText(rs.getString("service_date"));
                txtPatientID.setText(rs.getString("patient_id"));
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Services().setVisible(true);
        });
    }

    // Variables declaration
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable servicesTable;
    private javax.swing.JTextField txtPatientID;
    private javax.swing.JTextField txtServiceCharges;
    private javax.swing.JTextField txtServiceDate;
    private javax.swing.JTextField txtServiceID;
    private javax.swing.JTextField txtServiceName;
}
