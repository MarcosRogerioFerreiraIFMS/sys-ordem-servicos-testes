package br.edu.ifms.ordem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ifms.ordem.repositories.TecnicoRepository;

@ExtendWith(SpringExtension.class)
public class TecnicoServiceTests {
	
	@InjectMocks
	private TecnicoService service;
	
	@Mock
	private TecnicoRepository repository;
	
	private Long idExistente;
	private Long idInexistente;
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idInexistente = 100L;
	}
	
	@Test
	public void deleteDeveriaExcluirRegistroQuandoIdExistir() {
		
		service.delete(idExistente);
		
	}

}





