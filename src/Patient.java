import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Patient extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    // Constructor
    public Patient() {
        initComponents();
        con = Connect.ConnectDB();  // Establish database connection
        Get_Data();  // Load data into the table
    }

    // Method to fetch and display patient data
    private void Get_Data() {
        String sql = "SELECT patient_id AS 'Patient ID', patient_name AS 'Patient Name', fathers_name AS 'Father Name', "
                   + "address AS 'Address', contact_no AS 'Contact No', age AS 'Age' FROM patients";
        try {
            if (con == null || con.isClosed()) {
                JOptionPane.showMessageDialog(this, "Database connection is not established.");
                return;
            }

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            // Clear existing data in the table
            DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
            model.setRowCount(0); // Clear existing rows

            // Populate table with new data
            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getString("Patient ID");
                row[1] = rs.getString("Patient Name");
                row[2] = rs.getString("Father Name");
                row[3] = rs.getString("Address");
                row[4] = rs.getString("Contact No");
                row[5] = rs.getInt("Age");
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
        } finally {
            // Ensure resources are closed in case of an error
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage());
            }
        }
    }
    // GUI Components
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        patientTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel("Patient ID:");
        txtPatientID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel("Patient Name:");
        txtPatientName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel("Father Name:");
        txtFatherName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel("Address:");
        txtAddress = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel("Contact No:");
        txtContactNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel("Age:");
        txtAge = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton("Add");
        btnEdit = new javax.swing.JButton("Edit");
        btnDelete = new javax.swing.JButton("Delete");
        btnClear = new javax.swing.JButton("Clear");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patient Registration");

        patientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Patient ID", "Patient Name", "Father Name", "Address", "Contact No", "Age"
            }
        ));
        patientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patientTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(patientTable);

        // Action Listeners
        btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        btnClear.addActionListener(evt -> btnClearActionPerformed(evt));
        
        btnEdit.setEnabled(false);  // Disable until a record is selected
        btnDelete.setEnabled(false);  // Disable until a record is selected

        // Layout for the components
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtPatientID)
                            .addComponent(txtPatientName)
                            .addComponent(txtFatherName)
                            .addComponent(txtAddress)
                            .addComponent(txtContactNo)
                            .addComponent(txtAge)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addComponent(btnEdit)
                        .addComponent(btnDelete)
                        .addComponent(btnClear)))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPatientID))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPatientName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFatherName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddress))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtContactNo))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAge))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
        );

        pack();
    }

    // Add Patient
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "INSERT INTO patients (patient_id, patient_name, fathers_name, address, contact_no, age) VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtPatientID.getText());
            pst.setString(2, txtPatientName.getText());
            pst.setString(3, txtFatherName.getText());
            pst.setString(4, txtAddress.getText());
            pst.setString(5, txtContactNo.getText());
            pst.setInt(6, Integer.parseInt(txtAge.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient Added Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age input. Please enter a valid number.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // Edit Patient
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "UPDATE patients SET patient_name = ?, fathers_name = ?, address = ?, contact_no = ?, age = ? WHERE patient_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtPatientName.getText());
            pst.setString(2, txtFatherName.getText());
            pst.setString(3, txtAddress.getText());
            pst.setString(4, txtContactNo.getText());
            pst.setInt(5, Integer.parseInt(txtAge.getText()));
            pst.setString(6, txtPatientID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient Updated Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age input. Please enter a valid number.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // Delete Patient
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "DELETE FROM patients WHERE patient_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtPatientID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient Deleted Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // Clear Input Fields
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    // Method to clear all fields
    private void clearFields() {
        txtPatientID.setText("");
        txtPatientName.setText("");
        txtFatherName.setText("");
        txtAddress.setText("");
        txtContactNo.setText("");
        txtAge.setText("");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    // When a row is clicked, load data into the fields
    private void patientTableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = patientTable.getSelectedRow();
        txtPatientID.setText(patientTable.getValueAt(row, 0).toString());
        txtPatientName.setText(patientTable.getValueAt(row, 1).toString());
        txtFatherName.setText(patientTable.getValueAt(row, 2).toString());
        txtAddress.setText(patientTable.getValueAt(row, 3).toString());
        txtContactNo.setText(patientTable.getValueAt(row, 4).toString());
        txtAge.setText(patientTable.getValueAt(row, 5).toString());
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Patient().setVisible(true);
        });
    }

    // GUI Variables declaration
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable patientTable;
    private javax.swing.JTextField txtAddress, txtContactNo, txtFatherName, txtPatientID, txtPatientName, txtAge;

    // End of variables declaration
}
