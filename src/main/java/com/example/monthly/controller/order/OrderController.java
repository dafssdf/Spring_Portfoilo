package com.example.monthly.controller.order;

import com.example.monthly.dto.DeliveryDto;
import com.example.monthly.dto.ParcelDto;
import com.example.monthly.dto.PaymentDto;
import com.example.monthly.dto.SubsDto;
import com.example.monthly.service.board.ProductService;
import com.example.monthly.service.order.OrderService;
import com.example.monthly.service.user.UserService;
import com.example.monthly.vo.DeliveryVo;
import com.example.monthly.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

//주문, 결제, 배송 등과 관련
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/*")
@Slf4j
public class OrderController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final SubsDto subsDto= new SubsDto();

    @GetMapping("/order")
    public String order(Long productNumber, String parcelDate, String cnt, HttpServletRequest req,Model model){
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");
        DeliveryVo deliveryVo = userService.findAll(userNumber);
        ProductVo productVo = productService.productView(productNumber);
        model.addAttribute("delivery",deliveryVo);
        model.addAttribute("product",productVo);
        model.addAttribute("parcelDate",parcelDate);
        model.addAttribute("cnt",cnt);
        return "order/order";
    }

    @PostMapping("/subs")
    public RedirectView subs(@Param("productNumber") Long productNumber, HttpServletRequest req,
                             ParcelDto parcelDto,PaymentDto paymentDto,
                             DeliveryDto deliveryDto, String productAmount, String deilvery){
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

        //구독 추가
        subsDto.setProductNumber(productNumber);
        subsDto.setUserNumber(userNumber);
        System.out.println(subsDto+"구독 정보 입력=====================");
        orderService.subsRegister(subsDto);

        //상품 수량 빼기
        System.out.println("주문한 상품 수량"+productAmount);
        ProductVo productVo = productService.productView(productNumber);
        productService.amountChange(productVo,productAmount);


//       배송지 저장
        deliveryDto.setUserNumber(userNumber);
        System.out.println(deilvery+"라디오버튼 값==================================");
        System.out.println(deliveryDto+"===============배송지 저장================");
        if(deilvery.equals("new")) {
            orderService.ReDeliver(deliveryDto);
        }

        //결제 정보
        paymentDto.setUserNumber(userNumber);
        paymentDto.setProductNumber(productNumber);
        orderService.paymentRegister(paymentDto);
        System.out.println(paymentDto +"결제 정보 출력 ==================================");



        //배송 주문
        Long paymentNumber = orderService.payCardFind(productNumber,userNumber);
        parcelDto.setPaymentNumber(paymentNumber);
        orderService.parcelRegister(parcelDto);
        System.out.println(deliveryDto);
        System.out.println(parcelDto +"배송주문장 풀력 ============================================");


//        "redirect:/user/mypage";
        return new RedirectView("/user/mypage");
    }

}
