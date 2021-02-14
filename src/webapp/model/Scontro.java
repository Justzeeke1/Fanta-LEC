package webapp.model;

public class Scontro {
	private Squadra primaSquadra;
	private Squadra secondaSquadra;

	public Scontro() {
		super();
	}

	public Scontro(Squadra primaSquadra, Squadra secondaSquadra) {
		super();
		this.primaSquadra = primaSquadra;
		this.secondaSquadra = secondaSquadra;
	}

	public Squadra getPrimaSquadra() {
		return primaSquadra;
	}

	public void setPrimaSquadra(Squadra primaSquadra) {
		this.primaSquadra = primaSquadra;
	}

	public Squadra getSecondaSquadra() {
		return secondaSquadra;
	}

	public void setSecondaSquadra(Squadra secondaSquadra) {
		this.secondaSquadra = secondaSquadra;
	}

	@Override
	public String toString() {
		return "Scontro [primaSquadra=" + primaSquadra + ", secondaSquadra=" + secondaSquadra + "]";
	}

}
