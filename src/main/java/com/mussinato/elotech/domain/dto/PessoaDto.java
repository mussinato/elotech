package com.mussinato.elotech.domain.dto;

import com.mussinato.elotech.domain.Pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PessoaDto {
	private Pessoa pessoa;
	private String mensagem;
}
