package com.example.monthly.controller.user;


import com.example.monthly.dto.DeliveryDto;
import com.example.monthly.dto.UserDto;
import com.example.monthly.service.order.OrderService;
import com.example.monthly.service.user.UserService;
import com.example.monthly.vo.DeliveryVo;
import com.example.monthly.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


// 로그인, 회원가입, 정보수정 등
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserController {
    private final UserService userService;

    private final OrderService orderService;

    @GetMapping("/mypage")
    public void mypage(){

    }


    @GetMapping("/userModify")
    public String userModify(Long userNumber, Model model){
        //        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
        userNumber = 74L;
        DeliveryVo user = userService.findAll(userNumber);
        model.addAttribute("user",user);
        return "user/userModify";
    }

    @PostMapping("/userModify")
    public String userModify(UserDto userDto, DeliveryDto deliveryDto, String checkPassword, HttpServletRequest req){
//        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
       userDto.setUserNumber(1L);
       userDto.setUserPassword(checkPassword);
       userService.updatePassword(userDto);

       //배송지 수정 (세션에서 받아온 userNumber 넣기)
       deliveryDto.setUserNumber(74L);

        System.out.println(deliveryDto);

       if(userService.findAll(74L).getDeliveryPostcode() != null){
           orderService.changeDelivery(deliveryDto);
       }else {
            orderService.registerDelivery(deliveryDto);

       }

        return "user/mypage";

    }

    @GetMapping("/changeStatus")
    public RedirectView userWithdraw(Long userNumber ,HttpServletRequest req){
//        Long userNumber = (Long) req.getSession().getAttribute("userNumber");
        userService.changeStatus(1L);
        return new RedirectView("/user/login");
    }



    @GetMapping("/login")
    public String login(){
        return "user/login"; }

    @PostMapping("/login")
    public RedirectView login(String userId, String userPassword, HttpServletRequest req, RedirectAttributes attributes) {
        try {
            Long userNumber = userService.userLogin(userId, userPassword);
            req.getSession().setAttribute("userNumber", userNumber);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("loginError", true);
            attributes.addFlashAttribute("errorMessage", "아이디나 비밀번호가 올바르지 않습니다. 다시 시도해주세요.");
            return new RedirectView("/user/login");
        }

        return new RedirectView("/board/main");
    }


    @GetMapping("/join")
    public String join(){
        return "user/join"; }

    @GetMapping("/checkId")
    public int checkId(String userId){
        return userService.checkId(userId);
    }

    @PostMapping("/joinOK")
    public String joinOK(UserVo userVo, Model model) {
        userService.register(userVo);
        model.addAttribute("user", userVo);
        return "user/join_ok";
    }


    @GetMapping("/findId")
    public String findId(){
        return "user/find_id"; }


    @PostMapping("/findIdOK")
    public String findIdOK(UserVo userVo, Model model) {
        userService.findId(userVo);
        model.addAttribute("user", userVo);
        return "user/find_id_ok"; }

    @GetMapping("/findPw")
    public String findPw(){
        return "user/find_pw"; }

    @PostMapping("/findPwOK")
    public String findPwOK(UserVo userVo, Model model){
        userService.findPw(userVo);
        model.addAttribute("user", userVo);
        return "user/find_pw_ok"; }


}
