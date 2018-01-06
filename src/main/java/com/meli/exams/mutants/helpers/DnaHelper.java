package com.meli.exams.mutants.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnaHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(DnaHelper.class);
	

	public static String[] diagTopLeftBotomRight(String[] dna, int offset) {
		int n = (dna.length - offset)*2 + 1;
		StringBuffer[] sbArr = new StringBuffer[n];
		for (int i = 0; i < n; i++) {
			int l = (int) Math.ceil((double) dna.length/2);
			int k = i < l ? 0 : i - l + 1;
			for (int j = k; j < i + offset - k; j++) {
				int x = j,
					y = (i + offset - 1 - j);
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

	public static String[] diagTopRightBotomLeft(String[] dna, int offset) {
		int n = (dna.length - offset)*2 + 1;
		StringBuffer[] sbArr = new StringBuffer[n];
		for (int i = 0; i < n; i++) {
			int l = (int) Math.ceil((double) dna.length/2);
			int k = i < l ? 0 : i - l + 1;
			for (int j = k; j < i + offset - k; j++) {
				int x = j,
					y = (l - 1) - (i - j);
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

	public static String[] transpose(String[] dna) {
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
	
	public static void validateDNA(String[] dna) throws IllegalArgumentException {
		String mssg = null;
		if (dna == null) {
			mssg = "Invalid DNA: Can't analyze null DNA.";
			LOG.error(mssg);
			throw new IllegalArgumentException(mssg);
		}
		for (String s : dna) {
			if (s.length() != dna.length) {
				mssg = "Invalid DNA: DNA size must be NxN.";
				LOG.error(mssg);
				throw new IllegalArgumentException(mssg);
			}
			if (!s.matches("[ATCG]*")) {
				mssg = "Invalid DNA: Does not match '[ATCG]*' pattern.";
				LOG.error(mssg);
				throw new IllegalArgumentException(mssg);
			}
		}
	}
	

}
