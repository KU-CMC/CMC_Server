package com.sctk.cmc.service.designer.custom;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.controller.designer.custom.dto.CustomGetDetailResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomGetInfoResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomIdResponse;
import com.sctk.cmc.controller.designer.custom.dto.CustomResultIdResponse;
import com.sctk.cmc.domain.*;
import com.sctk.cmc.repository.designer.custom.DesignerCustomRepository;
import com.sctk.cmc.service.designer.custom.dto.CustomResultAcceptParams;
import com.sctk.cmc.service.designer.custom.dto.CustomResultRejectParams;
import com.sctk.cmc.service.member.custom.MemberCustomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static com.sctk.cmc.common.exception.ResponseStatus.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DesignerCustomServiceImpl implements DesignerCustomService {
    private final MemberCustomService memberCustomService;
    private final DesignerCustomRepository designerCustomRepository;

    @Override
    public List<CustomGetInfoResponse> retrieveAllInfo(Long designerId) {

        List<Custom> allCustoms = designerCustomRepository.findAllByDesignerId(designerId);

        List<CustomGetInfoResponse> responseList = allCustoms.stream()
                .map(CustomGetInfoResponse::of)
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public CustomGetDetailResponse retrieveDetailById(Long designerId, Long customId) {

        Custom custom = memberCustomService.retrieveById(customId);

        //해당 커스텀 요청이 로그인한 디자이너 소유인지 검증
        validateDesignerAuthority(designerId, custom);

        return CustomGetDetailResponse.of(custom);
    }

    @Transactional
    @Override
    public CustomIdResponse deleteSoft(Long designerId, Long customId) {

        Custom custom = memberCustomService.retrieveById(customId);

        validateDesignerAuthority(designerId, custom);

        custom.changeActiveToFalse();

        return CustomIdResponse.of(custom.getId());
    }

    @Transactional
    @Override
    public CustomResultIdResponse acceptCustom(Long designerId, Long customId,
                                               CustomResultAcceptParams customResultAcceptParams) {

        Custom custom = memberCustomService.retrieveById(customId);

        validateDesignerAuthority(designerId, custom);

        // accepted 가 REQUESTING 인지 검증
        validateAcceptedIsRequesting(custom);

        CustomResult.ofAcceptance(custom, customResultAcceptParams);
        custom.changeStatusTo(CustomStatus.APPROVAL);

        return CustomResultIdResponse.of(custom.getId());
    }

    @Transactional
    @Override
    public CustomResultIdResponse rejectCustom(Long designerId, Long customId, CustomResultRejectParams customResultRejectParams) {

        Custom custom = memberCustomService.retrieveById(customId);

        validateDesignerAuthority(designerId, custom);

        validateAcceptedIsRequesting(custom);

        CustomResult.ofRejection(custom, customResultRejectParams);
        custom.changeStatusTo(CustomStatus.REFUSAL);

        return CustomResultIdResponse.of(custom.getId());
    }

    private void validateDesignerAuthority(Long designerId, Custom custom) {
        if ( !custom.getDesigner().getId().equals(designerId) ) {
            throw new CMCException(NOT_HAVE_DESIGNERS_AUTHORITY);
        }
    }

    private void validateAcceptedIsRequesting(Custom custom) {
        if (custom.getAccepted() != CustomStatus.REQUESTING) {
            throw new CMCException(ALREADY_RESPONDED_CUSTOM);
        }
    }

}
