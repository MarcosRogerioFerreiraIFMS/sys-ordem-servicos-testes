package br.edu.ifms.ordem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ifms.ordem.repositories.TecnicoRepository;
import br.edu.ifms.ordem.services.exceptions.DataBaseException;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class TecnicoServiceTests {
	
	@InjectMocks
	private TecnicoService service;
	
	@Mock
	private TecnicoRepository repository;
	
	private Long idExistente;
	private Long idInexistente;
	private Long idDependente;
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idInexistente = 100L;
		idDependente = 10L;
		
		//Configurar comportamento
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(idInexistente);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(idDependente);
		
	}
	
	@Test
	public void deleteDeveriaExcluirRegistroQuandoIdExistir() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
		
	}
	
	@Test
	public void deleteShouldThrowExceptionWhenIdInexistente() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idInexistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idInexistente);
		
	}
	
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependente() {
		
		Assertions.assertThrows(DataBaseException.class, () -> {
			service.delete(idDependente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idDependente);
		
	}

}





