package com.ezbudget.utils;

import java.util.Comparator;

import org.json.JSONObject;

public class KeySpecificJsonComparator implements Comparator<JSONObject> {

	private String compareStringKey = "";

	public KeySpecificJsonComparator(String compareStringKey) {
		this.compareStringKey = compareStringKey;
	}

	@Override
	public int compare(JSONObject o1, JSONObject o2) {
		String valA = o1.getString(compareStringKey);
		String valB = o2.getString(compareStringKey);
		return valA.compareTo(valB);
	}
}
