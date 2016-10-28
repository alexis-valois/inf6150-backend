package com.ezbudget.serializer;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MoneyTypeSerializer implements JsonSerializer<Money>, JsonDeserializer<Money> {

	@Override
	public Money deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		try {
			JsonObject obj = json.getAsJsonObject();
			CurrencyUnit currency = CurrencyUnit.of(obj.get("currency").getAsString());
			BigDecimal amount = obj.get("amount").getAsBigDecimal();
			return Money.of(currency, amount);
		} catch (IllegalArgumentException e) {
			return Money.of(CurrencyUnit.CAD, 0);
		}
	}

	@Override
	public JsonElement serialize(Money src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.add("amount", new JsonPrimitive(src.getAmount().doubleValue()));
		obj.add("currency", new JsonPrimitive(src.getCurrencyUnit().getCurrencyCode()));
		return obj;
	}

}
