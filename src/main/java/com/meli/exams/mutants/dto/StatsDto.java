package com.meli.exams.mutants.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "count_mutant_dna", "count_human_dna", "ratio" })
public class StatsDto {

	private long humans;
	private long mutants;
	private double ratio;

	
	public StatsDto() {
		super();
	}

	public StatsDto(long humans, long mutants, double ratio) {
		this.humans = humans;
		this.mutants = mutants;
		this.ratio = ratio;
	}

	@JsonProperty("count_human_dna")
	public long getHumans() {
		return humans;
	}

	public void setHumans(long humans) {
		this.humans = humans;
	}

	@JsonProperty("count_mutant_dna")
	public long getMutants() {
		return mutants;
	}

	public void setMutants(long mutants) {
		this.mutants = mutants;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
