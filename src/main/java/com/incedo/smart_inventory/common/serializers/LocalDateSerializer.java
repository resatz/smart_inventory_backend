package com.incedo.smart_inventory.common.serializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

	private final DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
	
	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		try {
			gen.writeString(formatter.format(value));
		}
		catch (DateTimeParseException ex) {
			
		}
	}
	
}
