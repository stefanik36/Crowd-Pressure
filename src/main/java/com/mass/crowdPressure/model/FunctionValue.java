package com.mass.crowdPressure.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FunctionValue<K, V> {

	private Map<K, V> map;

	public FunctionValue() {
		map = new HashMap<>();
	}

	public void put(K key, V value) {
		map.put(key, value);
	}

	public Map<K, V> getMap() {
		return map;
	}

}
