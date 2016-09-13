package com.ezbudget.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class JSONArrayHttpMessageConverter extends
		AbstractHttpMessageConverter<JSONArray> {
	public JSONArrayHttpMessageConverter() {
		super(new MediaType[] { new MediaType("application", "json") });
	}

	public JSONArrayHttpMessageConverter(MediaType supportedMediaType) {
		super(supportedMediaType);
	}

	public JSONArrayHttpMessageConverter(MediaType[] supportedMediaTypes) {
		super(supportedMediaTypes);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return JSONArray.class.equals(clazz);
	}

	@Override
	protected JSONArray readInternal(Class<? extends JSONArray> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return new JSONArray(IOUtils.toString(inputMessage.getBody(), Charset.forName("UTF8")));
	}

	@Override
	protected void writeInternal(JSONArray t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		outputMessage.getBody().write(t.toString(3).getBytes());
	}
}