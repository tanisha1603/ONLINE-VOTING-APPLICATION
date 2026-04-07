import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password;
    JButton login, register;

    // Deep navy + electric teal palette
    static final Color BG_DARK      = new Color(10, 14, 26);
    static final Color PANEL_BG     = new Color(18, 24, 42);
    static final Color ACCENT       = new Color(0, 210, 190);
    static final Color ACCENT_DIM   = new Color(0, 160, 145);
    static final Color TEXT_PRIMARY = new Color(220, 230, 245);
    static final Color TEXT_MUTED   = new Color(100, 120, 155);
    static final Color FIELD_BG     = new Color(26, 35, 58);
    static final Color FIELD_BORDER = new Color(45, 60, 95);

    LoginPage() {
        setTitle("Online Voting System");
        setSize(460, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(BG_DARK);

        JPanel root = new GradientPanel();
        root.setLayout(new GridBagLayout());
        root.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        // ── Title block ──────────────────────────────────────
        JLabel icon = new JLabel("🗳", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 4, 0);
        root.add(icon, gbc);

        JLabel title = new JLabel("VOTE PORTAL", SwingConstants.CENTER);
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 2, 0);
        root.add(title, gbc);

        JLabel subtitle = new JLabel("Secure Online Voting System", SwingConstants.CENTER);
        subtitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_MUTED);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 22, 0);
        root.add(subtitle, gbc);

        // ── Divider ──────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setForeground(FIELD_BORDER);
        sep.setBackground(FIELD_BORDER);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 18, 0);
        root.add(sep, gbc);

        // ── Username row ─────────────────────────────────────
        gbc.gridwidth = 1; gbc.gridy = 4; gbc.insets = new Insets(6, 0, 6, 12);

        JLabel userLabel = styledLabel("Username");
        gbc.gridx = 0; gbc.weightx = 0;
        root.add(userLabel, gbc);

        username = styledField(15);
        gbc.gridx = 1; gbc.weightx = 1; gbc.insets = new Insets(6, 0, 6, 0);
        root.add(username, gbc);

        // ── Password row ─────────────────────────────────────
        gbc.gridy = 5; gbc.insets = new Insets(6, 0, 6, 12);

        JLabel passLabel = styledLabel("Password");
        gbc.gridx = 0; gbc.weightx = 0;
        root.add(passLabel, gbc);

        password = styledPassField(15);
        gbc.gridx = 1; gbc.weightx = 1; gbc.insets = new Insets(6, 0, 6, 0);
        root.add(password, gbc);

        // ── Login button (full width) ─────────────────────────
        login = accentButton("  Login  ", ACCENT, BG_DARK);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(18, 0, 6, 0);
        root.add(login, gbc);

        // ── Register button (full width) ──────────────────────
        register = ghostButton("  Register First  ");
        gbc.gridy = 7; gbc.insets = new Insets(4, 0, 0, 0);
        root.add(register, gbc);

        login.addActionListener(this);
        register.addActionListener(this);

        setContentPane(root);
        setVisible(true);
    }

    // ── Helpers ───────────────────────────────────────────────

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

    private JButton accentButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ACCENT_DIM : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        b.setForeground(fg);
        b.setBackground(bg);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        return b;
    }

    private JButton ghostButton(String text) {
        JButton b = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed()
                    ? new Color(0, 210, 190, 40)
                    : new Color(0, 210, 190, 15));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(ACCENT_DIM);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
        b.setForeground(ACCENT);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 20, 8, 20));
        return b;
    }

    // ── Gradient background panel ─────────────────────────────
    static class GradientPanel extends JPanel {
        GradientPanel() { setOpaque(true); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, 0, BG_DARK, getWidth(), getHeight(), PANEL_BG));
            g2.fillRect(0, 0, getWidth(), getHeight());
            // subtle corner glow
            RadialGradientPaint glow = new RadialGradientPaint(
                new Point2D.Float(getWidth(), 0),
                getWidth() * 0.7f,
                new float[]{0f, 1f},
                new Color[]{new Color(0, 210, 190, 30), new Color(0,0,0,0)});
            g2.setPaint(glow);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            new RegisterPage();
        }
        if (e.getSource() == login) {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "select * from users where username=? and password=?");
                ps.setString(1, username.getText());
                ps.setString(2, new String(password.getPassword()));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getBoolean("voted")) {
                        showDialog("Already Voted", "You have already cast your vote.", false);
                    } else {
                        new VotingPage(rs.getInt("id"));
                        dispose();
                    }
                } else {
                    showDialog("Login Failed", "Invalid username or password.", false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showDialog(String title, String msg, boolean success) {
        JOptionPane.showMessageDialog(this, msg, title,
            success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new LoginPage();
        });
    }
}
