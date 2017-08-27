package com.jpm.openart.cache;

import java.util.List;


import org.springframework.stereotype.Component;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Component
public class CacheRemovalListener<T> implements RemovalListener<String, List<T>> {

	private RemovalNotification<String, List<T>> removalNotification;

	@Override
	public void onRemoval(RemovalNotification<String, List<T>> removalNotification) {
		this.removalNotification = removalNotification;
	}

	public RemovalNotification<String, List<T>> getRemovalNotification() {
		return removalNotification;
	}
}
