package com.jpm.openart.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.google.common.util.concurrent.ListenableFuture;

public class LuceneSearchService extends BaseSample {
	
	private IndexSearcher searcher;
    private final int MAX_RESULTS = 1000;

    public LuceneSearchService(RAMDirectory ramDirectory) {
        try {
            searcher = new IndexSearcher(IndexReader.open(ramDirectory, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<String> search(String query) throws Exception {
    	List<String> results = new ArrayList<String>();
    	   QueryParser queryParser = new QueryParser(Version.LUCENE_35, null, new StandardAnalyzer(Version.LUCENE_35));
           Query q = queryParser.parse(query);
           TopDocs topDocs = searcher.search(q, MAX_RESULTS);
           for (ScoreDoc sd : topDocs.scoreDocs) {
               Document document = searcher.doc(sd.doc);
               results.add(document.get("id"));
           }
    	
    	return results;
    	
    }
    
    public ListenableFuture<List<String>> searchAsync(final String query)  {
        return executorService.submit(new Callable<List<String>>() {
            public List<String> call() throws Exception {
                return search(query);
            }
        });
    }

}
