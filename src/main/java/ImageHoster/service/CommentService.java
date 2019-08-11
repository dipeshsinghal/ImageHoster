package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    //Call the createComment() method in the Repository to update comment for a image
    public Comment createComment(Comment comment) {
        return commentRepository.createComment(comment);
    }
}
