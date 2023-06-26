package com.sctk.cmc.service.member.product;

import com.sctk.cmc.controller.member.product.dto.LikeProductGetExistenceResponse;
import com.sctk.cmc.controller.member.product.dto.LikedProductInfoResponse;
import com.sctk.cmc.domain.Product;

import java.util.List;

public interface MemberProductService {
    Product retrieveById(Long productId);

    LikedProductInfoResponse retrieveInfoById(Long memberId, Long productId);

    List<LikedProductInfoResponse> retrieveAllInfoById(Long memberId);

    LikeProductGetExistenceResponse checkLiked(Long memberId, Long productId);
}
