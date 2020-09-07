package engine.controller;

import engine.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/quizzes")
public class CompletionController {

    @Autowired
    private CompletionRepository completionRepository;

    @GetMapping("completed")
    public Page<CompletionDto> getCompletionsByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @Autowired Principal principal) {

        Pageable pageable = PageRequest.of(page, 10);
        return completionRepository.findByUser(principal.getName(), pageable)
                .map(c -> new CompletionDto(c.getQuiz().getId(), c.getCompletedAt()));
    }

    private static class CompletionDto {
        private Long id;
        private LocalDateTime completedAt;

        public CompletionDto() {}

        public CompletionDto(Long id, LocalDateTime completedAt) {
            this.id = id;
            this.completedAt = completedAt;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDateTime getCompletedAt() {
            return completedAt;
        }

        public void setCompletedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
        }
    }
}
