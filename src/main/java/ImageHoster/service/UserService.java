package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public boolean registerUser(User newUser) {

        //validate password complexity rule before allowing to save user registration data to database
        if(!validatePasswordRules(newUser.getPassword())) {
            return false;
        }

        //registration user data to database
        userRepository.registerUser(newUser);
        return true;
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

    //Call this function to validate password complexity rule
    private boolean validatePasswordRules(String password) {

        boolean containLetter = false;
        boolean containDigit = false;
        boolean containSpecialChar = false;

        //iterate each character of password String
        for(char letter : password.toCharArray()) {
            if ( (letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z' ) ) {
                //if found any alphabet character set containLetter to true
                containLetter = true;
            } else if ( letter >= '0' && letter <= '9') {
                //if found any digit character set containDigit to true
                containDigit = true;
            } else {
                //else set containSpecialChar to true
                containSpecialChar = true;
            }
            if( containLetter && containDigit && containSpecialChar ) {
                //once all above there are true mean password meet the complexity no further check required return true
                return true;
            }
        }
        return false;
    }

}
