import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class Patient extends javax.swing.JFrame {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public Patient() {
        initComponents();
        con = Connect.ConnectDB(); // Ensure that this method establishes the connection correctly
        Get_Data();
    }

    // Method to fetch data from the database and populate the JTable
    private void Get_Data() {
        String sql = "SELECT PatientID AS 'Patient ID', PatientName AS 'Patient Name', FatherName AS 'Father Name', "
                + "Address, ContactNo AS 'Contact No', Email AS 'Email ID', Age, Gen AS 'Gender', BG AS 'Blood Group', Remarks "
                + "FROM PatientRegistration";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs)); // Populates jTable with the data from the query
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e); // Show error message if query fails
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );

        pack();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            con = Connect.ConnectDB();
            int row = jTable1.getSelectedRow();
            String table_click = jTable1.getModel().getValueAt(row, 0).toString();
            String sql = "SELECT * FROM PatientRegistration WHERE PatientID = '" + table_click + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                this.setVisible(false); // Hide current form (use this instead of hide())
                Registration frm = new Registration(); // Assuming Registration is another form
                frm.setVisible(true);

                // Populate the fields in the Registration form with the data
                frm.txtId.setText(rs.getString("PatientID"));
                frm.txtName.setText(rs.getString("PatientName"));
                frm.txtFname.setText(rs.getString("FatherName"));
                frm.txtEmail.setText(rs.getString("Email"));
                
                int age = rs.getInt("Age");
                frm.txtAge.setText(Integer.toString(age));

                frm.txtInfo.setText(rs.getString("Remarks"));
                frm.cmbBG.setSelectedItem(rs.getString("BG"));
                frm.cmbGender.setSelectedItem(rs.getString("Gen"));
                frm.txtAdd.setText(rs.getString("Address"));
                frm.txtContact.setText(rs.getString("ContactNo"));

                frm.btnUpdate.setEnabled(true);
                frm.btnDelete.setEnabled(true);
                frm.btnSave.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex); // Show error message
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        // Custom code for window closing (if necessary)
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Patient().setVisible(true);
        });
    }
}
