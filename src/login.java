import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    public login() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 30, 80, 25);
        panel.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(100, 30, 150, 25);
        panel.add(txtUsername);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 60, 80, 25);
        panel.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 60, 150, 25);
        panel.add(txtPassword);
        
        btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 90, 150, 25);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
        panel.add(btnLogin);
        
        add(panel);
    }
    
    private void loginAction() {
        Connection con = Connect.ConnectDB();
        try {
            String sql = "SELECT * FROM admin WHERE login_id = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txtUsername.getText());
            pst.setString(2, new String(txtPassword.getPassword()));
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                this.dispose();
                new Main().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new login().setVisible(true);
    }
}
