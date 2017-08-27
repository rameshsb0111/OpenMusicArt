package com.jpm.openart.cache;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jpm.openart.core.model.Song;
import com.jpm.openart.database.DBService;
import com.jpm.openart.lucene.LuceneSearchService;

public class GenericCacheLoader<T> {
	
CacheRemovalListener<Song> cacheRemovalListener;

private final DBService dbService;
private final LuceneSearchService luceneSearchService;
private final LoadingCache<String, List<Song>> cache;

public GenericCacheLoader(DBService dbService, LuceneSearchService luceneSearchService) {
	this.dbService = dbService;
	this.luceneSearchService = luceneSearchService;
	this.cacheRemovalListener = new CacheRemovalListener<>();
	this.cache = loadCache();
	
}

public LoadingCache loadCache() {
	LoadingCache<String, List<Song>> cache = CacheBuilder.newBuilder().expireAfterWrite(500, TimeUnit.MILLISECONDS)
            .removalListener(cacheRemovalListener)
            .recordStats()
            .build(getCacheLoader());
	return cache;
	
}

public List<Song> getCache(String key) throws ExecutionException{
	return this.cache.get(key);
}

private CacheLoader<String, List<Song>> getCacheLoader() {
    return new CacheLoader<String, List<Song>>() {
        @Override
        public List<Song> load(String key) throws Exception {
            List<String> ids = luceneSearchService.search(key);
            return dbService.getSongsById(ids);
        }
    };
}
}
