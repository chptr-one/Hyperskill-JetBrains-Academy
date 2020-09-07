package engine.controller;

import engine.WebQuizEngine;
import engine.model.Feedback;
import engine.model.Quiz;
import engine.service.QuizService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/quizzes")
public class QuizController {

    private final Logger logger = WebQuizEngine.logger;

    @Autowired
    private QuizService service;

    @PostMapping(path = "/{id}/solve")
    public Feedback solveQuiz(@PathVariable long id,
                              @RequestBody Map<String, Set<Integer>> answer,
                              @Autowired Principal principal) {
        Set<Integer> answers = answer.get("answer");
        logger.info("Solving a quiz {} with answer {}", id, answers);
        return service.solve(id, answers, principal.getName()) ?
                Feedback.SUCCESS : Feedback.FAILURE;
    }

    @PostMapping()
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz,
                           @Autowired Principal principal) {
        logger.info("Try to create quiz '{}' by user '{}'", quiz, principal.getName());

        service.create(quiz, principal.getName());
        return quiz;
    }

    @GetMapping(path = "/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return service.findById(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id,
                           @Autowired Principal principal) {
        logger.info("Try to delete quiz with id '{}' by user '{}'", id, principal.getName());
        service.delete(id, principal.getName());
    }

    @GetMapping()
    public Page<Quiz> getQuizPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        return service.findAll(page, 10, "id");
    }
}
