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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.exams.mutants.dto.DnaDto;
import com.meli.exams.mutants.dto.StatsDto;
import com.meli.exams.mutants.facades.MutantDetectionFacade;

public class MutantDetectionRestControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	MutantDetectionFacade facadeMock;
	
	@InjectMocks
	private MutantDetectionRestController controller;
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	

	
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
		DnaDto mutantDnaDto = new DnaDto(new String[] { "CCCC", "CCCC", "CCCC", "CCCC" });
		when(facadeMock.isMutant(any(DnaDto.class))).thenReturn(true);
		mockMvc.perform(post("/mutant")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonMapper.writeValueAsBytes(mutantDnaDto)))
			.andExpect(status().isOk());
		verify(facadeMock, times(1)).isMutant(any(DnaDto.class));
	}
	
	@Test
	public void testMutantWithNonMutantDNA() throws Exception {
		DnaDto nonMutantDnaDto = new DnaDto(new String[] { "CTTC", "ACCC", "CCGG", "ACGC" });
		when(facadeMock.isMutant(any(DnaDto.class))).thenReturn(false);
		mockMvc.perform(post("/mutant")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonMapper.writeValueAsBytes(nonMutantDnaDto)))
			.andExpect(status().isForbidden());
		verify(facadeMock, times(1)).isMutant(any(DnaDto.class));
	}
	
	@Test
	public void testMutantWithInvalidDNA() throws Exception {
		DnaDto invalidDnaDto = new DnaDto(new String[] { "XXXX", "ACCC", "CCGG", "ACGC" });
		when(facadeMock.isMutant(any(DnaDto.class))).thenThrow(new IllegalArgumentException("oops!"));
		mockMvc.perform(post("/mutant")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonMapper.writeValueAsBytes(invalidDnaDto)))
			.andExpect(status().isForbidden());
		verify(facadeMock, times(1)).isMutant(any(DnaDto.class));
	}

	@Test
	public void testStats() throws Exception {
		StatsDto stats = new StatsDto(8, 2, 2d/8d);
		when(facadeMock.stats()).thenReturn(stats);
		
		mockMvc.perform(get("/stats"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.count_human_dna", equalTo(8)))
			.andExpect(jsonPath("$.count_mutant_dna", equalTo(2)))
			.andExpect(jsonPath("$.ratio", equalTo(2d/8d)));
		
		verify(facadeMock, times(1)).stats();
	}


}
