package risk.client.ui.gui.map;

import risk.common.network.Server;
import risk.common.entities.Player;
import risk.common.entities.world.Province;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapPanel extends JPanel {
    private Server server;
    private Set<ProvincePolygon> provincePolygons;
    private List<ProvinceClickListener> provinceListeners = new ArrayList<>();

    public MapPanel(Server server) {
        this.server = server;

        setPreferredSize(new Dimension(800, 600));

        setBackground(Color.BLACK);

        // add listeners
        addComponentListener(new InternalComponentAdapter());
        addMouseListener(new InternalMouseAdapter());

        refresh();
    }

    //region initialization

    public void refresh() {
        // create province polygons
        double scale = getPolygonsScale(
                (int) getPreferredSize().getWidth(),
                (int) getPreferredSize().getHeight());

        Set<Province> pp = server.getWorld().getProvinces();
        provincePolygons = new HashSet<>(pp.size());
        for (Province p : pp) {
            provincePolygons.add(new ProvincePolygon(p, scale));
        }
    }

    //endregion

    //region clicking

    private class InternalMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            ProvincePolygon p = getProvincePolygonByPoint(e.getPoint());
            if (p != null) {
                for (ProvinceClickListener l : provinceListeners) {
                    l.provinceClicked(new ProvinceClickEvent(p.getProvince()));
                }
            }
        }
    }

    private ProvincePolygon getProvincePolygonByPoint(Point point) {
        for (ProvincePolygon p : provincePolygons) {
            if (p.getPolygon().contains(point)) {
                return p;
            }
        }
        return null;
    }

    public void addProvinceClickListener(ProvinceClickListener l) {
        provinceListeners.add(l);
    }

    //endregion

    //region resizing

    private class InternalComponentAdapter extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            double scale = getPolygonsScale(getWidth(), getHeight());
            for (ProvincePolygon p : provincePolygons) {
                p.setScale(scale);
            }
        }
    }

    private double getPolygonsScale(int panelWidth, int panelHeight) {
        double worldWidth = server.getWorld().getWidth();
        double worldHeight = server.getWorld().getHeight();
        if ((double) panelWidth / panelHeight > worldWidth / worldHeight) {
            return panelHeight / worldHeight;
        } else {
            return panelWidth / worldWidth;
        }
    }

    //endregion

    //region painting

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gg = (Graphics2D) g;

        // anti-aliasing
        gg.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // fill provinces areas
        for (ProvincePolygon p : provincePolygons) {
            Player owner = p.getProvince().getOwner();
            if (owner == null) {
                gg.setColor(Color.WHITE);
            } else {
                gg.setColor(owner.getColor().getAwtColor());
            }
            gg.fill(p.getPolygon());
        }

        // draw provinces borders
        gg.setColor(Color.BLACK);
        gg.setStroke(new BasicStroke(2));
        for (ProvincePolygon p : provincePolygons) {
            gg.draw(p.getPolygon());
        }

        // draw provinces texts
        gg.setFont(new Font("default", Font.BOLD, 16));
        for (ProvincePolygon p : provincePolygons) {
            if (p.getProvince().getOwner() != null) {
                String text = String.valueOf(p.getProvince().getUnitsCount());
                int width = gg.getFontMetrics().stringWidth(text);
                int height = gg.getFontMetrics().getHeight();
                int x = (int)p.getCentroid().getX() - width / 2;
                int y = (int)p.getCentroid().getY() + height / 2;
                gg.drawString(text, x, y);
            }
        }
    }

    //endregion
}
