import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class Doctor extends javax.swing.JFrame {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Constructor
    public Doctor() {
        initComponents();
        con = Connect.ConnectDB();  // Establish database connection
        Get_Data();  // Load data into the table
    }

    // Method to fetch and display doctor data
    private void Get_Data() {
        String sql = "SELECT doctor_id AS 'Doctor ID', doctor_name AS 'Doctor Name', father_name AS 'Father Name', "
                   + "contact_no AS 'Contact No', address AS 'Address', date_joining AS 'Date of Joining' FROM doctors";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            doctorTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Fetching Data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // GUI Components
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        doctorTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtDoctorID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDoctorName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFatherName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtContactNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDateJoining = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Doctor Registration");

        doctorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Doctor ID", "Doctor Name", "Father Name", "Contact No", "Address", "Date of Joining"
            }
        ));
        doctorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doctorTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(doctorTable);

        jLabel1.setText("Doctor ID:");
        jLabel2.setText("Doctor Name:");
        jLabel3.setText("Father Name:");
        jLabel4.setText("Contact No:");
        jLabel5.setText("Address:");
        jLabel6.setText("Date of Joining:");

        // Add Doctor Button
        btnAdd.setText("Add");
        btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));

        // Edit Doctor Button
        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);  // Disable until a record is selected
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));

        // Delete Doctor Button
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);  // Disable until a record is selected
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));

        // Clear Fields Button
        btnClear.setText("Clear");
        btnClear.addActionListener(evt -> btnClearActionPerformed(evt));

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
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDoctorID)
                            .addComponent(txtDoctorName)
                            .addComponent(txtFatherName)
                            .addComponent(txtContactNo)
                            .addComponent(txtAddress)
                            .addComponent(txtDateJoining)))
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
                    .addComponent(txtDoctorID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDoctorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDateJoining, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    // Add Doctor
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "INSERT INTO doctors (doctor_id, doctor_name, father_name, contact_no, address, date_joining) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtDoctorID.getText());
            pst.setString(2, txtDoctorName.getText());
            pst.setString(3, txtFatherName.getText());
            pst.setString(4, txtContactNo.getText());
            pst.setString(5, txtAddress.getText());
            pst.setString(6, txtDateJoining.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Doctor Added Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Edit Doctor
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "UPDATE doctors SET doctor_name = ?, father_name = ?, contact_no = ?, address = ?, "
                       + "date_joining = ? WHERE doctor_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtDoctorName.getText());
            pst.setString(2, txtFatherName.getText());
            pst.setString(3, txtContactNo.getText());
            pst.setString(4, txtAddress.getText());
            pst.setString(5, txtDateJoining.getText());
            pst.setString(6, txtDoctorID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Doctor Updated Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Delete Doctor
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "DELETE FROM doctors WHERE doctor_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtDoctorID.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Doctor Deleted Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Clear Fields
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    private void clearFields() {
        txtDoctorID.setText("");
        txtDoctorName.setText("");
        txtFatherName.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");
        txtDateJoining.setText("");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    // Handle Table Row Click
    private void doctorTableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = doctorTable.getSelectedRow();
        if (row != -1) {
            txtDoctorID.setText(doctorTable.getValueAt(row, 0).toString());
            txtDoctorName.setText(doctorTable.getValueAt(row, 1).toString());
            txtFatherName.setText(doctorTable.getValueAt(row, 2).toString());
            txtContactNo.setText(doctorTable.getValueAt(row, 3).toString());
            txtAddress.setText(doctorTable.getValueAt(row, 4).toString());
            txtDateJoining.setText(doctorTable.getValueAt(row, 5).toString());
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Doctor().setVisible(true);
        });
    }

    // GUI Components Declaration
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JTable doctorTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtContactNo;
    private javax.swing.JTextField txtDateJoining;
    private javax.swing.JTextField txtDoctorID;
    private javax.swing.JTextField txtDoctorName;
    private javax.swing.JTextField txtFatherName;
}
