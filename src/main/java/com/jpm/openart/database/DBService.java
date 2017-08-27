package com.jpm.openart.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.jdbcx.JdbcConnectionPool;

import com.jpm.openart.core.model.Song;
import com.jpm.openart.lucene.BaseSample;

public class DBService extends BaseSample{
	 private JdbcConnectionPool connectionPool;
	    private String query = "Select first_name,last_name,address,email from person where id in(";
	    
	    private static final String title = "song_title";
	    private static final  String genre = "genre";
	    private static final  String artist = "artist_name";
	    private static final  String fileName = "file_name";
	    private static final  String format = "file_format";
	    private static final  String duration = "song_duration";
	    
	    public DBService(JdbcConnectionPool connectionPool) {
	    	this.connectionPool = connectionPool;
	    }
	    
	    public ResultSet executeQuery(Statement statement, String query) throws SQLException{
	    	
	    	return statement.executeQuery(query);
	    	
	    }
	    
	    public void close(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
	    	resultSet.close();
	    	statement.close();
	    	connection.close();
	    	
	    }
	    public List<Song> getSongsById(List<String> ids) {
	        List<Map<String, String>> personData = getSongsDetailsById(ids);
	        List<Song> people = new ArrayList<Song>();
	        for (Map<String, String> data : personData) {
	            people.add(new Song(data));
	        }
	        return people;
	    }
	    
	    
	    public List<Map<String, String>> getSongsDetailsById(List<String> rawIds) {
	    	
	    	try {
	            StringBuilder queryBuilder = new StringBuilder(query);
	            queryBuilder.append(buildInList(rawIds)).append(')');
	            Connection connection = connectionPool.getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = executeQuery(statement, queryBuilder.toString());
	            List<Map<String, String>> results = extractResults(resultSet);
	            close(resultSet, statement, connection);

	            return results;
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    private String buildInList(List<String> rawIds) {
	        StringBuilder builder = new StringBuilder();
	        for (String rawId : rawIds) {
	            builder.append(rawId).append(',');
	        }
	        builder.setLength(builder.length() - 1);
	        return builder.toString();
	    }
	    
	    private List<Map<String, String>> extractResults(ResultSet resultSet) {
	        List<Map<String, String>> allResults = new ArrayList<>();
	        try {
	        	while(resultSet.next()){
	        		Map<String, String>  rowResult = new HashMap<>();
	        		rowResult.put(title, resultSet.getString(title));
	        		rowResult.put(genre, resultSet.getString(genre));
	        		rowResult.put(artist, resultSet.getString(artist));
	        		rowResult.put(fileName, resultSet.getString(fileName));
	        		rowResult.put(format, resultSet.getString(format));
	        		rowResult.put(duration, resultSet.getString(duration));
	        	}
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	        return allResults;
	    }

}
