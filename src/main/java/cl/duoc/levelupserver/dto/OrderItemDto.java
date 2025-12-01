package cl.duoc.levelupserver.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String code; // El código del producto (ej: "PS5-001")
    private Integer qty; // La cantidad
}