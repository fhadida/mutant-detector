package com.meli.exams.mutants.services.impl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.gson.Gson;
import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.services.MutantDetectionService;

@Service
public class MutantDetectionServiceImpl implements MutantDetectionService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MutantDetectionServiceImpl.class);
	
	private static final String MUTANT_REGEX = "(A{4}|T{4}|C{4}|G{4})";
	
	private static final Pattern MUTANT_PATTERN = Pattern.compile(MUTANT_REGEX);
	
	private static final int MUTANT_THRESHOLD = 2;
	
	private static final int MIN_REPETITIONS = 4;
	
	@Autowired
	private Datastore datastore;
	
	private KeyFactory dnaKeyFactory;
	
	
	public boolean isMutant(String[] dna) {
		validateDNA(dna);
		LOG.debug("DNA to analyze: " + Arrays.toString(dna));
		if (dna.length < MIN_REPETITIONS) {
			LOG.debug("Not a Mutant.");
			return false;
		}
		int count = searchMutant(dna);
		if (count >= MUTANT_THRESHOLD) {
			LOG.debug("It's a Mutant!");
			return true;
		} else {
			final String[] dna_t = transpose(dna);
			count += searchMutant(dna_t);
			if (count >= MUTANT_THRESHOLD) {
				LOG.debug("It's a Mutant!");
				return true;
			} else {
				final String[] dna_d1 = diagTopRightBotomLeft(dna);
				count += searchMutant(dna_d1);
				if (count >= MUTANT_THRESHOLD) {
					LOG.debug("It's a Mutant!");
					return true;
				} else {
					final String[] dna_d2 = diagTopLeftBotomRight(dna);
					count += searchMutant(dna_d2);
					if (count >= MUTANT_THRESHOLD) {
						LOG.debug("It's a Mutant!");
						return true;
					} else {
						LOG.debug("Not a Mutant.");
						return false;
					}
				}
			}
		}
	}

	private static String[] diagTopLeftBotomRight(String[] dna) {
		int n = (dna.length - MIN_REPETITIONS)*2 + 1;
		StringBuffer[] sbArr = new StringBuffer[n];
		for (int i = 0; i < n; i++) {
			int l = (int) Math.ceil((double) dna.length/2);
			int k = i < l ? 0 : i - l + 1;
			for (int j = k; j < i + MIN_REPETITIONS - k; j++) {
				int x = j,
					y = (i + MIN_REPETITIONS - 1 - j);
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
		int n = (dna.length - MIN_REPETITIONS)*2 + 1;
		StringBuffer[] sbArr = new StringBuffer[n];
		for (int i = 0; i < n; i++) {
			int l = (int) Math.ceil((double) dna.length/2);
			int k = i < l ? 0 : i - l + 1;
			for (int j = k; j < i + MIN_REPETITIONS - k; j++) {
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
				LOG.debug(String.format("The pattern '%s' was found. Chance of Mutant's DNA!", mutantMatcher.group(0)));
			}
		}
		return count;
	}

	private static void validateDNA(String[] dna) throws IllegalArgumentException {
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

	public Datastore getDatastore() {
		return datastore;
	}

	public KeyFactory getDnaKeyFactory() {
		return dnaKeyFactory;
	}

	@PostConstruct
	public void initializeKeyFactories() {
		LOG.info("Initializing key factories");
		dnaKeyFactory = getDatastore().newKeyFactory().setKind("Dna");
	}
	 
	@Override
	public Entity save(Dna dna) {
		LOG.info(String.format("Saving DNA %d...", dna.hashCode()));
		return getDatastore().put(createDnaEntity(dna));
	}

	private Entity createDnaEntity(Dna dna) {
		Gson gson = new Gson();
		Key key = getDnaKeyFactory().newKey(dna.hashCode());
		return Entity.newBuilder(key)
				.set("rawDna", gson.toJson(dna.getRawDna()))
				.set("isMutant", dna.isMutant())
				.build();
	}

}
