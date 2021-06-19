package Controller;

import javax.swing.*;
import java.util.regex.Pattern;

public class GameFormValidation {

    private static final String ONLY_NUMBERS_REGEX = "[0-9]+([.][0-9]+)?";
    private static final String WHITESPACE_REGEX = "\\s+";

    private String final_error_msg;

    public GameFormValidation(){

        final_error_msg = "";
    }

    public boolean isFieldNotEmpty(JTextField field){

        if(field.getText().length() == 0 || Pattern.matches(WHITESPACE_REGEX, field.getText())){

            final_error_msg += "The field " + field.getName() + " cant be empty \n";
            return false;
        }
        return true;
    }

    public boolean isFieldNumeric(JTextField field){

        if(!Pattern.matches(ONLY_NUMBERS_REGEX, field.getText())){

            final_error_msg += "The field " + field.getName() + " has to be a numeric value \n";
            return false;
        }
        return true;
    }

    public String getFinalErrorMsg(){

        return final_error_msg;
    }

    public void resetFinalErrorMsg(){

        final_error_msg = "";
    }
}
