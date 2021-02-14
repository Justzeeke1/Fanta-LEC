package javafxapp.model;

public class TableSquadra {

	private String id;
	private String overall;
	private String nickname;
	private String ruolo;
	private String prezzo;

	public TableSquadra() {
		super();
	}

	public TableSquadra(String id, String overall, String nickname, String ruolo, String prezzo) {
		super();
		this.id = id;
		this.overall = overall;
		this.nickname = nickname;
		this.ruolo = ruolo;
		this.prezzo = prezzo;
	}

	public TableSquadra(String overall, String nickname, String ruolo) {
		super();
		this.overall = overall;
		this.nickname = nickname;
		this.ruolo = ruolo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
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

	public String getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}

	@Override
	public String toString() {
		return "TableSquadra [overall=" + overall + ", nickname=" + nickname + ", ruolo=" + ruolo + "]";
	}

}
