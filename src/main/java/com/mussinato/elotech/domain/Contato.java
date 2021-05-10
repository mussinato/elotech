package com.mussinato.elotech.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "contato")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Contato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(name = "contato_fk_pessoa"), nullable = false)
	@JsonBackReference
	private Pessoa pessoa;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank(message = "Telefone é obrigatório")
	@Column(nullable = false)
	private String telefone;
	
	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	@Column(nullable = false)
	private String email;
}
