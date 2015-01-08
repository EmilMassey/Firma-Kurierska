package obiekty;

public class Przesylka {
	int id;
	int typ;
	int waga;
	boolean dostarczona;
	
	public Przesylka(int id, int typ, int waga, boolean dostarczona) {
		this.id = id;
		this.typ = typ;
		this.waga = waga;
		this.dostarczona = false;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTyp() {
		return typ;
	}
	public void setTyp(int typ) {
		this.typ = typ;
	}
	public int getWaga() {
		return waga;
	}
	public void setWaga(int waga) {
		this.waga = waga;
	}
	public boolean isDostarczona() {
		return dostarczona;
	}
	public void setDostarczona(boolean dostarczona) {
		this.dostarczona = dostarczona;
	}
}
