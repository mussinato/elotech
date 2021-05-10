package com.mussinato.elotech.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mussinato.elotech.domain.Pessoa;
import com.mussinato.elotech.domain.dto.PessoaDto;
import com.mussinato.elotech.service.PessoaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pessoas")
public class PessoaRest {
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable("id") Long id){
		Optional<Pessoa> pessoa = pessoaService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(pessoa.stream().findAny().orElse(null));
	}
	
	@GetMapping
	public ResponseEntity<Page<Pessoa>> findAll(@RequestParam(name = "offset", required = true, defaultValue = "0") Integer offset, @RequestParam(name = "limit", required = true) Integer limit){
		Page<Pessoa> pessoas = pessoaService.findPaginated(offset, limit);
		return ResponseEntity.status(HttpStatus.OK).body(pessoas);		
	}
	
	@PostMapping
	public ResponseEntity<PessoaDto> create(@RequestBody Pessoa pessoa){
		try {
			pessoa = pessoaService.create(pessoa);
			return ResponseEntity.status(HttpStatus.CREATED).body(new PessoaDto(pessoa, null));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PessoaDto(null, e.getMessage()));
		}		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@RequestBody Pessoa pessoa, @PathVariable("id") Long id){
		try {
			pessoa = pessoaService.update(pessoa,id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id){
		try {
			Optional<Pessoa> pessoa = pessoaService.findById(id);
			if (pessoa.isPresent()) {
				pessoaService.delete(pessoa.get());
				return ResponseEntity.noContent().build();
			}
			throw new RuntimeException("Pessoa n�o encontrada");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}		
	}
}
