package com.ezbudget.filter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ezbudget.exception.FilterParsingException;

@Component
public class FilterConverter implements Converter<String, Filter> {
	@Override
	public Filter convert(String str) {
		String[] sections = str.split(Filter.FILTER_INNER_SEPARATOR);
		if ((sections == null) || (sections.length < 3)) {
			throw new FilterParsingException(
					"Invalid filter string format. filter=" + str);
		}
		String valueStr = sections[2];
		String type = sections[1];
		String[] values = valueStr.split(Filter.FILTER_VALUE_SEPARATOR);

		if (FilterConditionType.isTypeValid(type)) {
			throw new FilterParsingException(
					"Invalid filter type received. type=" + type + " filter="
							+ str);
		}

		if ((values == null) || (values.length <= 0)) {
			throw new FilterParsingException(
					"Invalid filter value received. type=" + type + " filter="
							+ str);
		}
		if ((type.equals(FilterConditionType.BETWEEN.getCondition()))
				&& (values.length != 2)) {
			throw new FilterParsingException(
					"Between filter require two values. filter=" + str);
		}

		Filter filter = new Filter(sections[0], type, values);
		return filter;
	}
}
