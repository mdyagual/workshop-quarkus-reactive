package co.com.api.compras.utils;

import co.com.api.compras.utils.serialization.LocalDateDeserializer;
import co.com.api.compras.utils.serialization.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

import java.util.Date;

@Data
public class Tarjeta {
    private String numero;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private Date fechaCaducidad;
    private String cvv;
    private String propietario;
}
