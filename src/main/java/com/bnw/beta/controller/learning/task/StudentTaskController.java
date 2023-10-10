package com.bnw.beta.controller.learning.task;

import com.bnw.beta.domain.learning.dto.TaskDTO;
import com.bnw.beta.domain.learning.dto.TaskSendDTO;
import com.bnw.beta.domain.learning.dto.TaskSubmitDTO;
import com.bnw.beta.service.learning.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentTaskController {

    @Autowired
    private TaskService taskService;

    //전송된 숙제 조회하기
    @GetMapping("/taskList/{member_no}")
    public String selectTaskById(@PathVariable int member_no, Model model){
        member_no = 99;
        System.out.println("들어옴");
        List<TaskDTO> taskList = taskService.selectTaskById(member_no);

        System.out.println(taskList);
        model.addAttribute("taskList", taskList);
        return "learning/task/student/taskList";
    }

    //모달창 숙제 정보 불러오기
    @GetMapping("/taskDetail/{tasksend_no}")
    public String taskDetail(@PathVariable int tasksend_no, Model model){
        TaskSendDTO taskDetail = taskService.selectTaskByNo(tasksend_no);
        model.addAttribute("taskDetail", taskDetail);
        return "learning/task/student/taskDetail";
    }

    //숙제 저장하기 (미작성 -> 작성중)
    @PostMapping("/saveTask")
    public String saveTask(String member_id, @RequestParam int tasksend_no, @RequestParam int task_no,
                                    @RequestParam String tasksubmit_chapter, @RequestParam String tasksubmit_content,
                                    @RequestParam String tasksubmit_add){

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        member_id = "baduk";
        int member_no =  99;
        int result = taskService.wirteTask(tasksend_no, task_no, tasksubmit_chapter, tasksubmit_content, tasksubmit_add, member_id);
        if(result > 0){
            return "redirect:/student/taskList/" + member_no;
        }
        return "redirect:/student/taskDetail/" + tasksend_no;
    }

    //숙제 수정페이지
    @GetMapping("/taskModify/{tasksend_no}")
    public String taskModify(@PathVariable int tasksend_no, Model model){
        TaskSubmitDTO taskSubmit = taskService.modifyTask(tasksend_no);
        System.out.println(taskSubmit);
        model.addAttribute("taskSubmit", taskSubmit);
        return "learning/task/student/taskModify";
    }

    //숙제 수정하기
    @PostMapping("/taskModify")
    public String taskModify(@RequestParam int tasksend_no, @RequestParam String tasksubmit_chapter,
                                                @RequestParam String tasksubmit_content, @RequestParam String tasksubmit_add){
        int member_no = 99;

        int result = taskService.ModifySubmitTask(tasksend_no,tasksubmit_chapter,tasksubmit_content,tasksubmit_add);
        System.out.println(result);
        if(result>0){
            return "redirect:/student/taskList/" + member_no;
        }
        return "redirect:/student/taskModify/" + tasksend_no;
    }

    //숙제 제출하기
    @PostMapping("/submitTask")
    public String submitTask(@RequestParam("tasksend_no[]") List<Integer> tasksend_no){
        int member_no = 99;
        int result = taskService.submitTask(tasksend_no);
        if(result > 0){
            return "/student/submitTaskList/" + member_no;
        }
        return "redirect:/student/taskList/" + member_no;
    }
}
