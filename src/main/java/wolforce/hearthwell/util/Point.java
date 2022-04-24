package wolforce.hearthwell.util;

import java.awt.geom.Point2D;

import wolforce.hearthwell.HearthWell;

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
