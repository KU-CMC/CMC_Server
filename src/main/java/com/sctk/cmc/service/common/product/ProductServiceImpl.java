package com.sctk.cmc.service.common.product;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.DescriptionImg;
import com.sctk.cmc.domain.Product;
import com.sctk.cmc.domain.ThumbnailImg;
import com.sctk.cmc.repository.common.product.ProductRepository;
import com.sctk.cmc.service.common.product.dto.ProductGetDetailResponse;
import com.sctk.cmc.service.designer.product.dto.ProductGetInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCT_ILLEGAL_ID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductGetInfoResponse> retrieveAllInfoByDesignerId(Long designerId) {
        List<Product> productList = retrieveAllByDesignerId(designerId);

        return productList.stream()
                .map(ProductGetInfoResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public ProductGetDetailResponse retrieveDetailById(Long productId) {
        Product product = retrieveByDesignerIdAndId(productId);

        List<String> descriptionImgUrlList = convertDescriptionImgListToUrlList(product);
        List<String> thumbnailImgUrlList = convertThumbnailImgListToUrlList(product);

        return ProductGetDetailResponse.of(product, product.getDesigner(), thumbnailImgUrlList, descriptionImgUrlList);
    }

    private static List<String> convertDescriptionImgListToUrlList(Product product) {
        return product.getDescriptionImgList().stream()
                .map(DescriptionImg::getUrl)
                .collect(Collectors.toList());
    }

    private static List<String> convertThumbnailImgListToUrlList(Product product) {
        return product.getThumbnailImgList().stream()
                .map(ThumbnailImg::getUrl)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> retrieveAllByDesignerId(Long designerId) {
        return productRepository.findAllByDesignerId(designerId);
    }

    @Override
    public Product retrieveByDesignerIdAndId(Long productId) {
        return productRepository.findWithImgListById(productId)
                .orElseThrow(() -> new CMCException(PRODUCT_ILLEGAL_ID));
    }
}