package com.estudo.jwt.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
public class Role implements Serializable{
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "role_autoridades", joinColumns =  @JoinColumn(name = "role_id"))
	@Column(name="autoridade")
	private Set<String> autoridades;

}
