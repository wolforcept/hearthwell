package wolforce.hearthwell.util;

import wolforce.hearthwell.HearthWell;

import java.awt.geom.Point2D;

public class Point extends Point2D.Float {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

	public Point(float x, float y) {
		super(x, y);
	}

	public void translate(float dx, float dy) {
		x += dx;
		y += dy;
	}

}
