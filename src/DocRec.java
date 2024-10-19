import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class DocRec extends javax.swing.JFrame {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DocRec() {
        initComponents();
        con = Connect.ConnectDB(); // Initialize connection
        Get_Data();
    }

    private void Get_Data() {
        String sql = "select DoctorID as 'Doctor ID', DoctorName as 'Doctor Name', FatherName as 'Father Name', Address, ContacNo as 'Contact No', Email as 'Email ID', Qualifications, Gender, BloodGroup as 'Blood Group', DateOfJoining as 'Joining Date' from Doctor order by DoctorName";        
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs)); // Use DbUtils to populate the table
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e); // Show error message
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
        );

        pack();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            con = Connect.ConnectDB();
            int row = jTable1.getSelectedRow();
            String table_click = jTable1.getModel().getValueAt(row, 0).toString();
            String sql = "select * from Doctor where DoctorID = '" + table_click + "'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                this.hide();
                Entry frm = new Entry();
                frm.setVisible(true);
                frm.txtId.setText(rs.getString("DoctorID"));
                frm.txtName.setText(rs.getString("Doctorname"));
                frm.txtFname.setText(rs.getString("Fathername"));
                frm.txtE.setText(rs.getString("Email"));
                frm.txtQ.setText(rs.getString("Qualifications"));
                frm.cmbB.setSelectedItem(rs.getString("BloodGroup"));
                frm.cmbG.setSelectedItem(rs.getString("Gender"));
                frm.txtD.setText(rs.getString("DateOfJoining"));
                frm.txtAd.setText(rs.getString("Address"));
                frm.txtC.setText(rs.getString("ContacNo"));
                frm.btnUpdate.setEnabled(true);
                frm.btnDelete.setEnabled(true);
                frm.btnSave.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        // Custom code for window closing can go here
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new DocRec().setVisible(true);
        });
    }
}
