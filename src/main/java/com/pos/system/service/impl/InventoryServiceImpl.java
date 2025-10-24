package com.pos.system.service.impl;

import com.pos.system.Modal.Branch;
import com.pos.system.Modal.Inventory;
import com.pos.system.Modal.Product;
import com.pos.system.mapper.InventoryMapper;
import com.pos.system.payload.dto.InventoryDto;
import com.pos.system.repository.BranchRepository;
import com.pos.system.repository.InventoryRepository;
import com.pos.system.repository.ProductRepository;
import com.pos.system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {
        Branch branch = branchRepository.findById(inventoryDto.getBranchId()).orElseThrow(() -> new Exception("branch not found id: "+inventoryDto.getBranchId()));
        Product product = productRepository.findById(inventoryDto.getProductId()).orElseThrow(() -> new Exception("Product not found"));

        Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch , product);

        return InventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found"));
                ;
        inventory.setQuantity(inventoryDto.getQuantity());
        return InventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found"));
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDto getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found"));
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);

        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);

        return inventories.stream().map(InventoryMapper::toDto).collect(Collectors.toList());
    }
}
