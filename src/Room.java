import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class Room extends javax.swing.JFrame {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public Room() {
        initComponents();
        con = Connect.ConnectDB();
        Get_Data();
        setLocationRelativeTo(null); // Center the form
    }

    private void Reset() {
        txtRoomNo.setText("");
        txtRoomCharges.setText("");
        cmbRoomType.setSelectedIndex(-1);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
        txtRoomNo.requestFocus();
        Get_Data();
    }

    private void Get_Data() {
        String sql = "SELECT RoomNo AS 'Room No.', RoomType AS 'Room Type', RoomCharges AS 'Room Charges', RoomStatus AS 'Room Status' FROM Room";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Room_table.setModel(DbUtils.resultSetToTableModel(rs)); // Populate JTable with data
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initialize your GUI components here (labels, text fields, buttons, etc.)
        // E.g., jPanel1 = new javax.swing.JPanel();
    }

    private void txtRoomChargesKeyTyped(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        // Ensure only digits are allowed
        if (!Character.isDigit(c)) {
            evt.consume(); // Ignore non-digit input
        }
    }

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {
        Reset();
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            con = Connect.ConnectDB();
            if (txtRoomNo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter room no.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cmbRoomType.getSelectedIndex() == -1) { // Use selected index to validate selection
                JOptionPane.showMessageDialog(this, "Please select room type", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtRoomCharges.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter room charges", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql1 = "SELECT RoomNo FROM Room WHERE RoomNo = ?";
            pst = con.prepareStatement(sql1);
            pst.setString(1, txtRoomNo.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Room No. already exists", "Error", JOptionPane.ERROR_MESSAGE);
                txtRoomNo.setText("");
                txtRoomNo.requestFocus();
                return;
            }

            String sql = "INSERT INTO Room(RoomNo, RoomType, RoomCharges, RoomStatus) VALUES (?, ?, ?, 'Vacant')";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtRoomNo.getText());
            pst.setString(2, cmbRoomType.getSelectedItem().toString());
            pst.setInt(3, Integer.parseInt(txtRoomCharges.getText())); // Convert charges to integer
            pst.execute();

            JOptionPane.showMessageDialog(this, "Successfully saved", "Room Record", JOptionPane.INFORMATION_MESSAGE);
            btnSave.setEnabled(false);
            Get_Data();
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            con = Connect.ConnectDB();
            String sql = "UPDATE Room SET RoomType = ?, RoomCharges = ? WHERE RoomNo = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, cmbRoomType.getSelectedItem().toString());
            pst.setInt(2, Integer.parseInt(txtRoomCharges.getText()));
            pst.setString(3, txtRoomNo.getText());
            pst.execute();

            JOptionPane.showMessageDialog(this, "Successfully updated", "Room Record", JOptionPane.INFORMATION_MESSAGE);
            btnUpdate.setEnabled(false);
            Get_Data();
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int P = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (P == 0) {
                con = Connect.ConnectDB();
                String sql = "DELETE FROM Room WHERE RoomNo = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, txtRoomNo.getText());
                pst.execute();

                JOptionPane.showMessageDialog(this, "Successfully deleted", "Record", JOptionPane.INFORMATION_MESSAGE);
                Reset();
            }
        } catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Room().setVisible(true);
        });
    }
}
