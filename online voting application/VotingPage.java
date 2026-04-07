import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VotingPage extends JFrame implements ActionListener {

    JRadioButton c1, c2, c3;
    JButton vote;
    int userId;

    static final Color BG_DARK      = new Color(10, 14, 26);
    static final Color PANEL_BG     = new Color(18, 24, 42);
    static final Color ACCENT       = new Color(0, 210, 190);
    static final Color ACCENT_DIM   = new Color(0, 160, 145);
    static final Color TEXT_PRIMARY = new Color(220, 230, 245);
    static final Color TEXT_MUTED   = new Color(100, 120, 155);
    static final Color CARD_BG      = new Color(26, 35, 58);
    static final Color CARD_BORDER  = new Color(45, 60, 95);

    VotingPage(int id) {
        userId = id;
        setTitle("Cast Your Vote");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel root = new LoginPage.GradientPanel();
        root.setLayout(new GridBagLayout());
        root.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        // ── Header ────────────────────────────────────────────
        JLabel icon = new JLabel("🗳", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 4, 0);
        root.add(icon, gbc);

        JLabel title = new JLabel("CAST YOUR VOTE", SwingConstants.CENTER);
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        title.setForeground(TEXT_PRIMARY);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 2, 0);
        root.add(title, gbc);

        JLabel sub = new JLabel("Select one candidate below", SwingConstants.CENTER);
        sub.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        sub.setForeground(TEXT_MUTED);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 20, 0);
        root.add(sub, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(CARD_BORDER);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 18, 0);
        root.add(sep, gbc);

        // ── Radio cards ───────────────────────────────────────
        String[] labels   = {"Candidate A", "Candidate B", "Candidate C"};
        String[] emojis   = {"🔵", "🟠", "🟢"};
        JRadioButton[] rbs = new JRadioButton[3];
        ButtonGroup bg = new ButtonGroup();

        for (int i = 0; i < 3; i++) {
            rbs[i] = candidateCard(emojis[i], labels[i]);
            bg.add(rbs[i]);
            gbc.gridy = 4 + i;
            gbc.insets = new Insets(5, 0, 5, 0);
            root.add(rbs[i], gbc);
        }
        c1 = rbs[0]; c2 = rbs[1]; c3 = rbs[2];

        // ── Submit button ─────────────────────────────────────
        vote = accentButton("  Submit Vote  ");
        gbc.gridy = 7; gbc.insets = new Insets(20, 0, 0, 0);
        root.add(vote, gbc);

        vote.addActionListener(this);
        setContentPane(root);
        setVisible(true);
    }

    private JRadioButton candidateCard(String emoji, String label) {
        JRadioButton rb = new JRadioButton(emoji + "  " + label) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = isSelected()
                    ? new Color(0, 210, 190, 35)
                    : CARD_BG;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                Color border = isSelected() ? ACCENT : CARD_BORDER;
                g2.setColor(border);
                g2.setStroke(new BasicStroke(isSelected() ? 1.5f : 1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        rb.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        rb.setForeground(TEXT_PRIMARY);
        rb.setOpaque(false);
        rb.setBackground(new Color(0,0,0,0));
        rb.setFocusPainted(false);
        rb.setBorder(new EmptyBorder(12, 16, 12, 16));
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Repaint on selection change so custom border updates
        rb.addItemListener(e -> rb.repaint());
        return rb;
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
        b.setBorder(new EmptyBorder(11, 20, 11, 20));
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        String candidate = "";
        if (c1.isSelected()) candidate = "Candidate A";
        else if (c2.isSelected()) candidate = "Candidate B";
        else if (c3.isSelected()) candidate = "Candidate C";

        if (candidate.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please select a candidate before submitting.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirm vote for " + candidate + "?\nThis action cannot be undone.",
            "Confirm Vote", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "update candidates set votes=votes+1 where name=?");
            ps.setString(1, candidate);
            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                "update users set voted=true where id=?");
            ps2.setInt(1, userId);
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "✓  Your vote has been recorded!", "Vote Submitted",
                JOptionPane.INFORMATION_MESSAGE);

            new ResultPage();
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
