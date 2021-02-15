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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allenatore == null) ? 0 : allenatore.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + punteggio;
		result = prime * result + sconfitte;
		result = prime * result + vittorie;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		if (allenatore == null) {
			if (other.allenatore != null)
				return false;
		} else if (!allenatore.equals(other.allenatore))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (punteggio != other.punteggio)
			return false;
		if (sconfitte != other.sconfitte)
			return false;
		if (vittorie != other.vittorie)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Squadra [id=" + id + ", nome=" + nome + ", vittorie=" + vittorie + ", sconfitte=" + sconfitte
				+ ", punteggio=" + punteggio + ", allenatore=" + allenatore + "]";
	}

}
