package com.meli.exams.mutants.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.meli.exams.mutants.dto.DnaDto;
import com.meli.exams.mutants.dto.StatsDto;
import com.meli.exams.mutants.facades.MutantDetectionFacade;

public class MutantDetectionRestControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock MutantDetectionFacade facade;
	
	@InjectMocks MutantDetectionRestController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMutantWithMutantDNA() throws Exception {
		when(facade.isMutant(any(DnaDto.class))).thenReturn(Boolean.TRUE);
		mockMvc.perform(post("/mutant"))
			.andExpect(status().isOk());
		verify(facade, times(1)).isMutant(any(DnaDto.class));
	}
	
	@Test
	public void testMutantWithNonMutantDNA() throws Exception {
		when(facade.isMutant(any(DnaDto.class))).thenReturn(Boolean.FALSE);
		mockMvc.perform(post("/mutant"))
			.andExpect(status().isForbidden());
		verify(facade, times(1)).isMutant(any(DnaDto.class));
	}

	@Test
	public void testStats() throws Exception {
		StatsDto stats = new StatsDto(8, 2, 2d/8d);
		when(facade.stats()).thenReturn(stats);
		
		mockMvc.perform(get("/stats"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.count_human_dna", equalTo(8)))
			.andExpect(jsonPath("$.count_mutant_dna", equalTo(2)))
			.andExpect(jsonPath("$.ratio", equalTo(2d/8d)));
		
		verify(facade, times(1)).stats();
	}


}
