package com.shopping.repository;

import com.shopping.dto.ItemSearchDto;
import com.shopping.dto.MainItemDto;
import com.shopping.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    // 상품 조회 조건 itemSearchDto와 페이징 정보 pagable를 이용하여 데이터를 조회
    // Pagable는 JPA에서 페이징 처리를 도와주는 유틸리티 성격의 인터페이스
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    // 메인 페이지에서 보여줄 상품 리스트를 구해줌
   Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
