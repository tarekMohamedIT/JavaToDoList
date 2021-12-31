package Application.Exceptions;

import Application.Utils.ValidationDictionary;

public class EntityValidationException extends RuntimeException{
    ValidationDictionary _validationErrors;

    public EntityValidationException(ValidationDictionary _validationErrors) {
        super("The validation failed due to validation errors.");
        this._validationErrors = _validationErrors;
    }
    public ValidationDictionary getValidationErrors() {
        return _validationErrors;
    }
}
