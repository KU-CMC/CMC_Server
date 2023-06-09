package com.sctk.cmc.service.designer.product.dto;

import com.sctk.cmc.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductGetInfoResponse {
    private Long productId;
    private String mainImgUrl;

    public static ProductGetInfoResponse of(Product product) {
        return ProductGetInfoResponse.builder()
                .productId(product.getId())
                .mainImgUrl(product.getThumbnailImgList().get(0).getUrl())
                .build();
    }
}
