package com.meli.exams.mutants.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.meli.exams.mutants.dao.DnaDao;
import com.meli.exams.mutants.model.Dna;

@Repository
public class DatastoreDnaDao implements DnaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(DatastoreDnaDao.class);
	
	private static final String ISMUTANT_FIELD = "isMutant";
	private static final String RAWDNA_FIELD = "rawDna";
	private static final String DNA_DATASTORE_KIND = "Dna";

	private DatastoreService datastore;

	
	@Autowired
	public void setDatastore(DatastoreService datastore) {
		this.datastore = datastore;
	}
	
	public DatastoreService getDatastore() {
		return datastore;
	}
	
	@Override
	public Long save(Dna dna) {
		Key key = null;
		try {
			Entity dnaEntity = createDnaEntity(dna);
			key = getDatastore().put(dnaEntity);
			LOG.info(String.format("DNA %d saved successfully", dna.hashCode()));
		} catch (JsonProcessingException e) {
			LOG.error("Not able to store DNA: " + e.getLocalizedMessage(), e);
		}
		
		if (key == null) return null;
		else return key.getId();
	}
	
	private Entity createDnaEntity(Dna dna) throws JsonProcessingException {
		Entity e = new Entity(DNA_DATASTORE_KIND, dna.hashCode());
		ObjectMapper mapper = new ObjectMapper();
		Text rawDna = new Text(mapper.writeValueAsString(dna.getRawDna()));
		e.setUnindexedProperty(RAWDNA_FIELD, rawDna);
		e.setProperty(ISMUTANT_FIELD, dna.isMutant());
		return e;
	}

	@Override
	public long countMutants() {
		Query q = new Query(DNA_DATASTORE_KIND)
					.setFilter(new Query.FilterPredicate(
						ISMUTANT_FIELD, Query.FilterOperator.EQUAL, true
					));
		PreparedQuery pq = getDatastore().prepare(q);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
	}

	@Override
	public long total() {
		Query q = new Query(DNA_DATASTORE_KIND);
		PreparedQuery pq = getDatastore().prepare(q);
		return pq.countEntities(FetchOptions.Builder.withDefaults());
	}

	

}
