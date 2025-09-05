package br.com.fiap.epictaskg.task;

import br.com.fiap.epictaskg.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{task.title.notblank}")
    private String title;

    @Size(min = 20, max = 255, message = "{task.description.size}")
    private String description;

    @Min(value = 1, message = "{task.score.min}")
    @Max(value = 100, message = "{task.score.max}")
    private int score;

    @Min(0) @Max(100)
    private int status;

    @ManyToOne
    private User user;

}
