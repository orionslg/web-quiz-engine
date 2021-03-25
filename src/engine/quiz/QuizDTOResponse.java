package engine.quiz;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuizDTOResponse {
    private Long id;
    private String title;
    private String text;
    private String[] options;
}
