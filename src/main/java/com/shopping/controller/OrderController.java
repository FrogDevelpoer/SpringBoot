package com.shopping.controller;

import com.shopping.dto.OrderDto;
import com.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // @ResponseBody @RequestBody : 스프링의 비동기 처리(ajax) 시에 사용되는 어노테이션
    // @ResponseBody는 자바 객체를 다시 http 응답 객체로 변경해줌
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){
        // @RequestBody는 커맨드 객체가 JSON으로 넘어 오는데 이것을 자바의 객체로 변환시켜주는 역할 수행

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            // HttpEntity는 요청이나 응답에 대한 결과를 처리해주는 Entity
            // HttpEntity = HttpHeader + HttpBody
            // ResponseEntity의 슈퍼 클래스는 HttpEntity
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();

        Long orderId;

        try{
            orderId = orderService.order(orderDto, email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
