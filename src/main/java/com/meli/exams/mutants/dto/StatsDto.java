package com.meli.exams.mutants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "count_mutant_dna", "count_human_dna", "ratio" })
public class StatsDto {

	private int humans;
	private int mutants;
	private double ratio;

	
	public StatsDto() {
		super();
	}

	public StatsDto(int humans, int mutants, double ratio) {
		this.humans = humans;
		this.mutants = mutants;
		this.ratio = ratio;
	}

	@JsonProperty("count_human_dna")
	public int getHumans() {
		return humans;
	}

	public void setHumans(int humans) {
		this.humans = humans;
	}

	@JsonProperty("count_mutant_dna")
	public int getMutants() {
		return mutants;
	}

	public void setMutants(int mutants) {
		this.mutants = mutants;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
