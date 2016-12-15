package com.insidecoding.geolog.api.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CsvConvertor {

	public String toCsv(List<SimpleAggr> items) {
		StringBuilder builder = new StringBuilder();
		builder.append("label,value\n");
		for (SimpleAggr aggr : items) {
			builder.append(aggr.toCsv()).append("\n");
		}

		return builder.toString();
	}
}
