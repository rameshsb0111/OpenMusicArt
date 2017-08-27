package com.jpm.openart.config;

import java.io.IOException;

import org.apache.lucene.store.RAMDirectory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jpm.openart.lucene.LuceneIndexBuilder;
import com.jpm.openart.lucene.LuceneSearchService;

@Configuration
@PropertySource("classpath:\\config.properties")
public class ApplicationConfig {
	
	@Value("${openArt.application.dbUrl:jdbc:h2:mem:test}")
	private String dbUrl;
	
	@Value("${openArt.application.lucene.filePath:classpath:src/main/resources/songs.txt}")
	private String filePath;

	@Bean
	public JdbcConnectionPool createConnectionPool() throws Exception {
		Class.forName("org.h2.Driver");
		return JdbcConnectionPool.create(dbUrl, "sa", "sa");
	}
	
	@Bean
	public LuceneIndexBuilder luceneIndexBuilder() throws IOException {
		return new LuceneIndexBuilder(filePath); 
	}
	
	@Bean 
	public LuceneSearchService luceneSearchService(LuceneIndexBuilder luceneIndexBuilde) throws Exception {
		
		RAMDirectory ramDirectory = luceneIndexBuilde.buildIndex();
		return new LuceneSearchService(ramDirectory);
	}
	

}
