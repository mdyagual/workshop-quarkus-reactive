package co.com.api.compras.utils;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;



@Data
public class Usuario {
    @NotNull(message = "Tarjeta del usuario es obligatoria")
    @Valid
    private Tarjeta tarjeta;

    @NotNull(message = "Lista de productos es obligatoria")
    @NotEmpty(message = "Lista de productos vacía")
    @Valid
    private List<Producto> productos = new ArrayList<>();
}
