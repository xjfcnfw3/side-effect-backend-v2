package sideeffect.project.domain.comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Embeddable
public class FreeComments {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "freeBoard", orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("id desc")
    private List<Comment> comments;

    public FreeComments() {
        this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }

    public int getCommentNumber() {
        return this.comments.size();
    }
}
