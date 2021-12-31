package Application.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationDictionary extends HashMap<String, String> {

    public ValidationDictionary validateString(String val, String key, String message){
        if (!stringIsNullOrWhiteSpace(val)) return this;

        message = stringIsNullOrWhiteSpace(message)
            ? String.format("The passed string is empty. [%s]", key)
                : message;

        put(key, message);
        return this;
    }

    private boolean stringIsNullOrWhiteSpace(String val){
        return val == null || Pattern.matches("^\\s*$", val);
    }

}
