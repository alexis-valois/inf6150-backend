package com.ezbudget.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {

	public static JSONArray sortByKeyASC(JSONArray source, String keyName,
			boolean elementsAreObjects) {
		List<JSONObject> sorted = new ArrayList<JSONObject>();
		JSONArray rtn = new JSONArray();
		if (elementsAreObjects) {
			for (int i = 0, len = source.length(); i < len; i++) {
				sorted.add(source.getJSONObject(i));
			}

			Collections.sort(sorted, new KeySpecificJsonComparator(keyName));

			for (JSONObject obj : sorted) {
				rtn.put(obj);
			}

		} else {
			// NOT IMPLEMENTED YET
		}
		return rtn;
	}
}
