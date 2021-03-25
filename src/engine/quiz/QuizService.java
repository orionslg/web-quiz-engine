package engine.quiz;

import engine.exceptions.QuizNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public List<Quiz> getQuizzes() {
        return (List<Quiz>) this.quizRepository.findAll();
    }

    public Quiz getQuiz(Long id) {
        Optional<Quiz> result = this.quizRepository.findById(id);
        return result.orElseThrow(QuizNotFoundException::new);
    }

    public Quiz saveQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    public boolean checkQuiz(Long id, int[] userAnswer) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
        int[] quizAnswer = quiz.getAnswer();

        if (quizAnswer == null && userAnswer.length == 0) {
            return true;
        }

        if (quizAnswer == null && userAnswer.length != 0) {
            return false;
        }

        if (userAnswer.length != quizAnswer.length) {
            return false;
        }

        if (userAnswer.length == 0 && quizAnswer.length == 0) {
            return true;
        } else {
            Arrays.sort(userAnswer);
            Arrays.sort(quizAnswer);
        }
        return Arrays.equals(userAnswer, quizAnswer);
    }
}
