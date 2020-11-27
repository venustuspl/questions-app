package pl.stormit.ideas.answers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.util.UUID;

@Controller
@RequestMapping("/test")
public class TestController {
    private final AnswerService answerService;
    private final QuestionRepository questionRepository;

    public TestController(AnswerService answerService, QuestionRepository questionRepository) {
        this.answerService = answerService;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public String getMainView(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "testMainView";
    }

    @GetMapping("/answer/{id}")
    public String getAnswersByQuestionId(Model model, @PathVariable UUID id) {
        model.addAttribute("answers", answerService.getAllAnswersByQuestionId(id));
        return "testAnswerView";
    }
}
