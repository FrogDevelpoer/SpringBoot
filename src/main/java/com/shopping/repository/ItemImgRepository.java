package com.shopping.repository;

import com.shopping.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 상품에 대한 이미지 정보를 위한 Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    // 특정 상품(Item)과 연관된 상품 이미지(ItemImg)를 아이디를 이용하여 오름차순으로 정렬하여 조회
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
