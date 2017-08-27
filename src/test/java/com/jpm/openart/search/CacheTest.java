package com.jpm.openart.search;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.jpm.openart.core.model.Song;
import org.hamcrest.core.*;;
public class CacheTest extends SearchingBaseTest {

	@Test
    public void testCacheLoadedAfterFirstRequestThenCached() throws Exception {
		SongsListRemovalListener removalListener= new SongsListRemovalListener();
		 LoadingCache<String, List<Song>> cache = CacheBuilder.newBuilder().expireAfterWrite(500, TimeUnit.MILLISECONDS)
	                .removalListener(removalListener)
	                .recordStats()
	                .build(getCacheLoader());

	        String queryKey = "title:Man who sold the world";
	        List<Song> songList = cache.get(queryKey);
	        List<Song> songListII = cache.get(queryKey);
	        Thread.sleep(500);
	        List<Song> songListtIII = cache.get(queryKey);
	        assertTrue(songList == songListII);
	        assertFalse(songList == songListtIII);
	}
	
	   private CacheLoader<String, List<Song>> getCacheLoader() {
	        return new CacheLoader<String, List<Song>>() {
	            @Override
	            public List<Song> load(String key) throws Exception {
	                List<String> ids = luceneSearcher.search(key);
	                return dbService.getSongsById(ids);
	            }
	        };
	    }
	   
	    @Test
	    public void testCacheSizeLimit() throws Exception {
	        SongsListRemovalListener removalListener = new SongsListRemovalListener();
	        LoadingCache<String, List<Song>> cache = CacheBuilder.newBuilder()
	                .maximumSize(2)
	                .removalListener(removalListener)
	                .build(CacheLoader.from(getFunction()));
	        String queryKey = "title:The man who sold the world";
	        String queryKeyII = "title:Nothing else matters";
	        String queryKeyIII = "title:Stairway to heaven";

	        cache.get(queryKey);
	        cache.get(queryKeyII);
	        cache.get(queryKeyIII);
	        assertEquals(removalListener.getRemovalNotification().getKey(), queryKey);
	        assertEquals(removalListener.getRemovalNotification().getCause(),RemovalCause.SIZE);
	    }
	
	   private class SongsListRemovalListener implements RemovalListener<String, List<Song>> {

	        private RemovalNotification<String, List<Song>> removalNotification;

	        @Override
	        public void onRemoval(RemovalNotification<String, List<Song>> removalNotification) {
	            this.removalNotification = removalNotification;
	        }

	        public RemovalNotification<String, List<Song>> getRemovalNotification() {
	            return removalNotification;
	        }
	    }
	   
	   private Function<String, List<Song>> getFunction() {
	        return new Function<String, List<Song>>() {
	            @Override
	            public List<Song> apply(String searchKey) {
	                try {
	                    List<String> ids = luceneSearcher.search(searchKey);
	                    return dbService.getSongsById(ids);
	                } catch (Exception e) {
	                    throw new RuntimeException(e);
	                }
	            }
	        };
	    }

}
