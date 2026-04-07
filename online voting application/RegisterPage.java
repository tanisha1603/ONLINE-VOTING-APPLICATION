import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterPage extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password, confirmPassword;
    JButton register;

    static final Color BG_DARK      = new Color(10, 14, 26);
    static final Color PANEL_BG     = new Color(18, 24, 42);
    static final Color ACCENT       = new Color(0, 210, 190);
    static final Color ACCENT_DIM   = new Color(0, 160, 145);
    static final Color TEXT_PRIMARY = new Color(220, 230, 245);
    static final Color TEXT_MUTED   = new Color(100, 120, 155);
    static final Color FIELD_BG     = new Color(26, 35, 58);
    static final Color FIELD_BORDER = new Color(45, 60, 95);

    RegisterPage() {
        setTitle("Create Account");
        setSize(460, 400);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new LoginPage.GradientPanel();
        root.setLayout(new GridBagLayout());
        root.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ── Title ─────────────────────────────────────────────
        JLabel icon = new JLabel("✦", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 30));
        icon.setForeground(ACCENT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 4, 0);
        root.add(icon, gbc);

        JLabel title = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        title.setForeground(TEXT_PRIMARY);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 2, 0);
        root.add(title, gbc);

        JLabel subtitle = new JLabel("Register to participate in voting", SwingConstants.CENTER);
        subtitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_MUTED);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 20, 0);
        root.add(subtitle, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(FIELD_BORDER);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 18, 0);
        root.add(sep, gbc);

        // ── Fields ────────────────────────────────────────────
        gbc.gridwidth = 1;

        // Username
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; gbc.insets = new Insets(6, 0, 6, 12);
        root.add(styledLabel("Username"), gbc);
        username = styledField(15);
        gbc.gridx = 1; gbc.weightx = 1; gbc.insets = new Insets(6, 0, 6, 0);
        root.add(username, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0; gbc.insets = new Insets(6, 0, 6, 12);
        root.add(styledLabel("Password"), gbc);
        password = styledPassField(15);
        gbc.gridx = 1; gbc.weightx = 1; gbc.insets = new Insets(6, 0, 6, 0);
        root.add(password, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0; gbc.insets = new Insets(6, 0, 6, 12);
        root.add(styledLabel("Confirm"), gbc);
        confirmPassword = styledPassField(15);
        gbc.gridx = 1; gbc.weightx = 1; gbc.insets = new Insets(6, 0, 6, 0);
        root.add(confirmPassword, gbc);

        // ── Register button ───────────────────────────────────
        register = accentButton("  Create Account  ");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.weightx = 0; gbc.insets = new Insets(20, 0, 0, 0);
        root.add(register, gbc);

        register.addActionListener(this);
        setContentPane(root);
        setVisible(true);
    }

    private JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        l.setForeground(TEXT_MUTED);
        return l;
    }

    private JTextField styledField(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        f.setForeground(TEXT_PRIMARY);
        f.setBackground(FIELD_BG);
        f.setCaretColor(ACCENT);
        f.setBorder(new CompoundBorder(
            new LineBorder(FIELD_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)));
        return f;
    }

    private JPasswordField styledPassField(int cols) {
        JPasswordField f = new JPasswordField(cols);
        f.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        f.setForeground(TEXT_PRIMARY);
        f.setBackground(FIELD_BG);
        f.setCaretColor(ACCENT);
        f.setBorder(new CompoundBorder(
            new LineBorder(FIELD_BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)));
        return f;
    }

    private JButton accentButton(String text) {
        JButton b = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ACCENT_DIM : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        b.setForeground(BG_DARK);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        String pass = new String(password.getPassword());
        String confirm = new String(confirmPassword.getPassword());

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this,
                "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (username.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "insert into users(username,password) values(?,?)");
            ps.setString(1, username.getText().trim());
            ps.setString(2, pass);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Account created successfully!\nYou can now log in.",
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
