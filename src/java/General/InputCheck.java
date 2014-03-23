package General;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains a variety of methods for checking user input.
 * 
 * @author Tom
 */
public class InputCheck {
    
    
    
    /**
     * Check if an input has the required format. Uses regular expressions.
     * 
     * @param input the input
     * @param pattern the regular expression used to be matched with the input
     * @throws BadInputException if there is no match
     */
    public static void checkInput(String input, String pattern) throws BadInputException {        
        if (!input.matches(pattern)) {
            throw new BadInputException("The input has the wrong format");
        }
    }
    
    /**
     * Check if an input has the required format. Uses regular expressions.
     * 
     * @param inputName the name of the input
     * @param input the input
     * @param pattern the regular expression used to be matched with the input
     * @throws BadInputException if there is no match
     */
    public static void checkInput(String inputName, String input, String pattern) throws BadInputException {
        
        try{
            checkInput(input, pattern);
        }catch (BadInputException ex){
            throw new BadInputException(inputName+ " has the wrong format");
        }
       
    }
    
    /**
     * Check if an input has the required format and length. Uses regular expressions.
     * 
     * @param min the minimum number of characters this input consists of
     * @param max the maximum number of characters this input consists of
     * @param inputName the name of the input
     * @param input the input
     * @param characterClass a single character class to be searched for
     * @throws BadInputException if there is no match or the input is the wrong length
     */
    public static void checkInput(int min,int max,String inputName, String input, String characterClass) throws BadInputException {
        try{
            checkInput(input, characterClass + "{"+min+","+max+"}");
        }catch (BadInputException ex){
            throw new BadInputException(inputName+ " has the wrong format or has a length greater than "+max+" or less than "+min);
        }
    }
    
   

}
