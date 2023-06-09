package com.sctk.cmc.service.designer;

import com.sctk.cmc.controller.designer.dto.*;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.service.designer.dto.FilteredDesignerInfo;
import com.sctk.cmc.service.designer.dto.DesignerInfo;
import com.sctk.cmc.service.designer.dto.DesignerJoinParam;
import com.sctk.cmc.controller.common.dto.ProfileImgPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface DesignerService {
    Long join(DesignerJoinParam param);

    Designer retrieveById(Long designerId);

    Designer retrieveByEmail(String email);

    DesignerInfo retrieveInfoById(Long designerId);

    boolean existsByEmail(String email);

    List<Designer> retrieveAllByName(String name);

    int registerCategories(Long designerId, List<CategoryParam> highCategories);

    List<CategoryView> retrieveAllCategoryViewById(Long designerId);

    List<CategoryView> retrieveOwnCategoryViewById(Long designerId);

    List<FilteredDesignerInfo> retrieveSortedBy(String criteria, int limit);

    List<FilteredDesignerInfo> retrieveAllFreshFrom(LocalDate targetDate, int limit);

    List<FilteredDesignerInfo> retrievePopularByLike(int limit);

    List<FilteredDesignerInfo> retrievePopularByCategory(int limit);

    ProfileImgPostResponse registerProfileImg(Long designerId, MultipartFile profileImg);

    PortfolioImgPostResponse registerPortfolioImg(Long designerId, MultipartFile portfolioImg);

    PortfolioImgGetResponse retrieveAllPortfolioImgById(Long designerId);

    PortfolioImgGetResponse retrieveAllOwnPortfolioImgById(Long designerId);

    void modifyCategories(Long designerId, List<CategoryParam> categoryParams);

    List<DesignerGetBySearchingResponse> searchAllByKeywordInNamesAndCategories(String keyword);
}
