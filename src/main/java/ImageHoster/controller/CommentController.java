package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;


    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/images/{id}/{title}/comments", method = RequestMethod.POST)
    public String commentImageSubmit(@PathVariable("id") Integer imageId, @PathVariable("title") String title, Comment comment, HttpSession session) throws IOException {

        Image image = imageService.getImage(imageId);
        comment.setImage(image);

        User user = (User) session.getAttribute("loggeduser");
        comment.setUser(user);

        comment.setDate(new Date());

        image.getComments().add(comment);

        commentService.createComment(comment);

        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
