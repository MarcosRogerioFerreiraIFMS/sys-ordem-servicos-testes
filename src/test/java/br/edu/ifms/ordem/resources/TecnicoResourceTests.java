package br.edu.ifms.ordem.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.services.TecnicoService;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;
import br.edu.ifms.ordem.tests.Factory;

@WebMvcTest(TecnicoResource.class)
public class TecnicoResourceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TecnicoService service;
	
	private PageImpl<TecnicoDTO> page;
	private TecnicoDTO tecnicoDTO;
	private Long idExistente;
	private Long idInexistente;
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idInexistente = 100L;
		
		tecnicoDTO = Factory.novoTecnicoDTO();
		page = new PageImpl<>(List.of(tecnicoDTO));
		
		when(service.findAllPaged(any())).thenReturn(page);
		
		//findById(id)
		when(service.findById(idExistente)).thenReturn(tecnicoDTO);
		when(service.findById(idInexistente)).thenThrow(ResourceNotFoundException.class);
	}
	
	
	@Test
	public void findAllDeveriaRetornarPaginaTecnicoDTO() throws Exception {
		
		// mockMvc.perform(get("/tecnicos")).andExpect(status().isOk());
		
		ResultActions resultado = mockMvc.perform(get("/tecnicos")
			   .accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isOk());
		
	}
}



