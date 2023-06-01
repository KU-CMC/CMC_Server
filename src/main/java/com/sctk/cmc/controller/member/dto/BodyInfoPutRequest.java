package com.sctk.cmc.controller.member.dto;

import com.sctk.cmc.domain.SizesByPart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BodyInfoPutRequest {
    private SizesByPart sizes;
}