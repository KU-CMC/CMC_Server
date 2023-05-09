package com.sctk.cmc.web.dto.member;

import com.sctk.cmc.domain.SizesByPart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BodyInfoPutRequest {
    private SizesByPart sizes;
}