package com.shopping.controller;

import com.shopping.dto.CartItemDto;
import com.shopping.service.CartService;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService ;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                         BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage()) ;
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName() ;
        Long cartItemId = 0L ;

        try{
            cartItemId = cartService.addCart(cartItemDto, email);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}