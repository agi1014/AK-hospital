import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class Services extends javax.swing.JFrame {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public Services() {
        initComponents();
        setLocationRelativeTo(null);
        txtServiceID.setVisible(false); // This hides the Service ID field.
        Get_Data1(); // Populate the table.
    }

    private void Get_Data1() {
        try {
            con = Connect.ConnectDB();
            String sql = "select PatientID as 'Patient ID', PatientName as 'Patient Name' from Patientregistration order by PatientName";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            tblPatient.setModel(DbUtils.resultSetToTableModel(rs)); // Filling the table.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void Reset() {
        txtPatientID.setText("");
        txtServiceCharges.setText("");
        txtPatientName.setText("");
        txtServiceDate.setText("");
        txtServiceName.setText("");
        txtSave.setEnabled(true);
        txtUpdate.setEnabled(false);
        txtDelete.setEnabled(false);
    }

    private void txtServiceNameActionPerformed(java.awt.event.ActionEvent evt) {
        // Handle actions for Service Name text field here if needed
    }

    private void txtServiceDateActionPerformed(java.awt.event.ActionEvent evt) {
        // Handle actions for Service Date text field here if needed
    }

    private void txtNewActionPerformed(java.awt.event.ActionEvent evt) {
        Reset(); // Reset the form when the New button is clicked.
    }

    private void txtSaveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            con = Connect.ConnectDB();
            if (txtServiceName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter service name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtServiceDate.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter service date", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isValidDate(txtServiceDate.getText())) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtServiceCharges.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter service charges", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "insert into Services(ServiceName, ServiceDate, ServiceCharges, PatientID) values(?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtServiceName.getText());
            pst.setString(2, txtServiceDate.getText());
            pst.setString(3, txtServiceCharges.getText());
            pst.setString(4, txtPatientID.getText());

            pst.execute();
            JOptionPane.showMessageDialog(this, "Successfully saved", "Service Record", JOptionPane.INFORMATION_MESSAGE);
            txtSave.setEnabled(false);
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void txtDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int P = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (P == 0) {
                con = Connect.ConnectDB();
                String sql = "delete from Services where ServiceID = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, txtServiceID.getText());
                pst.execute();
                JOptionPane.showMessageDialog(this, "Successfully deleted", "Record", JOptionPane.INFORMATION_MESSAGE);
                Reset();
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void txtUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            con = Connect.ConnectDB();
            String sql = "update Services set ServiceName=?, ServiceDate=?, ServiceCharges=? where ServiceID=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtServiceName.getText());
            pst.setString(2, txtServiceDate.getText());
            pst.setString(3, txtServiceCharges.getText());
            pst.setString(4, txtServiceID.getText());
            pst.execute();
            JOptionPane.showMessageDialog(this, "Successfully updated", "Service Record", JOptionPane.INFORMATION_MESSAGE);
            txtUpdate.setEnabled(false);
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void txtGetDataActionPerformed(java.awt.event.ActionEvent evt) {
        Get_Data1(); // Refresh the data when "Get Data" is clicked.
    }

    // Helper method to validate the date format
    private boolean isValidDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        df.setLenient(false); // Makes it strict on date format
        try {
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Main method and the rest of your form UI methods here.
}
