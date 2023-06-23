package com.sctk.cmc.service.member.like.handler.function.find;

import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.Member;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.domain.likeobject.LikeObject;
import com.sctk.cmc.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeProductFindFunction implements LikeObjectFindFunction {
    private final MemberService memberService;
    @Override
    public boolean support(Class<? extends LikedEntity> targetClass) {
        return targetClass.equals(Product.class);
    }

    @Override
    public LikeObject apply(Long memberId, Long targetId) {
        Member member = memberService.retrieveById(memberId);

        return member.getProductLikes()
                .stream()
                .filter(likeProduct -> likeProduct.getProduct().getId() == targetId)
                .findAny()
                .orElse(null);
    }
}
