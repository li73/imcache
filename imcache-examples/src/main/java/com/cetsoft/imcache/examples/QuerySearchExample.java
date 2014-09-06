/*
* Copyright (C) 2014 Cetsoft, http://www.cetsoft.com
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Library General Public
* License as published by the Free Software Foundation; either
* version 2 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Library General Public License for more details.
*
* You should have received a copy of the GNU Library General Public
* License along with this library; if not, write to the Free
* Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
* 
* Author : Yusuf Aytas
* Date   : May 20, 2014
*/
package com.cetsoft.imcache.examples;

import java.util.List;

import com.cetsoft.imcache.cache.SearchableCache;
import com.cetsoft.imcache.cache.builder.CacheBuilder;
import com.cetsoft.imcache.cache.search.CacheQuery;
import com.cetsoft.imcache.cache.search.criteria.BetweenCriteria;
import com.cetsoft.imcache.cache.search.criteria.ETCriteria;
import com.cetsoft.imcache.cache.search.filter.LTFilter;
import com.cetsoft.imcache.cache.search.index.IndexType;

/**
 * The Class QuerySearchExample.
 */
public class QuerySearchExample {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String []args){
		SearchableCache<Integer, SimpleObject> cache = CacheBuilder.heapCache().addIndex("j", IndexType.RANGE_INDEX).build();
		cache.put(0, createObject(1, 1));
		cache.put(1, createObject(2, 2));
		cache.put(2, createObject(3, 3));
		List<SimpleObject> objects = cache.execute(CacheQuery.newQuery().setCriteria(new BetweenCriteria("j",1,3).
				or(new ETCriteria("j", 3))).setFilter(new LTFilter("k", 3)));
		for (SimpleObject simpleObject : objects) {
			System.out.println(simpleObject);
		}
	}
	
	/**
	 * Creates the object.
	 *
	 * @param i the i
	 * @param j the j
	 * @return the simple object
	 */
	private static SimpleObject createObject(int i,int j){
		SimpleObject object = new SimpleObject();
		object.setI(i);
		object.setJ(j);
		object.setK((int) (Math.random()*5));
		return object;
	}
	
	/**
	 * The Class SimpleObject.
	 */
	@SuppressWarnings("unused")
	private static class SimpleObject{
		
		/** The k. */
		private int i,j,k;
		
		/** The name. */
		private String name = "test";
		
		/**
		 * Gets the i.
		 *
		 * @return the i
		 */
		public int getI() {
			return i;
		}
		
		/**
		 * Sets the i.
		 *
		 * @param i the new i
		 */
		public void setI(int i) {
			this.i = i;
		}
		
		/**
		 * Gets the j.
		 *
		 * @return the j
		 */
		public int getJ() {
			return j;
		}
		
		/**
		 * Sets the j.
		 *
		 * @param j the new j
		 */
		public void setJ(int j) {
			this.j = j;
		}
		
		/**
		 * Gets the k.
		 *
		 * @return the k
		 */
		public int getK() {
			return k;
		}
		
		/**
		 * Sets the k.
		 *
		 * @param k the new k
		 */
		public void setK(int k) {
			this.k = k;
		}
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Sets the name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "SimpleObject [i=" + i + ", j=" + j + ", k=" + k + ", name=" + name + "]";
		}
	}
}