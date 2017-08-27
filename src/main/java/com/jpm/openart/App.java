package com.jpm.openart;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jpm.openart.config.ApplicationConfig;
import com.jpm.openart.lucene.LuceneSearchService;

/**
 * Hello world!
 *
 */
public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		
		logger.info("Starting application");

		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

		LuceneSearchService luceneSearchService = (LuceneSearchService) context.getBean("luceneSearchService");
		
		logger.info("Application successfull started");
	}
}
