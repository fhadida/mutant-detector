package com.meli.exams.mutants.dto;

public class MutantReqDTO {
	
	private String[] dna;

	
	
	public MutantReqDTO() {
		super();
	}

	public MutantReqDTO(String[] dna) {
		super();
		this.dna = dna;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	

}
