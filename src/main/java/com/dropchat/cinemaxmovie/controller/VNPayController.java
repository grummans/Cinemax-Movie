package com.dropchat.cinemaxmovie.controller;

import com.dropchat.cinemaxmovie.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class VNPayController {

    private final VNPayService vnPayService;

    @PostMapping("/submitOrder/{code}")
    public String submitOrder(@PathVariable("code") String code,
                              HttpServletRequest request) throws UnsupportedEncodingException {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return vnPayService.createOrder(code, baseUrl);
    }

    @GetMapping("/vnPay-payment")
    public String confirmPayment(HttpServletRequest request) {
        return vnPayService.orderReturn(request) == 1 ? "orderSuccess" : "orderFail";
    }
}
