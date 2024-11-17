package com.estudo.jwt.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/*
 * Se for criar os usuarios pela primeira vez, cadastre manualmente no banco um adm pra si e para o cliente, depois fica a cargo do cliente
 * ou você criar os demais roles.
 */

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Usuario implements Serializable, UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	// email
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String senha;
	@Column(nullable = false, updatable = false)
	private String codigoSeguranca;
	@OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	private Role role;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.role.getAutoridades().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isPasswordCorrect(String password) {
		if(password == null || password.isBlank()) {
			throw new NullPointerException("Senha não pode ser nula ou em branco.");
		}
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        return bc.matches(password, this.senha);
    }
}
