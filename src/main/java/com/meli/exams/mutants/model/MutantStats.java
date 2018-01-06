package com.meli.exams.mutants.model;

public class MutantStats {

	private int total = 0;
	private int mutants = 0;
	
	
	public MutantStats() {
		super();
	}

	public MutantStats(int total, int mutants) {
		this.total = total;
		this.mutants = mutants;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getMutants() {
		return mutants;
	}

	public void setMutants(int mutants) {
		this.mutants = mutants;
	}

	public int getHumans() {
		return getTotal() - getMutants();
	}
	
	public double getRatio() {
		return (double) getMutants() / (double) getHumans();
	}
}
