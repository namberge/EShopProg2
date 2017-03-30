package risk.client.ui.gui.map;

import risk.common.entities.world.Province;
import risk.common.entities.world.Point;

import java.awt.*;

public class ProvincePolygon {
    private Province province;
    private double scale;
    private Polygon polygon;
    private Point centroid;

    public ProvincePolygon(Province province, double scale) {
        this.province = province;
        this.scale = scale;
        this.polygon = calculatePolygon();
        this.centroid = calculateCentroid();
    }

    /**
     * Calculates the scaled polygon from the province points.
     * @return the polygon
     */
    private Polygon calculatePolygon() {
        Polygon polygon = new Polygon();
        for (Point point : province.getPoints()) {
            int x = (int)Math.round(point.getX() * scale);
            int y = (int)Math.round(point.getY() * scale);
            polygon.addPoint(x, y);
        }
        return polygon;
    }

    /**
     * Calculates the center of vertices for the scaled polygon.
     * @return the polygon
     */
    private Point calculateCentroid()  {
        double x = 0, y = 0;
        for (int i = 0; i < polygon.npoints; i++) {
            x += polygon.xpoints[i];
            y += polygon.ypoints[i];
        }
        return new Point(
                x / province.getPoints().size(),
                y / province.getPoints().size());
    }

    public Province getProvince() {
        return province;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        this.polygon = calculatePolygon();
        this.centroid = calculateCentroid();
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Point getCentroid() {
        return centroid;
    }
}
