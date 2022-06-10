package com.shopping.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.constant.ItemSellStatus;
import com.shopping.dto.ItemSearchDto;
import com.shopping.entity.Item;
import com.shopping.entity.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements  ItemRepositoryCustom{

    // 쿼리를 build 시켜주는 클래스로 EntityManager를 매개 변수로 넣어준다
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QueryResults<Item> results = this.queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();


        // PageImpl 클래스는 Page 인터페이스의 구현체
        return new PageImpl<>(content, pageable, total);
    }

    // 조건식을 판단하여 결과가 true인 데이터들만 반환해주는 클래스
    // BooleanExpression 추상 클래스는 Predicate 인터페이스를 상속 받음
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        // 3가지 중에서 택일: 판매상태(전체), 판매, 품절
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("createdBy  ", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        // 사용자가 지정한 특정 기간 내의 데이터만 조회해주는 메소드
        LocalDateTime dateTime = LocalDateTime.now();   // 검색할 시간의 시작점

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        }else if(StringUtils.equals("1d", searchDateType)){     // 1일
            dateTime = dateTime.minusDays(1);

        }else if(StringUtils.equals("1w", searchDateType)){     // 1주
            dateTime = dateTime.minusWeeks(1);

        }else if(StringUtils.equals("1m", searchDateType)){     // 1개월
            dateTime = dateTime.minusMonths(1);

        }else if(StringUtils.equals("6m", searchDateType)){     // 6개월월
            dateTime = dateTime.minusMonths(6);
        }

        // after는 지정 날짜 이후, before는 지정 날짜 이전
        return QItem.item.regTime.after(dateTime);
    }
}
