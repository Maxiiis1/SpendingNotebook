package models.categories.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private String name;
    private List<Integer> mccCodes;
    private List<String> subCategories;
}