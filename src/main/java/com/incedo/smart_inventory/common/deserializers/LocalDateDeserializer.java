package com.incedo.smart_inventory.common.deserializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	private final DateTimeFormatter formatter;

    public LocalDateDeserializer() {
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
	
	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		try {
			return LocalDate.parse(p.getText(), formatter);
		}
		catch (DateTimeParseException ex) {
			return LocalDate.MIN;
		}
	}
	
}
