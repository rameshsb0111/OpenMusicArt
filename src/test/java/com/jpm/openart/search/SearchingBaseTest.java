package com.jpm.openart.search;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.store.RAMDirectory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.jpm.openart.database.DBService;
import com.jpm.openart.lucene.LuceneIndexBuilder;
import com.jpm.openart.lucene.LuceneSearchService;

public class SearchingBaseTest {

	  private static final String dbUrl = "jdbc:h2:mem:test";
	    private static final String insertSql = " CREATE TABLE SONG(TITLE VARCHAR(255), GENRE VARCHAR(255)," +
	    "ARTIST VARCHAR(255), FILE_NAME VARCHAR(255),SONG_DURATION INT,FILE_NAME VARCHAR(255),ID INT PRIMARY KEY) AS SELECT * FROM CSVREAD('classpath:songs.txt')";
	    protected static JdbcConnectionPool connectionPool;
	    private static Server dbServer;
	    private static LuceneIndexBuilder luceneIndexBuilder;
	    protected static LuceneSearchService luceneSearcher;
	    protected static DBService dbService;
	    
	@BeforeClass
    public static void setUpBeforeAllTests() throws Exception {
        startH2();
        setUpIndex();
        createConnectionPool();
        populateDb();
        dbService = new DBService(connectionPool);
    }

    @AfterClass
    public static void shutDownAfterAllTests() throws Exception {
        stopH2();
        closeConnectionPool();
        luceneSearcher.shutDown();
        dbService.shutDown();
    }

    private static void populateDb() throws SQLException {
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(insertSql);
        statement.close();
        connection.close();
    }

    private static void setUpIndex() throws Exception {
        String filePath = System.getProperty("names","src/main/resources/songs.txt");
        luceneIndexBuilder = new LuceneIndexBuilder(filePath);
        RAMDirectory ramDirectory = luceneIndexBuilder.buildIndex();
        luceneSearcher = new LuceneSearchService(ramDirectory);
    }

    private static void startH2() throws Exception {
        dbServer = Server.createTcpServer();
        dbServer.start();
    }

    private static void stopH2() throws Exception {
        dbServer.stop();
    }

    private static void createConnectionPool() throws Exception {
        Class.forName("org.h2.Driver");
        connectionPool =  JdbcConnectionPool.create(dbUrl, "sa", "sa");
    }

    private static void closeConnectionPool() throws Exception {
        connectionPool.dispose();
    }
}
