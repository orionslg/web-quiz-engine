package engine.quiz;

import engine.answer.AnswerRequest;
import engine.answer.AnswerResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizDTOResponse> getQuiz(@PathVariable Long id) {
        Quiz quiz = this.quizService.getQuiz(id);
        return new ResponseEntity<>(this.convertToDTOResponse(quiz), HttpStatus.OK);
    }

    @GetMapping("/api/quizzes")
    public List<QuizDTOResponse> fetchQuizzes() {
        List<Quiz> quizzes = this.quizService.getQuizzes();
        return quizzes.stream()
                .map(this::convertToDTOResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<AnswerResponse> getAnswerResponse(@PathVariable Long id,
                                                            @RequestBody AnswerRequest userAnswer) {
        boolean answer = this.quizService.checkQuiz(id, userAnswer.getAnswer());
        if (answer) {
            AnswerResponse correctResponse = new AnswerResponse(true, "Congratulations, you're right!");
            return new ResponseEntity<>(correctResponse, HttpStatus.OK);
        } else {
            AnswerResponse wrongResponse = new AnswerResponse(false, "Wrong answer! Please, try again.");
            return new ResponseEntity<>(wrongResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/api/quizzes")
    public QuizDTOResponse createQuiz(@Valid @RequestBody QuizDTORequest quiz) {
        Quiz saveQuiz = this.quizService.saveQuiz(this.convertToEntity(quiz));
        return this.convertToDTOResponse(saveQuiz);
    }

    private Quiz convertToEntity(QuizDTORequest quizDTO) {
        return modelMapper.map(quizDTO, Quiz.class);
    }

    private QuizDTOResponse convertToDTOResponse(Quiz quiz) {
        return modelMapper.map(quiz, QuizDTOResponse.class);
    }
}
