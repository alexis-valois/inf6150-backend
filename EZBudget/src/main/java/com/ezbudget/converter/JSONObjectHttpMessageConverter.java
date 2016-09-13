package com.ezbudget.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class JSONObjectHttpMessageConverter extends
		AbstractHttpMessageConverter<JSONObject> {
	public JSONObjectHttpMessageConverter() {
		super(new MediaType[] { new MediaType("application", "json") });
	}

	public JSONObjectHttpMessageConverter(MediaType supportedMediaType) {
		super(supportedMediaType);
	}

	public JSONObjectHttpMessageConverter(MediaType[] supportedMediaTypes) {
		super(supportedMediaTypes);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return JSONObject.class.equals(clazz);
	}

	@Override
	protected JSONObject readInternal(Class<? extends JSONObject> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return new JSONObject(IOUtils.toString(inputMessage.getBody(), Charset.forName("UTF8")));
	}

	@Override
	protected void writeInternal(JSONObject t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		outputMessage.getBody().write(t.toString(5).getBytes());
	}
}
