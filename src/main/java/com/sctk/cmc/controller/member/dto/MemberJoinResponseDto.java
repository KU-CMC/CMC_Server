package com.sctk.cmc.controller.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoinResponseDto {
    private Long id;

    public static MemberJoinResponseDto of(Long memberId) {
        return MemberJoinResponseDto.builder()
                .id(memberId)
                .build();
    }
}
