package com.shopping.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryTreeDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sortOrder;
    private List<CategoryTreeDTO> children;
}
