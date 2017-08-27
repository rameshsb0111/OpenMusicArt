package com.jpm.openart.cache;

public interface SearchService<T> {

	T search(String query) throws Exception;
}
