package com.example.monthly.service.order;

import com.example.monthly.dto.*;
import com.example.monthly.mapper.OrderMapper;
import com.example.monthly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderMapper orderMapper;
    private final UserService userService;

    /**
     *
     * @param userNumber
     * @return "배송지 조회"
     * @throws IllegalArgumentException 회원 번호가 존재하지 않으면 배송지도 존재 하지 않아 발생된다.
     */
    public DeliveryDto findDelivery(Long userNumber){
        if (userNumber == null) {
            throw  new IllegalArgumentException("회원 번호 누락");
        }
        return Optional.ofNullable(orderMapper.select(userNumber)).orElseThrow(() -> {throw new IllegalArgumentException("존재하지 않는 회원 번호");});
    }

    /**
     *
     * @param deliveryDto 페이지에 있는 배송 정보를가져온다.
     * @see "조건을 달아서 특정 상황에 배송지를 수정하거나,등록할수있게 하였다."
     */
    public void ReDeliver(DeliveryDto deliveryDto){
        if (deliveryDto == null) {
            throw new IllegalArgumentException("배송지 정보 누락");
        }
        if(userService.findAll(deliveryDto.getUserNumber()).getDeliveryPostcode() != null){
            orderMapper.update(deliveryDto);
        }else {
            orderMapper.insert(deliveryDto);

        }
        orderMapper.update(deliveryDto);
    }

    //구독 추가
    public void subsRegister(SubsDto subsDto){
        if (subsDto == null) {
            throw new IllegalArgumentException("구독 정보 누락");
        }

        orderMapper.subsInsert(subsDto);
    }

//    카카오결제 정보 추가
    public void kakapayRegister(KakaoDto kakaoDto){
        if (kakaoDto == null) {
            throw new IllegalArgumentException("카카오 결제 정보 누락");
        }
        orderMapper.kakaoPay(kakaoDto);
    }

//    카드 정보 추가
    public void cardRegister(CardDto cardDto){
        if (cardDto == null) {
            throw new IllegalArgumentException("카드정보 누락");
        }
        orderMapper.cardInsert(cardDto);
    }

    //결제 정보 추가
    public void paymentRegister(PaymentDto paymentDto){
        if (paymentDto == null) {
            throw new IllegalArgumentException("결제 정보 누락");
        }
        SubsDto subs = subsFindAll(paymentDto.getUserNumber(),paymentDto.getProductNumber());
        paymentDto.setSubsNumber(subs.getSubsNumber());
        orderMapper.paymentInsert(paymentDto);
    }

//    배송주문 추가
    public void parcelRegister(ParcelDto parcelDto){
        if (parcelDto == null) {
            throw new IllegalArgumentException("배송주문 정보 누락");
        }

        orderMapper.parcelInsert(parcelDto);
    }

    /**
     *
     * @param userNumber
     * @param productNumber
     * @return
     * @throws IllegalArgumentException 상품,회원번호가 존재하지 않아 발생
     */
    //구독 정보 회원 번호,상품 번호로 조회
    @Transactional(readOnly = true)
    public SubsDto subsFindAll(Long userNumber, Long productNumber){
        if (userNumber == null) {
            throw new IllegalArgumentException("회원 번호 누락");
        }
        if (productNumber == null){
            throw new IllegalArgumentException("상품 번호 누락");
        }
        return Optional.ofNullable(orderMapper.subsSelect(userNumber,productNumber)).orElseThrow(() -> {throw new IllegalArgumentException("회원,상품 정보가 누락되었다.");});

    }

    /**
     *
     * @param userNumber
     * @param productNumber
     * @return
     * @throws IllegalArgumentException 상품,회원번호가 존재하지 않아 발생
     */
    //같은 회원 번호로 카드 번호 결제 번호 조회
    public Long payCardFind(Long productNumber,Long userNumber){
        if (productNumber == null) {
            throw new IllegalArgumentException("회원 번호 누락");
        }

        return Optional.ofNullable(orderMapper.payCardSelect(productNumber,userNumber)).orElseThrow(() -> {throw new IllegalArgumentException("회원,상품 번호 누락");});
    }



}
