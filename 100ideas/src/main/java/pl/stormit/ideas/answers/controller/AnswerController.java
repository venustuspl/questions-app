package pl.stormit.ideas.answers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.answers.utils.AnswerMapper;
import pl.stormit.ideas.questions.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final AnswerMapper answerMapper;

    public AnswerController(
            AnswerService answerService,
            QuestionService questionService,
            AnswerMapper answerMapper) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.answerMapper = answerMapper;
    }


    @GetMapping("/{id}")
    public String getAnswers(Model model, @PathVariable UUID id) {
        model.addAttribute("question", questionService.getQuestionById(id));
        model.addAttribute("answers", answerMapper.mapToAnswerResponseList(answerService.getAllAnswersByQuestionId(id)));
        model.addAttribute("answerToAdd", new AnswerRequest());
        model.addAttribute("answerToUpdate", new AnswerUpdatedRequest());
        model.addAttribute("exception", model.containsAttribute("exception"));
        model.addAttribute("exceptionEdit", model.containsAttribute("exceptionEdit"));
        return "answer/answers";
    }

    @PostMapping
    public String addAnswer(AnswerRequest answerRequest, RedirectAttributes redirectAttributes) {
        try {
            answerService.addAnswer(answerMapper.mapToAnswer(answerRequest));
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exception", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/answers/" + answerRequest.getQuestionId();
        }
        return "redirect:/answers/" + answerRequest.getQuestionId();
    }

    @PostMapping("/update")
    public String updateAnswer(AnswerUpdatedRequest answerUpdatedRequest, RedirectAttributes redirectAttributes) {
        Answer answer = answerMapper.mapToAnswer(answerUpdatedRequest);
        try {
            answerService.updateAnswer(answer);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/answers/" + answer.getQuestion().getId();
        }
        return "redirect:/answers/" + answer.getQuestion().getId();
    }

    @GetMapping("/{id}/delete")
    public String deleteAnswer(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        Answer answer = answerService.getAnswerById(id);
        try {
            answerService.deleteAnswer(answer);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/answers/" + answer.getQuestion().getId();
        }
        return "redirect:/answers/" + answer.getQuestion().getId();
    }
}
