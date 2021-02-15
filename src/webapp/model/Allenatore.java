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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crediti == null) ? 0 : crediti.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Allenatore other = (Allenatore) obj;
		if (crediti == null) {
			if (other.crediti != null)
				return false;
		} else if (!crediti.equals(other.crediti))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Allenatore: [ID = " + id + " -- Username = " + username + " -- Password = " + password + " -- Crediti = " + crediti;
	}

}
