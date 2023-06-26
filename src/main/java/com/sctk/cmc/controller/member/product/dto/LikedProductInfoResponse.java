package com.sctk.cmc.controller.member.product.dto;

import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LikedProductInfoResponse {
    private Long productId;
    private String name;
    private int price;
    private int likes;
    private List<String> descriptionImgList;
    private Long designerId;
    private String designerName;
    private String designerProfileImgUrl;
    private boolean liked;

    public static LikedProductInfoResponse of(Product product, Designer designer,
                                                  List<String> descriptionImgList, Boolean liked){

        return LikedProductInfoResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .likes(product.getLikeCount())
                .descriptionImgList(descriptionImgList)
                .designerId(designer.getId())
                .designerName(designer.getName())
                .designerProfileImgUrl(designer.getProfileImgUrl())
                .liked(liked)
                .build();
    }
}
