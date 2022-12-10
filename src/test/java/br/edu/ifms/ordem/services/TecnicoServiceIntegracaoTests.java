package br.edu.ifms.ordem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.repositories.TecnicoRepository;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;

@SpringBootTest
public class TecnicoServiceIntegracaoTests {
	
	@Autowired
	private TecnicoService service;
	
	@Autowired
	private TecnicoRepository repository;
	
	private Long idExistente;
	private Long idInexistente;
	private Long totalTecnicos;
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 2L;
		idInexistente = 100L;
		totalTecnicos = 3L;
	}
	
	@Test
	public void deleteDeveriaExcluirRegistroQuandoIdExistente(){
		
		service.delete(idExistente);
		
		Assertions.assertEquals(totalTecnicos - 1, repository.count());
	}
	
	@Test
	public void deleteDeveriaLancarExceptionQuandoIdInexistente() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idInexistente);
		});
		
	}
	
	@Test
	public void findAllPagedDeveriaRetornarPaginaQuandoPage0Size10(){
		
		PageRequest pageRequest =  PageRequest.of(0, 10);
		Page<TecnicoDTO> resultado = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(resultado.isEmpty());
		Assertions.assertEquals(0, resultado.getNumber());
		Assertions.assertEquals(10, resultado.getSize());
		Assertions.assertEquals(totalTecnicos, resultado.getTotalElements());
	}
	
	//PERGUNTA
	// Se na linha 38 foi deletado um registro no banco de dados
	// quando foi consultado na linha 56 e testado na linha 61
	// a quantidade era a mesma uma vez que a variável totalTecnicos
	// não foi alterada - ROLLBACK
	
	@Test
	public void findAllPagedDeveriaRetornarPaginaVaziaQuandoPageNaoExistir(){
		
		PageRequest pageRequest =  PageRequest.of(50, 10);
		Page<TecnicoDTO> resultado = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(resultado.isEmpty());
	}
	
	@Test
	public void findAllPagedDeveriaRetornarPaginaOrdenadaPorNome(){
		
		PageRequest pageRequest =  PageRequest.of(0, 10, Sort.by("nome"));
		Page<TecnicoDTO> resultado = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(resultado.isEmpty());
		Assertions.assertEquals("Bruno Mazeo", resultado.getContent().get(0).getNome());
		Assertions.assertEquals("Claudia Raia", resultado.getContent().get(1).getNome());
	}
	
}




