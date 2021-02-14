package webapp.model;

public class Squadra {
	private Long id;
	private String nome;
	private int vittorie;
	private int sconfitte;
	private int punteggio;
	Allenatore allenatore;

	public Squadra() {
		super();
	}

	public Squadra(Long id, String nome, int vittorie, int sconfitte, int punteggio, Allenatore allenatore) {
		super();
		this.id = id;
		this.nome = nome;
		this.vittorie = vittorie;
		this.sconfitte = sconfitte;
		this.punteggio = punteggio;
		this.allenatore = allenatore;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getVittorie() {
		return vittorie;
	}

	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}

	public int getSconfitte() {
		return sconfitte;
	}

	public void setSconfitte(int sconfitte) {
		this.sconfitte = sconfitte;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public Allenatore getAllenatore() {
		return allenatore;
	}

	public void setAllenatore(Allenatore allenatore) {
		this.allenatore = allenatore;
	}

	@Override
	public String toString() {
		return "Squadra [id=" + id + ", nome=" + nome + ", vittorie=" + vittorie + ", sconfitte=" + sconfitte
				+ ", punteggio=" + punteggio + ", allenatore=" + allenatore + "]";
	}

}
