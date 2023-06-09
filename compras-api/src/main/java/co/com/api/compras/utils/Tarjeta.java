package co.com.api.compras.utils;

import co.com.api.compras.utils.serialization.LocalDateDeserializer;
import co.com.api.compras.utils.serialization.LocalDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class Tarjeta {
    @NotNull(message = "Número de tarjeta es obligatorio")
    @Size(min = 16, max=16, message = "La tarjeta no tiene los 16 dígitos")
    private String numero;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "fechaCaducidad es obligatorio")
    private Date fechaCaducidad;

    @NotNull(message = "CVV es obligatorio")
    @Size(max = 3, min=3, message = "El CVV no tiene los 3 dígitos")
    private String cvv;

    @Pattern(regexp = "^\\w+\\s+\\w+$", message = "Ingrese nombre y apellido separado por 1 espacio")
    @Size(max=30, min=10, message = "Nombre de propietario muy extenso o muy corto")
    private String propietario;
}
