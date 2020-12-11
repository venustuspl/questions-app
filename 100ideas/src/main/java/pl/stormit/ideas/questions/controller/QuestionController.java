package pl.stormit.ideas.questions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.domain.QuestionRequest;
import pl.stormit.ideas.questions.domain.QuestionUpdatedRequest;
import pl.stormit.ideas.questions.mapper.QuestionMapper;
import pl.stormit.ideas.questions.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final CategoryService categoryService;

    public QuestionController(AnswerService answerService, QuestionService questionService, QuestionMapper questionMapper, CategoryService categoryService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getAllQuestions());
        return "question/questions";
    }

    @GetMapping("/{id}")
    public String getQuestion(Model model, @PathVariable UUID id) {
        model.addAttribute("question", questionMapper.mapQuestionToQuestionResponse(questionService.getQuestionById(id)));
        model.addAttribute("questionToUpdate", new QuestionUpdatedRequest());
        model.addAttribute("exception", model.containsAttribute("exception"));
        model.addAttribute("exceptionEdit", model.containsAttribute("exceptionEdit"));
        return "question/questionupdate";
    }

    @PostMapping("/update")
    public String updateQuestion(QuestionUpdatedRequest questionUpdatedRequest, RedirectAttributes redirectAttributes) {
        Question question = questionMapper.mapQuestionUpdatedRequestToQuestion(questionUpdatedRequest);
        try {
            questionService.updateQuestion(question);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/questions/" + question.getId();
        }
        return "redirect:/questions/" + question.getId();
    }

    @GetMapping("/add")
    public String addQuestionPage(Model model) {
        model.addAttribute("questionToAdd", new QuestionRequest());
        model.addAttribute("exception", model.containsAttribute("exception"));
        return "question/questionadd";
    }

    @PostMapping("/add")
    public String addQuestion(QuestionRequest questionRequest, RedirectAttributes redirectAttributes) {
        try {
            questionService.addQuestion(questionMapper.mapQuestionRequestToQuestion(questionRequest));
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exception", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/questions/add";
        }
        return "redirect:/questions/";
    }

    @GetMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        Question question = questionService.getQuestionById(id);
        try {
            questionService.deleteQuestion(question);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/questions/" + question.getId();
        }

        return "redirect:/questions/";
    }

}