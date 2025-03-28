package com.yunho.shopping.controller;

import com.yunho.shopping.dto.CustomPrincipal;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProfileDto;
import com.yunho.shopping.dto.request.ProfileRequest;
import com.yunho.shopping.dto.request.SignUpRequest;
import com.yunho.shopping.dto.response.PurchaseHistoryResponse;
import com.yunho.shopping.service.MemberService;
import com.yunho.shopping.service.PurchaseHistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/signin")
    public String signIn(){
        return "/signin";
    }

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("signupRequest", new SignUpRequest());
        return "/signup";
    }

    @PostMapping("/signup")
    public String signUp(
            @Valid @ModelAttribute("signupRequest") SignUpRequest signupRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            return "/signup";
        }

        MemberDto memberDto = memberService.saveMember(
                signupRequest.getUserId(),
                signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.getName(),
                ProfileDto.of(
                        signupRequest.getPhoneNumber(),
                        signupRequest.getAge(),
                        signupRequest.getGender(),
                        signupRequest.getIntroduction()
                )
        );

        return "redirect:/signin";
    }

    @GetMapping("/signup/profile")
    public String showProfileForm(Model model) {
        model.addAttribute("profileRequest", new ProfileRequest());
        return "/profile";
    }

    @PostMapping("/signup/profile")
    public String signUpWithOAuth2(
            @Valid @ModelAttribute("profileRequest") ProfileRequest profileRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "/profile";
        }

        memberService.setProfile(principal.getUsername(), profileRequest.toDto());

        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(
            @AuthenticationPrincipal CustomPrincipal principal,
            Model model
    ){
        MemberDto memberDto = memberService.searchMember(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. username: " + principal.getUsername()));
        List<PurchaseHistoryResponse> purchaseHistories = purchaseHistoryService.getPurchaseHistoryOrderByCreatedAtTop3(principal.getUsername());

        model.addAttribute("member", memberDto);
        model.addAttribute("purchaseHistories", purchaseHistories);

        return "/myPage";
    }

    @PostMapping("/myPage")
    public String updateProfile(
            @Valid @ModelAttribute("profileRequest") ProfileRequest profileRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomPrincipal principal,
            Model model
    ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("member", memberService.searchMember(principal.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. username: " + principal.getUsername())));
            model.addAttribute("purchaseHistories",
                    purchaseHistoryService.getPurchaseHistoryOrderByCreatedAtTop3(principal.getUsername())
            );
            return "/myPage";
        }

        memberService.updateProfile(principal.getUsername(), profileRequest.toDto());

        return "redirect:/myPage";
    }
}
