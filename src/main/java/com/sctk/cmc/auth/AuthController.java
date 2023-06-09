package com.sctk.cmc.auth;

import com.sctk.cmc.auth.dto.LoginResponseDto;
import com.sctk.cmc.auth.dto.TokenReissueRequestDto;
import com.sctk.cmc.auth.dto.TokenReissueResponseDto;
import com.sctk.cmc.auth.jwt.JwtService;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.controller.member.dto.MemberJoinResponseDto;
import com.sctk.cmc.controller.designer.dto.DesignerJoinResponseDto;
import com.sctk.cmc.service.designer.dto.DesignerLoginParam;
import com.sctk.cmc.service.member.dto.MemberLoginParam;
import com.sctk.cmc.service.designer.DesignerService;
import com.sctk.cmc.service.member.MemberService;
import com.sctk.cmc.service.designer.dto.DesignerJoinParam;
import com.sctk.cmc.service.member.dto.MemberJoinParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final MemberService memberService;
    private final DesignerService designerService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup/member")
    public BaseResponse<MemberJoinResponseDto> signupMember(@RequestBody MemberJoinParam param) {

        Long memberId = memberService.join(param);
        MemberJoinResponseDto responseDto = MemberJoinResponseDto.of(memberId);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/signin/member")
    public BaseResponse<LoginResponseDto> signinMember(@RequestBody MemberLoginParam param) {

        LoginResponseDto responseDto = authService.loginMember(param.getEmail(), param.getPassword());

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/signup/designer")
    public BaseResponse<DesignerJoinResponseDto> signupDesigner(@RequestBody DesignerJoinParam param) {

        Long designerId = designerService.join(param);
        DesignerJoinResponseDto responseDto = DesignerJoinResponseDto.of(designerId);

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/signin/designer")
    public BaseResponse<LoginResponseDto> signinDesigner(@RequestBody DesignerLoginParam param) {

        LoginResponseDto responseDto = authService.loginDesigner(param.getEmail(), param.getPassword());

        return new BaseResponse<>(responseDto);
    }

    @PostMapping("/token/reissuance")
    public BaseResponse<TokenReissueResponseDto> tokenReissueMember(@RequestBody TokenReissueRequestDto requestDto) {

        TokenReissueResponseDto responseDto = jwtService.reissue(requestDto);

        return new BaseResponse<>(responseDto);
    }
}
