package com.example.monthly.service.user;

import com.example.monthly.dto.ExSubsDto;
import com.example.monthly.dto.ProductDto;
import com.example.monthly.mapper.SubsMapper;
import com.example.monthly.vo.SubsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageService {

    private final SubsMapper subsMapper;

    /**
     *
     * @param userNumber 조회를 위한 회원 번호
     * @return 구독의 날짜를 저장할때 년-월-일의 데이터 형식으로 저장하기 위한 로직 구현
     */
//    내부 구독 정보 출력
    public List<SubsVo> subsFindAll(Long userNumber){
        if (userNumber == null) {
            throw new IllegalArgumentException("회원 번호 누락");
        }


        List<SubsVo> subs = subsMapper.subsSelect(userNumber);

        for(int i =0 ; i < subs.toArray().length; i++){
            String str = subs.get(i).getSubsStartDate().substring(0,10);
            subs.get(i).setSubsStartDate(str);
        }

        return subs;
    }

//    외부 구독 추가
    public void exSubsRegister(ExSubsDto exSubsDto){
        if (exSubsDto == null) {
            throw new IllegalArgumentException("외부 구독 정보 누락");
        }
        subsMapper.exSubsInsert(exSubsDto);
    }

    /**
     *
     * @param subs controller에서 조회한 회원의 내부 구독 리스트
     * @return 회원의 모든 내부구독료를 더해서 반환한다.
     */
//    내부 구독료 계산
    public int subsPrice(List<SubsVo> subs){
        int subsPrice = 0;
        for(int i =0 ; i < subs.toArray().length; i++){
            subsPrice += Integer.valueOf(subs.get(i).getPaymentPrice());
        }

        return subsPrice;
    }

    /**
     *
     * @param exSubs controller에서 조회한 회원의 외부 구독 리스트
     * @return 회원의 모든 외부 구독료를 더해서 반환해준다.
     */
//  외부 구독료 계산
    public int exSubsPrice(List<ExSubsDto> exSubs){
        int exPrice = 0;
        if (!exSubs.isEmpty()) {
            for (int i = 0; i < exSubs.size(); i++) {
                exPrice += Integer.valueOf(exSubs.get(i).getExSubsPrice());
            }
        }
        return exPrice;
    }


//    외부구독 검색
    public List<ExSubsDto> exSubsFindAll(Long userNumber){
        if (userNumber == null) {
            throw new IllegalArgumentException("회원 번호 누락");
        }
        return subsMapper.exSubsSelect(userNumber);
    }

//    구독 총수 검색
    public int subsCnt(Long userNumber){
        if (userNumber == null) {
            throw new IllegalArgumentException("회원번호 누락");

        }
        return subsMapper.subsCount(userNumber);
    }

    public int exSubsCnt(Long userNumber){
        if (userNumber == null) {
            throw new IllegalArgumentException("회원번호 누락");

        }
        return subsMapper.exSubsCount(userNumber);
    }

    //외부 구독 삭제
    public void exSubsRemove(ExSubsDto exSubsDto){
        if (exSubsDto == null) {
            throw new IllegalArgumentException("외부 구독 정보 누락");
        }

        subsMapper.exSubsDelete(exSubsDto);
    }

    //내부 구독 취소
    public void subsRemove(Long productNumber,Long userNumber){
        if (productNumber == null) {
            throw new IllegalArgumentException("상품 이름 누락");
        }
        if (userNumber == null) {
            throw new IllegalArgumentException("회원 번호 누락");
        }
        subsMapper.subsDelete(productNumber,userNumber);
    }

    //내부구독을 위한 상품 검색
    public Long productSubs(ProductDto productDto){
        if (productDto == null) {
            throw new IllegalArgumentException("상품 이름 누락");
        }
        return subsMapper.productSubs(productDto);
    }

    //내부구독 검색
    public Long reSubs(Long productNumber, Long userNumber){
        if (productNumber == null) {
            throw new IllegalArgumentException("상품번호 누락");
        }

        if (userNumber == null) {
            throw new IllegalArgumentException("회원번호 누락");
        }
        return subsMapper.reSubsSelect(productNumber,userNumber);
    }
}
