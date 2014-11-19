package komunikacja;

public class Obiekt {
	private String nazwa;
	private Boolean mnoga;
	private Boolean istnieje = true;
	
	public Obiekt(String nazwa, String mnoga) {
		this.nazwa = nazwa;
		this.mnoga = Boolean.valueOf(mnoga);
	}
	
	public Obiekt(Boolean istnieje) {
		this.istnieje = istnieje;
	}
	
	public String getNazwa() { return this.nazwa; }
	public Boolean getMnoga() { return this.mnoga; }
	public Boolean czyIstnieje() { return istnieje; }
}
