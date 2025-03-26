package com.yunho.shopping.controller;

import com.yunho.shopping.dto.CustomPrincipal;
import com.yunho.shopping.dto.response.PurchaseHistoryResponse;
import com.yunho.shopping.service.PaginationService;
import com.yunho.shopping.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;
    private final PaginationService paginationService;

    @GetMapping("/myPage/purchaseHistory")
    public String getPurchaseHistory(
            @AuthenticationPrincipal CustomPrincipal principal,
            Model model,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<PurchaseHistoryResponse> purchaseHistories =
                purchaseHistoryService.getPurchaseHistoryWithPaging(principal.getUsername(), pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), purchaseHistories.getTotalPages());

        model.addAttribute("purchaseHistories", purchaseHistories);
        model.addAttribute("paginationBarNumbers", barNumbers);

        return "/purchaseHistory";
    }
}
