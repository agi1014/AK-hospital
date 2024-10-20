import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Room extends javax.swing.JFrame {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Constructor
    public Room() {
        initComponents();
        con = Connect.ConnectDB();  // Establish database connection
        Get_Data();  // Load data into the table
    }

    // Method to fetch and display room data
    private void Get_Data() {
        String sql = "SELECT room_no AS 'Room No', room_charges AS 'Room Charges' FROM room";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            roomTable.setModel(buildTableModel(rs));  // Use custom method to set the model
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Method to build DefaultTableModel from ResultSet
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Names of columns
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // Data of the table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            model.addRow(row);
        }
        return model;
    }

    // GUI Components
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtRoomNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtRoomCharges = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Room Management");

        roomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Room No", "Room Charges"
            }
        ));
        roomTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(roomTable);

        jLabel1.setText("Room No:");

        jLabel2.setText("Room Charges:");

        // Add Room Button
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        // Edit Room Button
        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);  // Disable until a record is selected
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        // Delete Room Button
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
                clearFields();
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
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRoomNo)
                            .addComponent(txtRoomCharges)))
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
                    .addComponent(txtRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRoomCharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    // Add Room
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "INSERT INTO room (room_no, room_charges) VALUES (?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtRoomNo.getText());
            pst.setString(2, txtRoomCharges.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Room Added Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Edit Room
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "UPDATE room SET room_charges = ? WHERE room_no = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtRoomCharges.getText());
            pst.setString(2, txtRoomNo.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Room Updated Successfully");
            Get_Data();  // Refresh table data
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Delete Room
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sql = "DELETE FROM room WHERE room_no = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtRoomNo.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Room Deleted Successfully");
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
        txtRoomNo.setText("");
        txtRoomCharges.setText("");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    // When a row is clicked, load data into the fields
    private void roomTableMouseClicked(java.awt.event.MouseEvent evt) {
        int row = roomTable.getSelectedRow();
        String roomNo = roomTable.getModel().getValueAt(row, 0).toString();
        String sql = "SELECT * FROM room WHERE room_no = '" + roomNo + "'";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtRoomNo.setText(rs.getString("room_no"));
                txtRoomCharges.setText(rs.getString("room_charges"));
                btnEdit.setEnabled(true);  // Enable Edit button
                btnDelete.setEnabled(true);  // Enable Delete button
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Variables declaration
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable roomTable;
    private javax.swing.JTextField txtRoomCharges;
    private javax.swing.JTextField txtRoomNo;
    // End of variables declaration
}
