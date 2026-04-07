import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class ResultPage extends JFrame {

    static final Color BG_DARK      = new Color(10, 14, 26);
    static final Color PANEL_BG     = new Color(18, 24, 42);
    static final Color ACCENT       = new Color(0, 210, 190);
    static final Color TEXT_PRIMARY = new Color(220, 230, 245);
    static final Color TEXT_MUTED   = new Color(100, 120, 155);
    static final Color CARD_BG      = new Color(26, 35, 58);
    static final Color CARD_BORDER  = new Color(45, 60, 95);

    static final Color[] BAR_COLORS = {
        new Color(0, 210, 190),
        new Color(99, 179, 237),
        new Color(246, 173, 85)
    };

    record CandidateResult(String name, int votes) {}

    ResultPage() {
        setTitle("Voting Results");
        setSize(460, 550);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new LoginPage.GradientPanel();
        root.setLayout(new BorderLayout(0, 0));
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        // ── Header ────────────────────────────────────────────
        JPanel header = new JPanel(new GridLayout(3, 1, 0, 2));
        header.setOpaque(false);

        JLabel icon = new JLabel("📊", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        header.add(icon);

        JLabel title = new JLabel("ELECTION RESULTS", SwingConstants.CENTER);
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        title.setForeground(TEXT_PRIMARY);
        header.add(title);

        JLabel sub = new JLabel("Final vote count", SwingConstants.CENTER);
        sub.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        sub.setForeground(TEXT_MUTED);
        header.add(sub);

        root.add(header, BorderLayout.NORTH);

        // ── Fetch data ────────────────────────────────────────
        List<CandidateResult> results = new ArrayList<>();
        int totalVotes = 0;

        try {
            Connection con = DBConnection.getConnection();
            java.sql.ResultSet rs = con.createStatement()
                .executeQuery("select * from candidates order by votes desc");
            while (rs.next()) {
                int v = rs.getInt("votes");
                results.add(new CandidateResult(rs.getString("name"), v));
                totalVotes += v;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ── Results chart panel ───────────────────────────────
        int total = totalVotes;
        List<CandidateResult> finalResults = results;

        JPanel chartPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int n = finalResults.size();
                int cardH = 60;
                int gap = 14;
                int topPad = 22;

                for (int i = 0; i < n; i++) {
                    CandidateResult cr = finalResults.get(i);
                    int y = topPad + i * (cardH + gap);
                    int cardW = getWidth();
                    Color barColor = BAR_COLORS[i % BAR_COLORS.length];

                    // Card background
                    g2.setColor(CARD_BG);
                    g2.fillRoundRect(0, y, cardW, cardH, 10, 10);
                    g2.setColor(CARD_BORDER);
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, y, cardW - 1, cardH - 1, 10, 10);

                    // Bar fill
                    double pct = total > 0 ? (double) cr.votes / total : 0;
                    int barW = (int) ((cardW - 20) * pct);
                    if (barW > 0) {
                        g2.setColor(new Color(barColor.getRed(),
                            barColor.getGreen(), barColor.getBlue(), 55));
                        g2.fillRoundRect(10, y + cardH - 12, barW, 6, 4, 4);
                        g2.setColor(barColor);
                        g2.setStroke(new BasicStroke(2f));
                        g2.drawRoundRect(10, y + cardH - 12, barW, 6, 4, 4);
                    }

                    // Rank medal
                    String medal = i == 0 ? "🥇" : i == 1 ? "🥈" : "🥉";
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
                    g2.drawString(medal, 12, y + 30);

                    // Name
                    g2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
                    g2.setColor(TEXT_PRIMARY);
                    g2.drawString(cr.name, 48, y + 24);

                    // Vote count
                    String votes = cr.votes + " votes";
                    g2.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
                    g2.setColor(barColor);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(votes, cardW - fm.stringWidth(votes) - 12, y + 24);

                    // Percentage
                    String pctStr = String.format("%.1f%%", pct * 100);
                    g2.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
                    g2.setColor(TEXT_MUTED);
                    fm = g2.getFontMetrics();
                    g2.drawString(pctStr, cardW - fm.stringWidth(pctStr) - 12, y + 40);
                }
                g2.dispose();
            }
            public Dimension getPreferredSize() {
                int n = finalResults.size();
                return new Dimension(400, 22 + n * (60 + 14));
            }
        };
        chartPanel.setOpaque(false);

        // ── Total votes footer ─────────────────────────────────
        JLabel totalLabel = new JLabel(
            "Total votes cast: " + totalVotes, SwingConstants.CENTER);
        totalLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        totalLabel.setForeground(TEXT_MUTED);
        totalLabel.setBorder(new EmptyBorder(12, 0, 0, 0));

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(18, 0, 0, 0));
        center.add(chartPanel, BorderLayout.CENTER);
        center.add(totalLabel, BorderLayout.SOUTH);

        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }
}
