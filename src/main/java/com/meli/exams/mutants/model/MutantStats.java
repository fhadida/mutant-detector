package com.meli.exams.mutants.model;

public class MutantStats {

	private long total = 0;
	private long mutants = 0;
	
	
	public MutantStats() {
		super();
	}

	public MutantStats(long total, long mutants) {
		this.total = total;
		this.mutants = mutants;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getMutants() {
		return mutants;
	}

	public void setMutants(long mutants) {
		this.mutants = mutants;
	}

	public long getHumans() {
		return getTotal() - getMutants();
	}
	
	public double getRatio() {
		if (getHumans() == 0)
			return Double.POSITIVE_INFINITY;
		return (double) getMutants() / (double) getHumans();
	}
}
