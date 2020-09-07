package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Quiz title should not be empty")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Quiz text should not be empty")
    @Column(nullable = false)
    private String text;

    @ElementCollection
    @NotNull
    @Size(min = 2, message = "Quiz must contain at least two options")
    @Column(nullable = false)
    private List<String> options = Collections.emptyList();

    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> answer = Collections.emptySet();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User createdBy;

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Set<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
