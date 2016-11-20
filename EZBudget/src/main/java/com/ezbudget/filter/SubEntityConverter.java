package com.ezbudget.filter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ezbudget.exception.SubEntitiesParsingException;

@Component
public class SubEntityConverter implements Converter<String, SubEntity> {

	@Override
	public SubEntity convert(String source) {
		String[] sections = source.split(SubEntity.SUBENTITY_INNER_SEPARATOR);
		if ((sections == null) || (sections.length < 2)) {
			throw new SubEntitiesParsingException("Invalid subEntities string format. subEntity=" + source);
		}
		String type = sections[0];
		String foreignKeyName = sections[1];
		return new SubEntity(type, foreignKeyName);
	}

}
