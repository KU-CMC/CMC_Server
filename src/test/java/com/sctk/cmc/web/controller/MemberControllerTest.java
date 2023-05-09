package com.sctk.cmc.web.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctk.cmc.common.exception.ResponseStatus;
import com.sctk.cmc.common.response.BaseResponse;
import com.sctk.cmc.domain.SizesByPart;
import com.sctk.cmc.service.abstractions.MemberService;
import com.sctk.cmc.service.dto.member.BodyInfoView;
import com.sctk.cmc.service.dto.member.MemberDetail;
import com.sctk.cmc.service.dto.member.MemberInfo;
import com.sctk.cmc.web.dto.member.BodyInfoPostRequest;
import com.sctk.cmc.web.dto.member.MemberDetailResponse;
import com.sctk.cmc.web.dto.member.MemberInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
// MvcResult 한글 깨짐
@Import(HttpEncodingAutoConfiguration.class)
class MemberControllerTest {
    static Long mockMemberId = 1L;

    @Autowired
    MockMvc mvc;

    @MockBean
    MemberService memberService;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(mockMemberId, "", List.of(new SimpleGrantedAuthority("MEMBER"))));
    }

    @Test
    public void member_detail_조회_테스트() throws Exception {
        // given
        MemberDetail mockDetail = new MemberDetail(
                "name",
                "nickname",
                "email",
                "profileImgUrl",
                "introduce"
        );

        MemberDetailResponse expectedResponse = new MemberDetailResponse(
                mockDetail.getName(),
                mockDetail.getNickname(),
                mockDetail.getEmail(),
                mockDetail.getProfileImgUrl(),
                mockDetail.getIntroduce()
        );

        when(memberService.retrieveDetailById(anyLong())).thenReturn(mockDetail);

        // when
        MvcResult result = mvc.perform(get(REQUEST_URI.MEMBER_DETAIL))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, MemberDetailResponse.class);
        BaseResponse<MemberDetailResponse> baseResponse = om.readValue(result.getResponse().getContentAsString(), javaType);
        MemberDetailResponse response = baseResponse.getData();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void member_info_조회_테스트() throws Exception {
        //given
        MemberInfo mockInfo = new MemberInfo(
                "name",
                "profileImgUrl",
                new BodyInfoView()
        );

        MemberInfoResponse expectedResponse = new MemberInfoResponse(
                mockInfo.getName(),
                mockInfo.getProfileImgUrl(),
                mockInfo.getBodyInfoView()
        );

        when(memberService.retrieveInfoById(anyLong())).thenReturn(mockInfo);

        //when
        MvcResult result = mvc.perform(get(REQUEST_URI.MEMBER_INFO))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, MemberInfoResponse.class);
        BaseResponse<MemberInfoResponse> response = om.readValue(result.getResponse().getContentAsString(), javaType);
        MemberInfoResponse infoResponse = response.getData();

        assertThat(infoResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void member_bodyInfo_등록_테스트() throws Exception {
        //given
        BodyInfoPostRequest request = new BodyInfoPostRequest(
                new SizesByPart(1, 2, 3, 4, 5, 6, 7, 8, 9)
        );

        //when
        MvcResult result = mvc.perform(post(REQUEST_URI.BODY_INFO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //then
        JavaType javaType = om.getTypeFactory().constructParametricType(BaseResponse.class, ResponseStatus.class);
        BaseResponse<ResponseStatus> response = om.readValue(result.getResponse().getContentAsString(), javaType);

        assertThat(response.getCode()).isEqualTo(ResponseStatus.SUCCESS.getCode());
    }

    static class REQUEST_URI {
        static String URI_PREFIX = "/api/v1";
        static String BASE = "/members";
        static String MEMBER_DETAIL = URI_PREFIX + BASE + "/detail";
        static String MEMBER_INFO = URI_PREFIX + BASE + "/" + mockMemberId + "/info";
        static String BODY_INFO = URI_PREFIX + BASE + "/body-info";
    }
}