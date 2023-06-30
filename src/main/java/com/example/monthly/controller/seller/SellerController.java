package com.example.monthly.controller.seller;

import com.example.monthly.dto.BrandDto;
import com.example.monthly.dto.BrandFileDto;
import com.example.monthly.dto.ProductDto;
import com.example.monthly.dto.SellerDto;
import com.example.monthly.service.board.BrandFileService;
import com.example.monthly.service.board.BrandService;
import com.example.monthly.service.board.ProductFileService;
import com.example.monthly.service.board.ProductService;
import com.example.monthly.service.seller.SellerService;
import com.example.monthly.vo.Criteria;
import com.example.monthly.vo.PageVo;
import com.example.monthly.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

// 판매자 브랜드관리, 제품등록, 제품관리 등과 관련
@Controller
@RequiredArgsConstructor
@RequestMapping("/seller/*")
public class SellerController {
    private final SellerService sellerService;
    private final BrandService brandService;
    private final BrandFileService brandFileService;
    private final ProductService productService;
    private final ProductFileService productFileService;

    @GetMapping("/login")
    public String login(){ return "seller/seller_login"; }

    @PostMapping("/login")
    public RedirectView login(String sellerId, String sellerPassword, HttpServletRequest req){
        try {
            Long sellerNumber = sellerService.findSellerNumber(sellerId,sellerPassword);
            req.getSession().setAttribute("sellerNumber",sellerNumber);

            Long brandNumber = brandService.checkBrandNumber(sellerNumber);
            if(brandNumber != null){
                req.getSession().setAttribute("brandNumber",brandNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/seller/login");
        }

        return new RedirectView("/seller/main");
    }
    @GetMapping("/main")
    public String main(){
        return "seller/seller_main"; }

    @GetMapping("/apply")
    public String apply(){
        return "seller/seller_apply";
    }

    @PostMapping("/apply")
    public RedirectView sellerApply(SellerDto sellerDto, RedirectAttributes redirectAttributes){
        sellerService.sellerApply(sellerDto);
        redirectAttributes.addFlashAttribute("sellerId",sellerDto.getSellerId());
        redirectAttributes.addFlashAttribute("sellerEmail",sellerDto.getSellerEmail());
        redirectAttributes.addFlashAttribute("sellerName",sellerDto.getSellerName());
        return new RedirectView("/seller/apDone");
    }
    @GetMapping("/apDone")
    public String applyDone(){return "seller/seller_apply_done";}

    @GetMapping("/brand")
    public String brand(Model model, HttpServletRequest req){
        Long sellerNumber = (Long) req.getSession().getAttribute("sellerNumber");
        SellerDto sellerDto = sellerService.findSellerInfo(sellerNumber);
        BrandDto brandDto = brandService.findBrandInfo(sellerNumber);
//        조회를 비동기로 해와서 페이지 진입하자마자 띄워준다면 필요없다.
//        BrandFileDto brandFileDto = brandFileService.findBrandFile(sellerNumber);
//        model.addAttribute("brandFile", brandFileDto);
        model.addAttribute("seller", sellerDto);
        model.addAttribute("brand", brandDto);
        return "seller/seller_brand";}

    @GetMapping("/list")
    public String productList(HttpServletRequest req, /*Criteria criteria,*/ Model model){
        Long sellerNumber = (Long)req.getSession().getAttribute("sellerNumber");
        Criteria criteria = new Criteria(1,10);
//        criteria.setAmount(10);
        List<ProductVo> productVoList = productService.findAllProduct(criteria, sellerNumber);
        model.addAttribute("productList", productVoList);
        model.addAttribute("pageInfo", new PageVo(criteria, productService.getTotal(sellerNumber)));
        return "seller/seller_product_list";}

    @GetMapping("/modify")
    public String productModify(Long productNumber){return "seller/seller_product_modify";}

//    브랜드번호가 있으면 제품등록하러갈 수 있음
    @GetMapping("/registerReady")
    public String productRegisterReady(){
        return "seller/seller_product_register";
    }

    @GetMapping("/register")
    public RedirectView productRegister(HttpServletRequest req){
        Long brandNumber = null;
        try {
          brandNumber = (Long)req.getSession().getAttribute("brandNumber");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(brandNumber == null){
            return new RedirectView("/seller/brand");
        }
        System.out.println("=======$$$$$$$$$$$$$$$$"+brandNumber);
        return new RedirectView("/seller/registerReady");
    }

    @PostMapping("/register")
    public RedirectView productRegist(ProductDto productDto, HttpServletRequest req,
                                @RequestParam("productFiles")List<MultipartFile> files,
                                @RequestParam("productFile")MultipartFile file){
       Long brandNumber = (Long)req.getSession().getAttribute("brandNumber");
       productDto.setBrandNumber(brandNumber);
       productService.registProduct(productDto, files, file);

    return new RedirectView("/seller/list");
    }


    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest req){
        req.getSession().invalidate();
        return new RedirectView("/seller/login");
    }

}
