package utils;

public class MyStringUtils {

    public static String removeAllSpecialCharacters(String myString){
        return myString.replaceAll("[^a-zA-Z0-9]", "");
    }
}
