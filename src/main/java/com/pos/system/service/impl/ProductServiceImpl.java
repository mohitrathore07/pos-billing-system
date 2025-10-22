package com.pos.system.service.impl;

import com.pos.system.Modal.Product;
import com.pos.system.Modal.Store;
import com.pos.system.Modal.User;
import com.pos.system.mapper.ProductMapper;
import com.pos.system.payload.dto.ProductDto;
import com.pos.system.repository.ProductRepository;
import com.pos.system.repository.StoreRepository;
import com.pos.system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto, User user) throws Exception {
        Store store =  storeRepository.findById(productDto.getStoreId()).orElseThrow(
                () -> new Exception("Store not found")
        );

        Product product = ProductMapper.toEntity(productDto,store);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception("product not found")
        );

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setMrp(product.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setBrand(productDto.getBrand());
        product.setUpdatedAt(productDto.getUpdatedAt());
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception("product not found")
        );

        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getProductsByStoreId(Long storeId) throws Exception {
        List<Product> products = productRepository.findByStoreId(storeId);

        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword) throws Exception {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}
