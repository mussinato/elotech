package com.mussinato.elotech.service.util;

import java.time.LocalDate;
import java.util.List;

import com.mussinato.elotech.domain.Contato;
import com.mussinato.elotech.domain.Pessoa;

public abstract class PessoaAbstract {
	public Pessoa criarPessoa() {
		Pessoa pessoa = new Pessoa();
		Contato contato = new Contato();
		
		contato.setPessoa(pessoa);
		contato.setEmail("teste@teste.com");
		contato.setNome("Teste contato");
		contato.setTelefone("44666455544");
		
		pessoa.setNome("teste");
		pessoa.setCpf("565.929.970-95");
		pessoa.setDataNascimento(LocalDate.now().minusDays(1));
		pessoa.setContatos(List.of(contato));
		
		return pessoa;
	}
}
