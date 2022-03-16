package uz.pdp.springsecurity.payload;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private String price;
    private String model;
    private Integer categoryId;
}
