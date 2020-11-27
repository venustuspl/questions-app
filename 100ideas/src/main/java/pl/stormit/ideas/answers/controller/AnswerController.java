package pl.stormit.ideas.answers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.ideas.answers.service.AnswerService;

import java.util.UUID;

@Controller
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @GetMapping("/{id}")
    public String getAnswer(Model model, @PathVariable UUID id) {
        model.addAttribute("answer", answerService.getAnswerById(id));
        return "answer/answer";
    }
}
