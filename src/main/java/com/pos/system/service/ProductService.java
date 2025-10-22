package com.pos.system.service;

import com.pos.system.Modal.User;
import com.pos.system.payload.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, User user) throws Exception;
    ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;
    List<ProductDto> getProductsByStoreId(Long storeId) throws Exception;
    List<ProductDto> searchByKeyword(Long storeId, String keyword) throws Exception;
}
