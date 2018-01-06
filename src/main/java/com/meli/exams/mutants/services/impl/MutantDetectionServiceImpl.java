package com.meli.exams.mutants.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.exams.mutants.dao.DnaDao;
import com.meli.exams.mutants.helpers.DnaHelper;
import com.meli.exams.mutants.model.Dna;
import com.meli.exams.mutants.model.MutantStats;
import com.meli.exams.mutants.services.MutantDetectionService;

@Service
public class MutantDetectionServiceImpl implements MutantDetectionService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MutantDetectionServiceImpl.class);
	
	private static final String MUTANT_REGEX = "(A{4}|T{4}|C{4}|G{4})";
	private static final Pattern MUTANT_PATTERN = Pattern.compile(MUTANT_REGEX);
	private static final int MUTANT_THRESHOLD = 2;
	private static final int MIN_REPETITIONS = 4;
	
	private DnaDao dnaDao;
	
	
	public DnaDao getDnaDao() {
		return dnaDao;
	}

	@Autowired
	public void setDnaDao(DnaDao dnaDao) {
		this.dnaDao = dnaDao;
	}

	public boolean isMutant(String[] dna) {
		DnaHelper.validateDNA(dna);
		LOG.debug("DNA to analyze: " + Arrays.toString(dna));
		if (dna.length < MIN_REPETITIONS) {
			LOG.debug("Not a Mutant.");
			return false;
		}
		int count = findMutantDnaPattern(dna);
		if (count >= MUTANT_THRESHOLD) {
			LOG.debug("It's a Mutant!");
			return true;
		} else {
			final String[] dna_t = DnaHelper.transpose(dna);
			count += findMutantDnaPattern(dna_t);
			if (count >= MUTANT_THRESHOLD) {
				LOG.debug("It's a Mutant!");
				return true;
			} else {
				final String[] dna_d1 = DnaHelper.diagTopRightBotomLeft(dna, MIN_REPETITIONS);
				count += findMutantDnaPattern(dna_d1);
				if (count >= MUTANT_THRESHOLD) {
					LOG.debug("It's a Mutant!");
					return true;
				} else {
					final String[] dna_d2 = DnaHelper.diagTopLeftBotomRight(dna, MIN_REPETITIONS);
					count += findMutantDnaPattern(dna_d2);
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

	private static int findMutantDnaPattern(String[] dna) {
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
	 
	@Override
	public void save(Dna dna) {
		getDnaDao().save(dna);
	}
	

	@Override
	public MutantStats stats() {
		final List<Dna> dnaList = getDnaDao().getAll();
		final long mutantCount = dnaList.stream()
				.filter(dna -> dna.isMutant())
				.count();
		
		LOG.info(String.format("Total DNAs: %d, Total Mutants: %d", dnaList.size(), mutantCount));
		return new MutantStats(dnaList.size(), mutantCount);
	}

}
