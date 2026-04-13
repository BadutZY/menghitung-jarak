package jarak;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MainPage extends JFrame {

    public MainPage() {
        initComponents();
        
        setAppIcon();
    }

    private void initComponents() {
        setTitle("Menghitung Jarak - Rizky Mario Revan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 20, 30));

        // Icon label (ruler symbol)
        JLabel iconLabel = new JLabel("📏", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Menghiung Jarak");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Pilih satuan yang ingin dikonversi");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(subtitleLabel);

        // Grid panel for 4 buttons
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Create 4 unit buttons
        JButton btnMM = createUnitButton("MM", "Mili Meter", new Color(255, 107, 107), new Color(255, 75, 75));
        JButton btnCM = createUnitButton("CM", "Centi Meter", new Color(78, 205, 196), new Color(46, 173, 164));
        JButton btnM  = createUnitButton("M",  "Meter",       new Color(69, 183, 209), new Color(37, 151, 177));
        JButton btnKM = createUnitButton("KM", "Kilo Meter",  new Color(150, 206, 180), new Color(118, 174, 148));

        btnMM.addActionListener(e -> openPage("MM"));
        btnCM.addActionListener(e -> openPage("CM"));
        btnM.addActionListener(e  -> openPage("M"));
        btnKM.addActionListener(e -> openPage("KM"));

        gridPanel.add(btnMM);
        gridPanel.add(btnCM);
        gridPanel.add(btnM);
        gridPanel.add(btnKM);

        // Footer
        JLabel footerLabel = new JLabel("© 2026 BadutZY, SwimmingFOX, Revan Dwi E", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(160, 180, 220));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
    
       private void setAppIcon() {
        setIconImage(new ImageIcon(
                getClass().getResource("logo.png")
        ).getImage());
    }

    private JButton createUnitButton(String unit, String desc, Color colorFrom, Color colorTo) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, colorFrom, getWidth(), getHeight(), colorTo);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setLayout(new BorderLayout());
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 110));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel unitLabel = new JLabel(unit, SwingConstants.CENTER);
        unitLabel.setFont(new Font("Georgia", Font.BOLD, 32));
        unitLabel.setForeground(Color.WHITE);
        unitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel(desc, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(new Color(255, 255, 255, 200));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalGlue());
        content.add(unitLabel);
        content.add(Box.createVerticalStrut(4));
        content.add(descLabel);
        content.add(Box.createVerticalGlue());

        btn.add(content, BorderLayout.CENTER);

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBorder(null);
            }
        });

        return btn;
    }

    private void openPage(String unit) {
        ConvertPage page = new ConvertPage(unit, this);
        page.setVisible(true);
        this.setVisible(false);
    }

    // Gradient background panel
    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(15, 32, 80),
                    0, getHeight(), new Color(20, 60, 120));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new MainPage().setVisible(true));
    }
}