package com.ezbudget.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ezbudget.converter.JSONArrayHttpMessageConverter;
import com.ezbudget.converter.JSONObjectHttpMessageConverter;

public class RestConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> httpMessageConverters) {
		httpMessageConverters.add(new JSONObjectHttpMessageConverter());
		httpMessageConverters.add(new JSONArrayHttpMessageConverter());
		httpMessageConverters.add(new HttpMessageConverter<Object>() {

			@Override
			public boolean canRead(Class<?> clazz, MediaType mediaType) {
				return false;
			}

			@Override
			public boolean canWrite(Class<?> clazz, MediaType mediaType) {
				return String.class.equals(clazz);
			}

			@Override
			public List<MediaType> getSupportedMediaTypes() {
				List<MediaType> rtn = new ArrayList<MediaType>();
				rtn.add(new MediaType("text", "html"));
				rtn.add(new MediaType("plain", "text"));
				rtn.add(new MediaType("application", "json"));
				return rtn;
			}

			@Override
			public String read(Class<?> clazz, HttpInputMessage inputMessage)
					throws IOException, HttpMessageNotReadableException {
				return IOUtils.toString(inputMessage.getBody(), Charset.forName("UTF8"));
			}

			@Override
			public void write(Object t, MediaType contentType,
					HttpOutputMessage outputMessage) throws IOException,
					HttpMessageNotWritableException {
				outputMessage.getBody().write(((String) t).getBytes());
			}

		});
		httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
	}
}