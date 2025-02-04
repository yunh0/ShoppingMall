package com.yunho.shopping.controller;

import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProfileDto;
import com.yunho.shopping.dto.request.ProfileRequest;
import com.yunho.shopping.dto.request.SignUpRequest;
import com.yunho.shopping.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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

        ProfileDto profileDto = memberService.saveProfile(
                signupRequest.getPhoneNumber(),
                signupRequest.getAge(),
                signupRequest.getGender(),
                signupRequest.getIntroduction()
        );
        MemberDto memberDto = memberService.saveMember(
                signupRequest.getUserId(),
                signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.getName(),
                profileDto
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
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/profile";
        }

        ProfileDto profileDto = memberService.saveProfile(
                profileRequest.getPhoneNumber(),
                profileRequest.getAge(),
                profileRequest.getGender(),
                profileRequest.getIntroduction()
        );

        MemberDto tempMember = memberService.getTempMemberFromSession();

        MemberDto memberDto = memberService.saveMember(
                tempMember.userId(),
                tempMember.email(),
                tempMember.password(),
                tempMember.name(),
                profileDto
        );

        memberService.clearTempMemberSession();

        return "redirect:/";
    }
}
