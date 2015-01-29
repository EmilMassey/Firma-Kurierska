package obiekty;

import java.awt.Point;

public class Nieruchomosc {
	private int id;
	private String ulica;
	private int numer;
	private Point wspolrzedne;
	
	public Nieruchomosc(int id, String ulica, int numer,
			int x, int y) {
		this.id = id;
		this.ulica = ulica;
		this.numer = numer;
		this.wspolrzedne = new Point(x, y);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public int getNumer() {
		return numer;
	}

	public void setNumer(int numer) {
		this.numer = numer;
	}

	public Point getWspolrzedne() {
		return wspolrzedne;
	}

	public void setWspolrzedne(int x, int y) {
		this.wspolrzedne = new Point(x, y);
	}
}
