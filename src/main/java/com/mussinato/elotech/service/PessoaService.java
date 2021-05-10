package com.mussinato.elotech.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mussinato.elotech.domain.Pessoa;
import com.mussinato.elotech.repository.PessoaRepository;
import com.mussinato.elotech.service.exceptions.PessoaValidacaoException;

@Service
public class PessoaService {
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Optional<Pessoa> findById(Long id) {
		return pessoaRepository.findById(id);
	}
	
	public Page<Pessoa> findPaginated(Integer offset, Integer limit) {
		Pageable paging = PageRequest.of(offset, limit);
		return pessoaRepository.findAll(paging);
	}
	
	public Pessoa create(Pessoa pessoa) {
		validar(pessoa);
		pessoa.setId(null);
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa update(Pessoa pessoa, Long id) {
		Pessoa pessoaDb = pessoaRepository.findById(id).orElseThrow(()->new RuntimeException("Pessoa n�o encontrada"));
		pessoaDb.setNome(pessoa.getNome());
		pessoaDb.setCpf(pessoa.getCpf());
		pessoaDb.setDataNascimento(pessoa.getDataNascimento());
		pessoaDb.setContatos(pessoa.getContatos());
		
		validar(pessoaDb);
		
		return pessoaRepository.save(pessoaDb);
	}
	
	private void validar(Pessoa pessoa) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.usingContext().getValidator();
		
		String erros = "";
		
		if (pessoa.getContatos().isEmpty()) {
			erros += "A pessoa deve possuir ao menos um contato,";
		}
		
		Set<ConstraintViolation<Pessoa>> validacoes = validator.validate(pessoa);
		
		if (!validacoes.isEmpty()) {
			erros += validacoes.stream().map(a -> a.getMessage()).collect(Collectors.joining(", "));			
		}
		
		if (!erros.equals("")) {
			throw new PessoaValidacaoException(erros);
		}
	}
	
	public void delete(Pessoa pessoa) {
		pessoaRepository.delete(pessoa);
	}
	
}
