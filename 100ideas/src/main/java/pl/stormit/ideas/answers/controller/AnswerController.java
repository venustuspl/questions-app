package pl.stormit.ideas.answers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.ideas.answers.domain.dto.AnswerAddRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;

@Controller
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerFacade answerFacade;

    public AnswerController(AnswerFacade answerFacade) {
        this.answerFacade = answerFacade;
    }

    @GetMapping("/{id}")
    public String getAnswers(Model model, @PathVariable String id) {
        model.addAttribute("question", answerFacade.getQuestionById(id));
        model.addAttribute("answers", answerFacade.getAllAnswersByQuestionId(id));
        model.addAttribute("answerToAdd", new AnswerAddRequest());
        model.addAttribute("answerToUpdate", new AnswerUpdatedRequest());
        model.addAttribute("exception", model.containsAttribute("exception"));
        model.addAttribute("exceptionEdit", model.containsAttribute("exceptionEdit"));
        return "answer/answers";
    }

    @PostMapping
    public String addAnswer(AnswerAddRequest answerAddRequest, RedirectAttributes redirectAttributes) {
        try {
            answerFacade.addAnswer(answerAddRequest);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exception", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/answers/" + answerAddRequest.getQuestionId();
        }
        return "redirect:/answers/" + answerAddRequest.getQuestionId();
    }

    @PostMapping("/update")
    public String updateAnswer(AnswerUpdatedRequest answerUpdatedRequest, RedirectAttributes redirectAttributes) {
        String questionId = answerFacade.getQuestionId(answerUpdatedRequest);
        try {
            answerFacade.updateAnswer(answerUpdatedRequest);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/answers/" + questionId;
        }
        return "redirect:/answers/" + questionId;
    }

    @GetMapping("/{id}/delete")
    public String deleteAnswer(@PathVariable String id, RedirectAttributes redirectAttributes) {
        String questionId = answerFacade.getQuestionId(id);
        try {
            answerFacade.deleteAnswer(id);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/answers/" +questionId;
        }
        return "redirect:/answers/" + questionId;
    }
}
