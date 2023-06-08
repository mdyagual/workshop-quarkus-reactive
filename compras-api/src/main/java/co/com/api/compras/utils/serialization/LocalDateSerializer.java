package co.com.api.compras.utils.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

@ApplicationScoped
public class LocalDateSerializer extends JsonSerializer<Date> {

    private static final DateFormat FORMATTER = new SimpleDateFormat("MM-yy");


    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String formatDate = FORMATTER.format(date);
        jsonGenerator.writeString(formatDate);
    }
}
