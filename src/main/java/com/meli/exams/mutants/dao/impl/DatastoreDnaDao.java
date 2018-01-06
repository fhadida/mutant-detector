package com.meli.exams.mutants.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.gson.Gson;
import com.meli.exams.mutants.dao.DnaDao;
import com.meli.exams.mutants.model.Dna;

@Repository
public class DatastoreDnaDao implements DnaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(DatastoreDnaDao.class);
	
	private static final String ISMUTANT_FIELD = "isMutant";
	private static final String RAWDNA_FIELD = "rawDna";
	private static final String DNA_DATASTORE_KIND = "Dna";

	private Datastore datastore;
	private KeyFactory dnaKeyFactory;
	
	
	@Autowired
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
	
	public Datastore getDatastore() {
		return datastore;
	}
	
	public KeyFactory getDnaKeyFactory() {
		return dnaKeyFactory;
	}

	@PostConstruct
	public void initializeKeyFactories() {
		LOG.info("Initializing key factories...");
		dnaKeyFactory = getDatastore().newKeyFactory().setKind(DNA_DATASTORE_KIND);
	}
	
	@Override
	public Entity save(Dna dna) {
		Entity e = null;
		try {
			e = getDatastore().add(createDnaEntity(dna));
			LOG.info(String.format("DNA %d saved successfully", dna.hashCode()));
		} catch (DatastoreException ex) {
			if ("ALREADY_EXISTS".equals(ex.getReason())) {
				LOG.info("DNA Already exists!");
			}
		}
		return e;
	}
	
	private Entity createDnaEntity(Dna dna) {
		Gson gson = new Gson();
		Key key = getDnaKeyFactory().newKey(dna.hashCode());
		return Entity.newBuilder(key)
				.set(RAWDNA_FIELD, gson.toJson(dna.getRawDna()))
				.set(ISMUTANT_FIELD, dna.isMutant())
				.build();
	}

	@Override
	public List<Dna> getAll() {
		List<Dna> list = new ArrayList<>();
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(DNA_DATASTORE_KIND).build();
		QueryResults<Entity> results = getDatastore().run(query);
		results.forEachRemaining(e -> {
			Gson gson = new Gson();
			final String[] rawDna = gson.fromJson(e.getString(RAWDNA_FIELD), String[].class);
			final Boolean isMutant = e.getBoolean(ISMUTANT_FIELD);
			list.add(new Dna(rawDna, isMutant));
		});
		LOG.info(String.format("%d DNAs found", list.size()));
		return list;
	}

}
