package com.meli.exams.mutants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

@SpringBootApplication
public class App {
	
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }
    
    @Bean
    public Datastore cloudDatastoreService() {
    	return DatastoreOptions.getDefaultInstance().getService();
    }
}
