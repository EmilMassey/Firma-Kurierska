package mapa;

import java.awt.Point;

public class Wektor {
	private Boolean odwiedzony = false;
	private int odleglosc = Integer.MAX_VALUE;
	private Point poprzednik = null;
	
	public Wektor(Boolean odwiedzony, int odleglosc, Point poprzednik) {
		super();
		this.odwiedzony = odwiedzony;
		this.odleglosc = odleglosc;
		this.poprzednik = poprzednik;
	}
	
	public Boolean getOdwiedzony() {
		return odwiedzony;
	}
	public void setOdwiedzony(Boolean odwiedzony) {
		this.odwiedzony = odwiedzony;
	}
	public int getOdleglosc() {
		return odleglosc;
	}
	public void setOdleglosc(int odleglosc) {
		this.odleglosc = odleglosc;
	}
	public Point getPoprzednik() {
		return poprzednik;
	}
	public void setPoprzednik(Point poprzednik) {
		this.poprzednik = poprzednik;
	}
}
