package com.meli.exams.mutants.services.impl;

import java.util.logging.Logger;

import com.meli.exams.mutants.services.MutantDetection;

public class MutantDetectionImpl implements MutantDetection {

	private static Logger LOG = Logger.getLogger(MutantDetectionImpl.class.getName());
	
	public boolean isMutant(String[] dna) {
		char[][] dnaMat = new char[dna.length][];
		boolean isValid = true;
		for (int i = 0; i < dna.length; i++) {
			if (dna[i].length() != dna.length && !dna[i].matches("[ATCG]*")) {
				isValid = false;
				LOG.severe("Invalid DNA");
				break;
			}
			dnaMat[i] = dna[i].toCharArray();
		}
		char candidateH,
			candidateV,
			candidateD1,
			candidateD2 = dnaMat[0][0];
		
		int count = 0;
		int detectionCount = 0;
		if (!isValid) return false;
		for (int i = 0; i < dnaMat.length; i++) {
			for (int j = 0; j < dnaMat[i].length; j++) {
				
			}
		}
		return false;
	}

}
