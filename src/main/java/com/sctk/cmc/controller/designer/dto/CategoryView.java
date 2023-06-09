package com.sctk.cmc.controller.designer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryView {
    private String highCategoryName;
    private List<String> lowCategoryNames;
}
