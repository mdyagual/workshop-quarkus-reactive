package co.com.api.compras.utils.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

@ApplicationScoped
public class LocalDateDeserializer extends JsonDeserializer<Date> {

    private static final DateFormat FORMATTER = new SimpleDateFormat("MM-yy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String dateStr = jsonParser.getValueAsString();
        try {
            return FORMATTER.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
