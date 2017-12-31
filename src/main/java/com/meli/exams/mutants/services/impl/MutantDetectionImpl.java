package com.meli.exams.mutants.services.impl;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meli.exams.mutants.services.MutantDetection;

public class MutantDetectionImpl implements MutantDetection {

	private static final Logger LOG = Logger.getLogger(MutantDetectionImpl.class.getName());
	
	private static final String MUTANT_REGEX = "(A{4,}|T{4,}|C{4,}|G{4,})";
	private static final Pattern MUTANT_PATTERN = Pattern.compile(MUTANT_REGEX);
	private static final int MUTANT_THRESHOLD = 2;
	
	public boolean isMutant(String[] dna) {
		validateDNA(dna);
		int count = searchMutant(dna);
		if (count >= MUTANT_THRESHOLD)
			return true;
		else {
			final String[] dna_t = transpose(dna);
			count += searchMutant(dna_t);
			if (count >= MUTANT_THRESHOLD)
				return true;
			else {
				LOG.info("DNA: " + Arrays.toString(dna));
				final String[] dna_d1 = diagTopRightBotomLeft(dna);
				LOG.info("DNA D1: " + Arrays.toString(dna_d1));
				count += searchMutant(dna_d1);
				if (count >= MUTANT_THRESHOLD)
					return true;
				else {
					final String[] dna_d2 = diagTopLeftBotomRight(dna);
					count += searchMutant(dna_d2);
					if (count >= MUTANT_THRESHOLD)
						return true;
					else
						return false;
				}
			}
		}
	}

	private static String[] diagTopLeftBotomRight(String[] dna) {
		StringBuffer[] sbArr = new StringBuffer[dna.length*2 - 1];
		for (int i = 0; i < sbArr.length; i++) {
			int k = i < dna.length ? 0 : i - dna.length + 1;
			for (int j = k; j <= i - k; j++) {
				int x = j,
					y = (i - j);
				if (sbArr[i] == null)
					sbArr[i] = new StringBuffer();
				sbArr[i].append(dna[x].charAt(y));
			}
		}
		String[] result = new String[sbArr.length]; 
		for (int i = 0; i < result.length; i++) {
			result[i] = sbArr[i].toString();
		}
		return result;
	}

	private static String[] diagTopRightBotomLeft(String[] dna) {
		StringBuffer[] sbArr = new StringBuffer[dna.length*2 - 1];
		for (int i = 0; i < sbArr.length; i++) {
			int k = i < dna.length ? 0 : i - dna.length + 1;
			for (int j = k; j <= i - k; j++) {
				int x = j,
					y = (dna.length - 1) - (i - j);
				if (sbArr[i] == null)
					sbArr[i] = new StringBuffer();
				sbArr[i].append(dna[x].charAt(y));
			}
		}
		String[] result = new String[sbArr.length]; 
		for (int i = 0; i < result.length; i++) {
			result[i] = sbArr[i].toString();
		}
		return result;
	}

	private static String[] transpose(String[] dna) {
		StringBuffer[] sbArr = new StringBuffer[dna.length];
		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j < dna[i].length(); j++) {
				if (sbArr[i] == null)
					sbArr[i] = new StringBuffer();
				sbArr[i].append(dna[j].charAt(i));
			}
		}
		String[] result = new String[sbArr.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = sbArr[i].toString();
		}
		return result;
	}

	private static int searchMutant(String[] dna) {
		int count = 0;
		for (int i = 0; i < dna.length && count < MUTANT_THRESHOLD; i++) {
			final Matcher mutantMatcher = MUTANT_PATTERN.matcher(dna[i]);
			while (mutantMatcher.find()) {
				count++;
			}
		}
		return count;
	}

	private static void validateDNA(String[] dna) throws IllegalArgumentException {
		for (String s : dna) {
			if (s.length() != dna.length || !s.matches("[ATCG]*")) {
				throw new IllegalArgumentException("Invalid DNA");
			}
		}
		
	}

}
