package com.bnw.beta.controller.admin;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.GameFileDTO;
import com.bnw.beta.domain.subscribe.dto.payDTO;
import com.bnw.beta.service.admin.game.GameService;
import com.bnw.beta.service.subscribe.pay.PayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;


@RequestMapping("/game")
@Controller
@RequiredArgsConstructor
public class GameController {
    private final PayService payService;
    private final GameService gameService;

    //게임콘텐츠 등록

    @GetMapping("/insert")
    public String insertGame() {
        return "admin/game/gameInsert";
    }

    @PostMapping("/gameInsert")
    public String insertGame(@ModelAttribute GameDTO dto, @RequestParam("imageFile") MultipartFile imageFile, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String member_id = "admin1";
        dto.setMember_id(member_id);
        int result = gameService.insertGame(dto);

        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            String filePath = "C:/Users/user/Desktop/BetaPro/beta/src/main/resources/static/image/guide/game/" +fileName;
            try {
                GameFileDTO gameFileDTO = new GameFileDTO();
                gameFileDTO.setGame_no(dto.getGame_no());
                gameFileDTO.setFilegame_name(fileName);
                gameFileDTO.setFilegame_path(filePath);

                gameService.insertGameImage(gameFileDTO);

                imageFile.transferTo(new File(filePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("message", "등록이 완료되었습니다.");
        return "redirect:/game/list";
    }

    //게임콘텐츠 조회
    @GetMapping("/list")
    public String selectAll(Model model) {
        List<GameDTO> gameList = gameService.selectAll();
        model.addAttribute("gameList", gameList);
        return "admin/game/gameList";
    }

    // 게임콘텐츠 제목 검색
    @GetMapping("/searchByTitle")
    public String searchByTitle(@RequestParam("game_title") String game_title, Model model) {
        if (game_title != null && !game_title.trim().isEmpty()) {
            // 사용자가 게임 제목을 선택한 경우에만 필터링
            List<GameDTO> filteredGames = gameService.searchByTitle(game_title);
            model.addAttribute("gameList", filteredGames);
        } else {
            // 선택한 게임이 없는 경우 전체 목록을 유지
            List<GameDTO> allGames = gameService.selectAll();
            model.addAttribute("gameList", allGames);
        }
        return "admin/game/gameList";
    }


    //////
    @GetMapping("/salesGraph")
    public String get(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date,
                      @RequestParam(value = "pay_enddate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_enddate,
                      Model model) {
        if (pay_date != null && pay_enddate == null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("dayList", payService.selectDaySales(pay_date));
        } else if (pay_enddate != null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("comment2", pay_enddate);
            model.addAttribute("dayList", payService.selectMonthSales(pay_date, pay_enddate));
        }
        return "admin/sales/salesGraph";
    }

    @GetMapping("/salesList")
    public String get2(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date,
                       @RequestParam(value = "pay_enddate", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_enddate,
                       Model model) {

        if (pay_date != null && pay_enddate == null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("dayList", payService.selectDaySales(pay_date));
        } else if (pay_enddate != null) {
            model.addAttribute("comment", pay_date);
            model.addAttribute("comment2", pay_enddate);
            model.addAttribute("dayList", payService.selectMonthSales(pay_date, pay_enddate));
        }
        return "admin/sales/salesList";
    }

    @PostMapping("/sales")
    @ResponseBody
    public List<payDTO> post1(@RequestParam(value = "pay_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date pay_date,
                              @RequestParam(value = "pay_date2", required = false) @DateTimeFormat(pattern = "yyyy-MM") Date pay_date2,
                              Model model) {
        return payService.selectSalesDetail(pay_date, pay_date2);
    }
}