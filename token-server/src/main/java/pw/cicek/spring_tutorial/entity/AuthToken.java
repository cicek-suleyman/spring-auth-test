package pw.cicek.spring_tutorial.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "auth_token")
public class AuthToken extends BaseEntity {
	@Column(unique = true)
	private String token;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "username")
	@JsonIdentityReference(alwaysAsId = true)
	private User user;

	public AuthToken() {
		super();
	}

	public AuthToken(String token, User user) {
		super();

		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
