package jarak;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ConvertPage extends JFrame {

    private String unit;
    private JFrame parentFrame;
    private JTextField inputField;
    private JLabel resultLabel1, resultLabel2, resultLabel3;
    private JLabel unitResult1, unitResult2, unitResult3;

    // Colors per unit
    private Color[] unitColors = {
        new Color(255, 107, 107), new Color(255, 75, 75),   // MM - red
        new Color(78, 205, 196),  new Color(46, 173, 164),  // CM - teal
        new Color(69, 183, 209),  new Color(37, 151, 177),  // M  - blue
        new Color(150, 206, 180), new Color(118, 174, 148)  // KM - green
    };

    private String[] allUnits = {"MM", "CM", "M", "KM"};
    private String[] fullNames = {"Mili Meter", "Centi Meter", "Meter", "Kilo Meter"};

    public ConvertPage(String unit, JFrame parent) {
        this.unit = unit;
        this.parentFrame = parent;
        initComponents();
        
        setAppIcon();
    }
    
    private void setAppIcon() {
        setIconImage(new ImageIcon(
                getClass().getResource("logo.png")
        ).getImage());
    }

    private Color getColorFrom() {
        switch (unit) {
            case "MM": return unitColors[0];
            case "CM": return unitColors[2];
            case "M":  return unitColors[4];
            case "KM": return unitColors[6];
            default:   return unitColors[0];
        }
    }

    private Color getColorTo() {
        switch (unit) {
            case "MM": return unitColors[1];
            case "CM": return unitColors[3];
            case "M":  return unitColors[5];
            case "KM": return unitColors[7];
            default:   return unitColors[1];
        }
    }

    private String getFullName() {
        switch (unit) {
            case "MM": return "Mili Meter";
            case "CM": return "Centi Meter";
            case "M":  return "Meter";
            case "KM": return "Kilo Meter";
            default:   return unit;
        }
    }

    private String[] getOtherUnits() {
        String[] others = new String[3];
        int idx = 0;
        for (String u : allUnits) {
            if (!u.equals(unit)) others[idx++] = u;
        }
        return others;
    }

    private String getFullNameOf(String u) {
        switch (u) {
            case "MM": return "Mili Meter";
            case "CM": return "Centi Meter";
            case "M":  return "Meter";
            case "KM": return "Kilo Meter";
            default:   return u;
        }
    }

    private void initComponents() {
        setTitle(unit + " -  Menghitung " + getFullName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(460, 620);
        setLocationRelativeTo(null);
        setResizable(false);

        Color colorFrom = getColorFrom();
        Color colorTo   = getColorTo();
        String[] others = getOtherUnits();

        // Main panel
        JPanel mainPanel = new MainPage.GradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        // Back button row
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JButton backBtn = new JButton("← Kembali");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backBtn.setForeground(Color.WHITE);
        backBtn.setOpaque(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        backBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                backBtn.setForeground(colorFrom);
            }
            @Override public void mouseExited(MouseEvent e) {
                backBtn.setForeground(Color.WHITE);
            }
        });

        topRow.add(backBtn, BorderLayout.WEST);

        // Unit badge
        JLabel badgeLabel = new JLabel(unit);
        badgeLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        badgeLabel.setForeground(Color.WHITE);
        badgeLabel.setOpaque(true);
        badgeLabel.setBackground(colorFrom);
        badgeLabel.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        topRow.add(badgeLabel, BorderLayout.EAST);

        JLabel titleLabel = new JLabel(getFullName() + " Converter");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Masukkan nilai dalam " + unit + " untuk dikonversi");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(topRow);
        headerPanel.add(Box.createVerticalStrut(12));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(subtitleLabel);

        // --- INPUT AREA ---
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JLabel inputLabelText = new JLabel("Nilai " + unit + " :");
        inputLabelText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        inputLabelText.setForeground(new Color(200, 220, 255));
        inputLabelText.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputField = new JTextField();
        inputField.setFont(new Font("Georgia", Font.BOLD, 26));
        inputField.setForeground(Color.WHITE);
        inputField.setBackground(new Color(255, 255, 255, 30));
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(12, colorFrom),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setOpaque(false);
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        JLabel unitHint = new JLabel(unit, SwingConstants.CENTER);
        unitHint.setFont(new Font("Segoe UI", Font.BOLD, 11));
        unitHint.setForeground(colorFrom);
        unitHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton convertBtn = new JButton("Konversi") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, colorFrom, getWidth(), getHeight(), colorTo);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        convertBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        convertBtn.setForeground(Color.WHITE);
        convertBtn.setOpaque(false);
        convertBtn.setContentAreaFilled(false);
        convertBtn.setBorderPainted(false);
        convertBtn.setFocusPainted(false);
        convertBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        convertBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        convertBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        convertBtn.addActionListener(e -> doConvert(others));

        // Allow Enter key to trigger convert
        inputField.addActionListener(e -> doConvert(others));

        inputPanel.add(inputLabelText);
        inputPanel.add(Box.createVerticalStrut(6));
        inputPanel.add(inputField);
        inputPanel.add(Box.createVerticalStrut(4));
        inputPanel.add(unitHint);
        inputPanel.add(Box.createVerticalStrut(14));
        inputPanel.add(convertBtn);

        // --- RESULTS AREA ---
        JPanel resultsPanel = new JPanel();
        resultsPanel.setOpaque(false);
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(6, 40, 20, 40));

        JLabel resultHeader = new JLabel("Hasil Konversi");
        resultHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultHeader.setForeground(new Color(200, 220, 255));
        resultHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(resultHeader);
        resultsPanel.add(Box.createVerticalStrut(10));

        // 3 result cards
        JPanel[] cards = new JPanel[3];
        resultLabel1 = new JLabel("-", SwingConstants.CENTER);
        resultLabel2 = new JLabel("-", SwingConstants.CENTER);
        resultLabel3 = new JLabel("-", SwingConstants.CENTER);

        unitResult1 = new JLabel(getFullNameOf(others[0]), SwingConstants.CENTER);
        unitResult2 = new JLabel(getFullNameOf(others[1]), SwingConstants.CENTER);
        unitResult3 = new JLabel(getFullNameOf(others[2]), SwingConstants.CENTER);

        JLabel[] resultLabels = {resultLabel1, resultLabel2, resultLabel3};
        JLabel[] unitResults  = {unitResult1, unitResult2, unitResult3};

        Color[] cardColors = {
            new Color(255, 255, 255, 25),
            new Color(255, 255, 255, 20),
            new Color(255, 255, 255, 15)
        };

        for (int i = 0; i < 3; i++) {
            final int idx = i;
            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(cardColors[idx]);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                    g2.dispose();
                }
            };
            card.setOpaque(false);
            card.setLayout(new BorderLayout(10, 0));
            card.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Left: unit badge
            JLabel badgeLbl = new JLabel(others[i]);
            badgeLbl.setFont(new Font("Georgia", Font.BOLD, 16));
            badgeLbl.setForeground(colorFrom);
            badgeLbl.setPreferredSize(new Dimension(40, 40));
            badgeLbl.setHorizontalAlignment(SwingConstants.LEFT);

            // Center: full name
            unitResults[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            unitResults[i].setForeground(new Color(180, 200, 240));
            unitResults[i].setHorizontalAlignment(SwingConstants.LEFT);

            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.setOpaque(false);
            leftPanel.add(badgeLbl, BorderLayout.NORTH);
            leftPanel.add(unitResults[i], BorderLayout.SOUTH);

            // Right: result value
            resultLabels[i].setFont(new Font("Georgia", Font.BOLD, 20));
            resultLabels[i].setForeground(Color.WHITE);
            resultLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);

            card.add(leftPanel, BorderLayout.WEST);
            card.add(resultLabels[i], BorderLayout.EAST);

            resultsPanel.add(card);
            if (i < 2) resultsPanel.add(Box.createVerticalStrut(8));
        }

        // Combine input + results in scroll center
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(inputPanel);
        centerPanel.add(resultsPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parentFrame.setVisible(true);
            }
        });
    }

    private void doConvert(String[] others) {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            resultLabel1.setText("-");
            resultLabel2.setText("-");
            resultLabel3.setText("-");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(text.replace(",", "."));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Input tidak valid! Masukkan angka yang benar.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert input to base unit: MM
        double inMM = toMM(value, unit);

        // Convert MM to each target unit
        JLabel[] labels = {resultLabel1, resultLabel2, resultLabel3};
        DecimalFormat df = new DecimalFormat("#,##0.##########");

        for (int i = 0; i < 3; i++) {
            double result = fromMM(inMM, others[i]);
            labels[i].setText(df.format(result) + " " + others[i]);
        }
    }

    private double toMM(double val, String fromUnit) {
        switch (fromUnit) {
            case "MM": return val;
            case "CM": return val * 10;
            case "M":  return val * 1000;
            case "KM": return val * 1_000_000;
            default:   return val;
        }
    }

    private double fromMM(double mm, String toUnit) {
        switch (toUnit) {
            case "MM": return mm;
            case "CM": return mm / 10;
            case "M":  return mm / 1000;
            case "KM": return mm / 1_000_000;
            default:   return mm;
        }
    }

    // Custom rounded border helper
    static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color  = color;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x+1, y+1, w-2, h-2, radius, radius);
            g2.dispose();
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius/2, radius/2, radius/2, radius/2);
        }
    }
}