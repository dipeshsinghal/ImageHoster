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

    //This method will add a comment on a images and redirect to same image with updated comment
    // Method will be called from images/image.html with image id and image title as path Variable with new Comment
    @RequestMapping(value = "/image/{id}/{title}/comments", method = RequestMethod.POST)
    public String commentImageSubmit(@PathVariable("id") Integer imageId, @PathVariable("title") String title, Comment comment, HttpSession session) throws IOException {

        //Get Image Object using imageId for which comment need to add
        Image image = imageService.getImage(imageId);

        //set image_id variable of Comment object
        comment.setImage(image);

        //get current logged in user from HTTPSession Object
        User user = (User) session.getAttribute("loggeduser");

        //set user_id variable of Comment object
        comment.setUser(user);

        //set date of Comment as current date
        comment.setDate(new Date());

        //add new comment in the list of existing comments for this image
        image.getComments().add(comment);

        //Now call createComment from service to add this comment into database
        commentService.createComment(comment);

        //redirect to same image one comment is added
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
