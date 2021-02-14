package javafxapp.model;

public class TableClassifica {

	private String posizione;
	private String nomeSquadra;
	private String punteggio;
	private String vittorie;
	private String sconfitte;

	public TableClassifica() {
		super();
	}

	public TableClassifica(String posizione, String nomeSquadra, String punteggio, String vittorie, String sconfitte) {
		super();
		this.posizione = posizione;
		this.nomeSquadra = nomeSquadra;
		this.punteggio = punteggio;
		this.vittorie = vittorie;
		this.sconfitte = sconfitte;
	}

	public String getPosizione() {
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}

	public String getNomeSquadra() {
		return nomeSquadra;
	}

	public void setNomeSquadra(String nomeSquadra) {
		this.nomeSquadra = nomeSquadra;
	}

	public String getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(String punteggio) {
		this.punteggio = punteggio;
	}

	public String getVittorie() {
		return vittorie;
	}

	public void setVittorie(String vittorie) {
		this.vittorie = vittorie;
	}

	public String getSconfitte() {
		return sconfitte;
	}

	public void setSconfitte(String sconfitte) {
		this.sconfitte = sconfitte;
	}

	@Override
	public String toString() {
		return "TableClassifica [posizione=" + posizione + ", nomeSquadra=" + nomeSquadra + ", punteggio=" + punteggio
				+ ", vittorie=" + vittorie + ", sconfitte=" + sconfitte + "]";
	}

}
