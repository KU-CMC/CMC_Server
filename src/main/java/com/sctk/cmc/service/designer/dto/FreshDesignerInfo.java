package com.sctk.cmc.service.designer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FreshDesignerInfo implements FilteredDesignerInfo {
    private Long designerId;
    private String name;
    private String profileImgUrl;
    private List<String> categoryNames;
}
