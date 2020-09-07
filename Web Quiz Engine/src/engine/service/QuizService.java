package engine.service;

import engine.exception.NotPermittedException;
import engine.exception.QuizNotFoundException;
import engine.exception.UserNotFoundException;
import engine.model.Completion;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompletionRepository completionRepository;

    public boolean solve(Long id, Set<Integer> answer, String username) {
        Quiz quiz = findById(id);

        if (Objects.equals(answer, quiz.getAnswer())) {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(UserNotFoundException::new);
            Completion completion = new Completion(user, quiz);
            completionRepository.save(completion);
            return true;
        } else {
            return false;
        }
    }

    public Long create(Quiz quiz, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);

        quiz.setCreatedBy(user);
        return quizRepository.save(quiz).getId();
    }

    public Quiz findById(long id) {
        return quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
    }

    public void delete(long quizId, String username) {
        Quiz quiz = findById(quizId);
        User user = userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);
        if (Objects.equals(quiz.getCreatedBy().getId(), user.getId())) {
            quizRepository.delete(quiz);
        } else {
            throw new NotPermittedException();
        }
    }

    public Page<Quiz> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }
}
