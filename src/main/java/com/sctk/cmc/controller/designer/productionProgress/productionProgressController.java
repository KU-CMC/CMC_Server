package com.sctk.cmc.controller.designer.productionProgress;

import com.sctk.cmc.auth.domain.SecurityDesignerDetails;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.service.designer.productionProgress.ProductionProgressService;
import com.sctk.cmc.service.designer.productionProgress.dto.ProductionProgressGetInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/designers")
@RequiredArgsConstructor
@Tag(name = "Designer Production Progress", description = "디자이너 제작중 API Document")
public class productionProgressController {
    private final ProductionProgressService productionProgressService;

    @Operation(summary = "제작중 전체 간단 조회 API", description = "디자이너가 모든 커스텀 요청을 간단 조회할 때 사용합니다.")
    @GetMapping("/production-progress")
    public BaseResponse<List<ProductionProgressGetInfoResponse>> retrieveAllInfo(@AuthenticationPrincipal SecurityDesignerDetails designerDetails) {

        Long designerId = designerDetails.getId();
        List<ProductionProgressGetInfoResponse> responses = productionProgressService.retrieveAllInfo(designerId);

        return new BaseResponse<>(responses);
    }
}