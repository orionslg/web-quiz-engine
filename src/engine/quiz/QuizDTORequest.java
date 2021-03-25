package engine.quiz;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class QuizDTORequest {
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String text;

    @Size(min = 2)
    @NotNull
    private String[] options;

    private int[] answer;
}
