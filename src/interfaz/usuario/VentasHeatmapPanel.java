package interfaz.usuario;

import logica.Galeria;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Map;

public class VentasHeatmapPanel extends JPanel {
    private Map<String, Integer> ventasPorDia;

    public VentasHeatmapPanel(Galeria galeria) {
        this.ventasPorDia = galeria.getVentasPorDia();
        setPreferredSize(new Dimension(1200, 800));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawCalendars(g2d);
    }

    private void drawCalendars(Graphics2D g2d) {
        int cellSize = 30;
        int padding = 5;
        int startX = padding;
        int startY = padding;
        int calendarWidth = (cellSize + padding) * 7;
        int calendarHeight = (cellSize + padding) * 6;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2024);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        for (int month = 0; month < 12; month++) {
            cal.set(Calendar.MONTH, month);
            drawMonth(g2d, cal, startX, startY, cellSize, padding);
            startX += calendarWidth + padding * 2;
            if (startX + calendarWidth > getWidth()) {
                startX = padding;
                startY += calendarHeight + padding * 2 + 20;
            }
        }
    }

    private void drawMonth(Graphics2D g2d, Calendar cal, int startX, int startY, int cellSize, int padding) {
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        // Draw month name
        g2d.setColor(Color.BLACK);
        g2d.drawString(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale()), startX, startY);

        // Draw days of week
        for (int i = 0; i < daysOfWeek.length; i++) {
            g2d.drawString(daysOfWeek[i], startX + i * (cellSize + padding), startY + 20);
        }

        // Draw day cells
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int x = startX + dayOfWeek * (cellSize + padding);
        int y = startY + 40;

        for (int day = 1; day <= daysInMonth; day++) {
            String dateString = String.format("2024-%02d-%02d", cal.get(Calendar.MONTH) + 1, day);
            int ventas = ventasPorDia.getOrDefault(dateString, 0);

            g2d.setColor(getColorForVentas(ventas));
            g2d.fillRect(x, y, cellSize, cellSize);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, cellSize, cellSize);
            g2d.drawString(String.valueOf(day), x + 2, y + 12);

            dayOfWeek++;
            if (dayOfWeek == 7) {
                dayOfWeek = 0;
                x = startX;
                y += cellSize + padding;
            } else {
                x += cellSize + padding;
            }
        }
    }

    private Color getColorForVentas(int ventas) {
        if (ventas == 0) return Color.WHITE;
        if (ventas == 1) return new Color(198, 239, 206);
        if (ventas <= 5) return new Color(123, 201, 111);
        if (ventas <= 10) return new Color(35, 154, 59);
        return new Color(0, 104, 34);
    }

    public static void main(String[] args) {
        Galeria galeria = new Galeria();
        galeria.cargarGaleria();

        JFrame frame = new JFrame("Ventas Heatmap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new VentasHeatmapPanel(galeria));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
