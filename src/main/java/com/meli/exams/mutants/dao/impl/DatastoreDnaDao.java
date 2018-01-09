package com.meli.exams.mutants.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.meli.exams.mutants.dao.DnaDao;
import com.meli.exams.mutants.model.Dna;

@Repository
public class DatastoreDnaDao implements DnaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(DatastoreDnaDao.class);
	
	private static final String ISMUTANT_FIELD = "isMutant";
	private static final String RAWDNA_FIELD = "rawDna";
	private static final String DNA_DATASTORE_KIND = "Dna";

	private DatastoreService datastore;
//	private KeyFactory dnaKeyFactory;
	
	
	@Autowired
	public void setDatastore(DatastoreService datastore) {
		this.datastore = datastore;
	}
	
	public DatastoreService getDatastore() {
		return datastore;
	}
	
//	public KeyFactory getDnaKeyFactory() {
//		return dnaKeyFactory;
//	}
//
//	@PostConstruct
//	public void initializeKeyFactories() {
//		LOG.info("Initializing key factories...");
//		dnaKeyFactory = getDatastore().newKeyFactory().setKind(DNA_DATASTORE_KIND);
//	}
	
	@Override
	public Long save(Dna dna) {
		Entity dnaEntity = createDnaEntity(dna);
		Key key = getDatastore().put(dnaEntity);
		LOG.info(String.format("DNA %d saved successfully", dna.hashCode()));
		LOG.info("DNA Already exists!");

		if (key == null) return null;
		else return key.getId();
	}
	
	private Entity createDnaEntity(Dna dna) {
		Gson gson = new Gson();
		Entity e = new Entity(DNA_DATASTORE_KIND, dna.hashCode());
		e.setProperty(RAWDNA_FIELD, gson.toJson(dna.getRawDna()));
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
