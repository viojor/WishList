package Controller;

import javax.swing.*;
import java.util.regex.Pattern;

public class FormValidator {

    private String final_error_msg;

    public FormValidator(){

        final_error_msg = "";
    }

    public boolean isFieldNotEmpty(JTextField field){

        if(field.getText().length() == 0 || Pattern.matches(Regex.WHITESPACE_REGEX, field.getText())){

            final_error_msg += "The field " + field.getName() + " cant be empty \n";
            return false;
        }
        return true;
    }

    public boolean isFieldNumericReal(JTextField field){

        if(!Pattern.matches(Regex.ONLY_REAL_NUMBERS_REGEX, field.getText())){

            final_error_msg += "The field " + field.getName() + " has to be a numeric value (float) \n";
            return false;
        }
        return true;
    }

    public boolean isFieldNumericInteger(JTextField field){

        if(!Pattern.matches(Regex.ONLY_INTEGER_NUMBERS_REGEX, field.getText())){

            final_error_msg += "The field " + field.getName() + " has to be a numeric value (integer) \n";
            return false;
        }
        return true;
    }

    public boolean isComboBoxNotEmpty(ComboBoxEditor field){

        if(field.getItem().toString().length() == 0 || Pattern.matches(Regex.WHITESPACE_REGEX, field.getItem().toString())){

            final_error_msg += "The field " + field.getEditorComponent().getName() + " cant be empty \n";
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
