package webapp.model;

public class Allenatore {

	private Long id;
	private String username;
	private String password;
	private Integer crediti;

	public Allenatore() {
		super();
	}

	public Allenatore(Long id, String username, String password, Integer crediti) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.crediti = crediti;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCrediti() {
		return crediti;
	}

	public void setCrediti(Integer crediti) {
		this.crediti = crediti;
	}

	@Override
	public String toString() {
		return "Allenatore: [ID = " + id + " -- Username = " + username + " -- Password = " + password + " -- Crediti = " + crediti;
	}

}
