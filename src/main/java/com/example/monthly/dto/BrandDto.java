package com.example.monthly.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class BrandDto {
    private Long brandNumber; //브랜드 넘버
    private Long sellerNumber; //파는사람 넘버
    private String brandName; //브랜드 이름
    private String brandContents; //브랜드 내용
    private String brandRegisterDate; //브랜드 등록일
}
