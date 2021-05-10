package com.mussinato.elotech.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "pessoa")
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank(message = "CPF é obrigatório")
	@Column(nullable = false, length = 14)
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotNull(message = "Data de nascimento é obrigatória")
	@Past(message = "Data de nascimento não pode ser futura")
	@Column(nullable = false)
	private LocalDate dataNascimento;
	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Contato> contatos = new ArrayList<Contato>();
}
