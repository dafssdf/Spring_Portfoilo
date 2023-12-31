package com.example.monthly.mapper;

import com.example.monthly.dto.BrandDto;
import com.example.monthly.dto.ProductDto;
import com.example.monthly.vo.BrandFileVo;
import com.example.monthly.vo.ProductFileVo;
import com.example.monthly.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BrandFileVo> brandSelect(Long brandNumber);
//    이것의 이름은  Service에서 만든 이름으로.. 괄호 안에는 매개변수가 들어가야하는데 지금 현재 안받아서 비워둠

    // 브랜드
    List<BrandFileVo> selectAllBrands();

    //제품
    List<ProductFileVo> productSelect();

    //검색
    List<ProductFileVo> searchProductList(@Param("searchInput") String searchInput);

    //브랜드 디테일
    List<ProductFileVo> brandDetail(Long brandNumber);

}
