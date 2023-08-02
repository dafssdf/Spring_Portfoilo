package com.example.monthly.service.board;

import com.example.monthly.dto.ProductDto;
import com.example.monthly.mapper.ProductMapper;
import com.example.monthly.vo.Criteria;
import com.example.monthly.vo.ProductVo;
import com.example.monthly.vo.SearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiInternalFrameUI;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductFileService productFileService;

    //조회
    @Transactional(readOnly = true)
    public ProductVo productView(Long productNumber){
        if (productNumber == null) {
            throw new IllegalArgumentException("상품번호 누락");
        }
        return Optional.ofNullable(productMapper.selectProduct(productNumber))
                .orElseThrow(() -> {throw new IllegalArgumentException("존재하지 않는 상품");});
    }

//   상품등록
    public void registProduct(ProductDto productDto, List<MultipartFile>files, MultipartFile file){
        if(productDto == null){
            throw new IllegalArgumentException("상품정보 누락");
        }
        productMapper.insertProduct(productDto);
        //       +파일등록추가
        if(!files.isEmpty()){
            try {
//                상세사진리스트등록
                productFileService.registerAndSaveFiles(files, "d", productDto.getProductNumber());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!file.isEmpty()){
            try {
//                대표사진등록
                productFileService.registerAndSaveMainFile(file,"m", productDto.getProductNumber());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

//    상품수정
    public void modifyProduct(ProductDto productDto){
        if(productDto == null){
            throw new IllegalArgumentException("상품 수정 정보 누락");
        }
        productMapper.updateProduct(productDto);
    }

//    리스트에서 상품상태수정
    public void modifyStatus(ProductDto productDto){
        if(productDto == null){
            throw new IllegalArgumentException("상품 판매상태 정보 누락");
        }
        productMapper.updateStatus(productDto);
    }

    public void modifyProduct(ProductDto productDto, List<MultipartFile>files, MultipartFile file){
        if(productDto==null || files==null || file==null){
            throw new IllegalArgumentException("제품 수정 매개변수 null체크");
        }
        Long productNumber = productDto.getProductNumber();
        System.out.println(productNumber);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(files.get(0));
        if(!files.get(0).isEmpty()){
            System.out.println("==============================================");
            System.out.println(files);
        productFileService.remove(productNumber); //원래있던 파일은 지우고
            try {
                productFileService.registerAndSaveFiles(files, "d",productNumber); //리스트다시저장
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!file.isEmpty()){
            productFileService.removeMainFile(productNumber);
            try {
                productFileService.registerAndSaveMainFile(file, "m",productNumber); //대표사진다시저장
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productMapper.updateProduct(productDto);
    }


//    상품전체리스트조회
    @Transactional(readOnly = true)
    public List<ProductVo> findAllProduct(Long sellerNumber){
        if(sellerNumber==null){throw new IllegalArgumentException("판매자번호 누락");}
       return productMapper.selectList(sellerNumber);
    }
//      상품 전체갯수조회
    @Transactional(readOnly = true)
    public int getTotal(Long sellerNumber){
        if(sellerNumber==null){throw new IllegalArgumentException("판매자번호 누락");}
        return productMapper.selectTotal(sellerNumber);}
//      검색조건 넣어서 상품 갯수조회
    @Transactional(readOnly = true)
    public int getSearchTotal(Long sellerNumber, SearchVo searchVo){
        if(sellerNumber==null||searchVo ==null){throw new IllegalArgumentException("검색정보누락");}
        return productMapper.selectSearchTotal(sellerNumber,searchVo);
    }
//      상품전체리스트 페이징처리 조회
    public List<ProductVo> findListPage(Criteria criteria, Long sellerNumber){
        if(criteria==null || sellerNumber ==null){
            throw new IllegalArgumentException("페이징 정보 누락");
        }
        return productMapper.selectListPage(criteria, sellerNumber);
    }

    /**
     *
     * @param productVo 데이터베이스에 저장되어있는 상품 수량을 가져오기 위한 vo
     * @param productAmount 페이지에서 받아온 상품 수량
     */
//    상품 수량 변경
    public void amountChange(ProductVo productVo,String productAmount){
        if (productVo == null) {
            throw new IllegalArgumentException("상품 수량 등 정부 누락");
        }
        Long amount = productVo.getProductAmount() - Integer.valueOf(productAmount);
        productVo.setProductAmount(amount);
        productMapper.amountChange(productVo);
    }

//    검색조건 넣어서 상품 전체리스트 조회
    @Transactional(readOnly = true)
    public List<ProductVo> searhProduct(Long sellerNumber, SearchVo searchVo,Criteria criteria){
        System.out.println("===============검색 productService진입==================");
        if(sellerNumber==null || searchVo==null){throw new IllegalArgumentException("검색정보누락");}
        return productMapper.selectSearchProduct(sellerNumber, searchVo, criteria);
    }
}
