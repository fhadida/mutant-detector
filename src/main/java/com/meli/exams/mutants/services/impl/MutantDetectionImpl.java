package com.meli.exams.mutants.services.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meli.exams.mutants.services.MutantDetection;

public class MutantDetectionImpl implements MutantDetection {

	private static final Logger LOG = Logger.getLogger(MutantDetectionImpl.class.getName());
	
	private static final String MUTANT_REGEX = "(A{4,}|T{4,}|C{4,}|G{4,})";
	
	public boolean isMutant(String[] dna) {
//		char[][] dnaMat = new char[dna.length][];
		final Pattern mutantPattern = Pattern.compile(MUTANT_REGEX);
		boolean isValid = true;
		int count = 0;
		for (int i = 0; i < dna.length && count < 2; i++) {
			if (dna[i].length() != dna.length || !dna[i].matches("[ATCG]*")) {
				isValid = false;
				LOG.severe("Invalid DNA");
				break;
			}
			final Matcher mutantMatcher = mutantPattern.matcher(dna[i]);
			while (mutantMatcher.find()) {
				count++;
			}
		}
		if (count > 1)
			return true;
		else
			return false;
	}

}
