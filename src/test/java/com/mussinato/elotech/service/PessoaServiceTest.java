package com.mussinato.elotech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mussinato.elotech.domain.Contato;
import com.mussinato.elotech.domain.Pessoa;
import com.mussinato.elotech.service.exceptions.PessoaValidacaoException;
import com.mussinato.elotech.service.util.PessoaAbstract;

@SpringBootTest
public class PessoaServiceTest extends PessoaAbstract {

	@Autowired
	private PessoaService pessoaService;
	
	@Test
	public void cadastrarPessoaSucesso() {
		Pessoa pessoa = criarPessoa();
		Pessoa pessoaCriada = pessoaService.create(pessoa);
		assertNotNull(pessoaCriada);
		assertNotNull(pessoaCriada.getId());
	}
	
	@Test
	public void cadastrarPessoaSemContato() {
		try {
			Pessoa pessoa = criarPessoa();
			pessoa.setContatos(new ArrayList<Contato>());
			pessoaService.create(pessoa);
			fail("Nao deve permitir cadastrar pessoa sem contato");
		} catch (PessoaValidacaoException e) {
			assertEquals("A pessoa deve possuir ao menos um contato,",e.getMessage());
		}
	}
	
	@Test
	public void cadastrarPessoaCpfInvalido() {
		try {
			Pessoa pessoa = criarPessoa();
			pessoa.setCpf("11122233388");
			pessoaService.create(pessoa);
			fail("Nao deve permitir cadastrar pessoa com cpf invalido");
		} catch (PessoaValidacaoException e) {
			assertEquals("CPF inválido", e.getMessage());
		}
	}
	
	@Test
	public void cadastrarPessoaDataNascimentoFutura() {
		try {
			Pessoa pessoa = criarPessoa();
			pessoa.setDataNascimento(LocalDate.now().plusDays(1));
			pessoaService.create(pessoa);
			fail("Nao deve permitir cadastrar pessoa com data nascimento futura");
		} catch (PessoaValidacaoException e) {
			assertEquals("Data de nascimento não pode ser futura", e.getMessage());
		}
	}
	
	@Test
	public void deletarPessoa() {
		Pessoa pessoa = criarPessoa();
		Pessoa pessoaCriada = pessoaService.create(pessoa);
		pessoaService.delete(pessoaCriada);
	}
	
	@Test
	public void alterarPessoa() {
		Pessoa pessoa = criarPessoa();
		Pessoa pessoaCriada = pessoaService.create(pessoa);
		pessoaCriada.setNome("Alterado");
		Pessoa pessoaAlterada = pessoaService.update(pessoaCriada, pessoaCriada.getId());
		assertEquals("Alterado", pessoaAlterada.getNome());
	}
}
