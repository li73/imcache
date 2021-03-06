/*
* Copyright (C) 2014 Yusuf Aytas, http://www.yusufaytas.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
* Author : Yusuf Aytas
* Date   : Jan 6, 2014
*/
package com.cetsoft.imcache.spring;

import com.cetsoft.imcache.cache.CacheLoader;
import com.cetsoft.imcache.cache.EvictionListener;
import com.cetsoft.imcache.cache.SearchableCache;
import com.cetsoft.imcache.cache.builder.CacheBuilder;
import com.cetsoft.imcache.cache.builder.ConcurrentHeapCacheBuilder;
import com.cetsoft.imcache.cache.builder.HeapCacheBuilder;
import com.cetsoft.imcache.cache.builder.OffHeapCacheBuilder;
import com.cetsoft.imcache.cache.builder.TransactionalHeapCacheBuilder;
import com.cetsoft.imcache.cache.builder.VersionedOffHeapCacheBuilder;
import com.cetsoft.imcache.cache.offheap.OffHeapCache;
import com.cetsoft.imcache.cache.offheap.bytebuffer.OffHeapByteBufferStore;
import com.cetsoft.imcache.cache.search.IndexHandler;
import com.cetsoft.imcache.serialization.Serializer;

/**
 * The Class SpringCacheBuilder.
 */
public class SpringCacheBuilder extends CacheBuilder {

	/** The type. */
	private String type;

	/** The concurrency level. */
	private int concurrencyLevel = OffHeapCache.DEFAULT_CONCURRENCY_LEVEL;

	/** The eviction period. */
	private long evictionPeriod = OffHeapCache.DEFAULT_EVICTION_PERIOD;

	/** The buffer cleaner period. */
	private long bufferCleanerPeriod = OffHeapCache.DEFAULT_BUFFER_CLEANER_PERIOD;

	/** The buffer cleaner threshold. */
	private float bufferCleanerThreshold = OffHeapCache.DEFAULT_BUFFER_CLEANER_THRESHOLD;

	/** The serializer. */
	private Serializer<Object> serializer = SERIALIZER;

	/** The buffer store. */
	private OffHeapByteBufferStore bufferStore;

	/**
	 * Instantiates a new spring cache builder.
	 */
	public SpringCacheBuilder() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cetsoft.imcache.cache.builder.CacheBuilder#build()
	 */
	@Override
	public <K, V> SearchableCache<K, V> build() {
		SearchableCache<K, V> cache = null;
		if (type == null || type.equals("concurrentheap")) {
			ConcurrentHeapCacheBuilder builder = CacheBuilder.concurrentHeapCache();
			builder.cacheLoader(cacheLoader).evictionListener(evictionListener).indexHandler(indexHandler);
			cache = builder.build();
		} else if (type.equals("heap")) {
			HeapCacheBuilder builder = CacheBuilder.heapCache();
			builder.cacheLoader(cacheLoader).evictionListener(evictionListener).indexHandler(indexHandler);
			cache = builder.build();
		} else if (type.equals("transactionalheap")) {
			TransactionalHeapCacheBuilder builder = CacheBuilder.transactionalHeapCache();
			builder.cacheLoader(cacheLoader).evictionListener(evictionListener).indexHandler(indexHandler);
			cache = builder.build();
		} else if (type.equals("offheap")) {
			OffHeapCacheBuilder builder = CacheBuilder.offHeapCache();
			builder.cacheLoader(cacheLoader).evictionListener(evictionListener).indexHandler(indexHandler)
					.concurrencyLevel(concurrencyLevel).bufferCleanerPeriod(bufferCleanerPeriod)
					.bufferCleanerThreshold(bufferCleanerThreshold).evictionPeriod(evictionPeriod)
					.serializer(serializer).storage(bufferStore);
			cache = builder.build();
		} else if (type.equals("versionedoffheap")) {
			VersionedOffHeapCacheBuilder builder = CacheBuilder.versionedOffHeapCache();
			builder.cacheLoader(cacheLoader).evictionListener(evictionListener).indexHandler(indexHandler)
					.concurrencyLevel(concurrencyLevel).bufferCleanerPeriod(bufferCleanerPeriod)
					.bufferCleanerThreshold(bufferCleanerThreshold).evictionPeriod(evictionPeriod)
					.serializer(serializer).storage(bufferStore);
			cache = builder.build();
		}
		return cache;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the concurrency level.
	 *
	 * @param concurrencyLevel the new concurrency level
	 */
	public void setConcurrencyLevel(int concurrencyLevel) {
		this.concurrencyLevel = concurrencyLevel;
	}

	/**
	 * Sets the eviction period.
	 *
	 * @param evictionPeriod the new eviction period
	 */
	public void setEvictionPeriod(long evictionPeriod) {
		this.evictionPeriod = evictionPeriod;
	}

	/**
	 * Sets the buffer cleaner period.
	 *
	 * @param bufferCleanerPeriod the new buffer cleaner period
	 */
	public void setBufferCleanerPeriod(long bufferCleanerPeriod) {
		this.bufferCleanerPeriod = bufferCleanerPeriod;
	}

	/**
	 * Sets the buffer cleaner threshold.
	 *
	 * @param bufferCleanerThreshold the new buffer cleaner threshold
	 */
	public void setBufferCleanerThreshold(float bufferCleanerThreshold) {
		this.bufferCleanerThreshold = bufferCleanerThreshold;
	}

	/**
	 * Sets the serializer.
	 *
	 * @param serializer the new serializer
	 */
	public void setSerializer(Serializer<Object> serializer) {
		this.serializer = serializer;
	}

	/**
	 * Sets the cache loader.
	 *
	 * @param cacheLoader the cache loader
	 */
	public void setCacheLoader(CacheLoader<Object, Object> cacheLoader) {
		this.cacheLoader = cacheLoader;
	}

	/**
	 * Sets the eviction listener.
	 *
	 * @param evictionListener the eviction listener
	 */
	public void setEvictionListener(EvictionListener<Object, Object> evictionListener) {
		this.evictionListener = evictionListener;
	}

	/**
	 * Sets the index handler.
	 *
	 * @param indexHandler the index handler
	 */
	public void setIndexHandler(IndexHandler<Object, Object> indexHandler) {
		this.indexHandler = indexHandler;
	}

	/**
	 * Sets the buffer store.
	 *
	 * @param bufferStore the new buffer store
	 */
	public void setBufferStore(OffHeapByteBufferStore bufferStore) {
		this.bufferStore = bufferStore;
	}

}
