package webapp.model;

public class Giocatore {
	private Long id;
	private String nickname;
	private String ruolo;
	private int overall;
	private int prezzo;
	private String linkFoto;
	private Boolean riserva = false;
	Squadra squadra;

	public Giocatore() {
		super();
	}
	
	public Giocatore(Long id, String nickname, String ruolo, int overall, int prezzo, String linkFoto, Boolean riserva,
			Squadra squadra) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.ruolo = ruolo;
		this.overall = overall;
		this.prezzo = prezzo;
		this.linkFoto = linkFoto;
		this.riserva = riserva;
		this.squadra = squadra;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public int getOverall() {
		return overall;
	}

	public void setOverall(int overall) {
		this.overall = overall;
	}

	public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public String getLinkFoto() {
		return linkFoto;
	}

	public void setLinkFoto(String linkFoto) {
		this.linkFoto = linkFoto;
	}

	public Boolean getRiserva() {
		return riserva;
	}

	public void setRiserva(Boolean riserva) {
		this.riserva = riserva;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	@Override
	public String toString() {
		return "Giocatore: ID = " + id + ", Nickname = " + nickname + ", Ruolo = " + ruolo + ", Overall = " + overall
				+ ", Prezzo = " + prezzo + ", Link Foto = " + linkFoto + ", Riserva = " + riserva;
	}

}
