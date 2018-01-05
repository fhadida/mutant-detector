package com.meli.exams.mutants.model;

import java.util.Arrays;
import java.util.Objects;

public class Dna {

	private String[] rawDna;
	private boolean isMutant;

		
	public Dna() {
		super();
	}

	public Dna(String[] rawDna, boolean isMutant) {
		this.rawDna = rawDna;
		this.isMutant = isMutant;
	}

	public String[] getRawDna() {
		return rawDna;
	}

	public void setRawDna(String[] rawDna) {
		this.rawDna = rawDna;
	}

	public boolean isMutant() {
		return isMutant;
	}

	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.toString(this.rawDna));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Dna)) return false;
		Dna other = (Dna) obj;
		return Objects.equals(this.rawDna, other.rawDna);
	}
	
	

}
