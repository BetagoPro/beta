package com.bnw.beta.controller.subscribe;

import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.subscribe.pay.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("pay")
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/cartList")
    public String cartList(Model model, @RequestParam("game_no") List<Integer> game_no) {
        List<payDTO> cartlist = payService.selectBuylist(game_no);
        System.out.println("test"+cartlist);
        model.addAttribute("cartlist", cartlist);
        return "subscribe/pay";
    }

    @PostMapping("/payment")
    public String submitPay(@RequestParam("game_no[]") List<Integer> game_no,
                            @RequestParam("game_date[]") List<String> game_date,
                            @RequestParam("pay_type") String pay_type,
                            payDTO payDTO, Principal principal) {
        System.out.println("테스트"+pay_type);
        String member_id = principal.getName();

        for (int i = 0; i < game_no.size(); i++) {
            Integer gameNo = game_no.get(i);
            String gameDate = game_date.get(i);
            payDTO.setMember_id(member_id);
            payDTO.setGame_no(gameNo);
            payDTO.setGame_date(gameDate);
            System.out.println(payDTO);
            payService.insertIntoPay(payDTO);
        }

        return "redirect:/list";
    }

    ///////////////매출////////////////
    @GetMapping("/test")
    public String get(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date,
                      @RequestParam(value = "pay_enddate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_enddate,
                      Model model) {

        System.out.println(pay_date+"dd"+pay_enddate);
        if (pay_date != null && pay_enddate == null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("dayList", payService.selectDaySales(pay_date));
            System.out.println(payService.selectDaySales(pay_date));
        } else if(pay_enddate != null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("comment2", pay_enddate);
            model.addAttribute("dayList", payService.selectMonthSales(pay_date,pay_enddate));
        }
        return "test";
    }

    @GetMapping("/test2")
    public String get2(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date,
                      @RequestParam(value = "pay_enddate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_enddate,
                      Model model) {

        if (pay_date != null && pay_enddate == null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("dayList", payService.selectDaySales(pay_date));
            System.out.println(payService.selectDaySales(pay_date));
        } else if(pay_enddate != null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("comment2", pay_enddate);
            model.addAttribute("dayList", payService.selectMonthSales(pay_date,pay_enddate));
        }
        return "test2";
    }

    @PostMapping("/test2")
    @ResponseBody
    public List<payDTO> post1(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date pay_date,
                              @RequestParam(value = "pay_date2", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date2,
                              Model model) {

        System.out.println("ss"+pay_date);
        System.out.println("dd"+pay_date2);
        System.out.println(payService.selectSalesDetail(pay_date,pay_date2));
        return payService.selectSalesDetail(pay_date,pay_date2);
    }
}
