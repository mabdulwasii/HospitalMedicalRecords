package guru.oze.hospitalmedicalrecords.utils;

import guru.oze.hospitalmedicalrecords.utils.annotation.ValidPassword;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordValidatorConstraint implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

            // at least 8 characters
            new LengthRule(8, 255),

            // at least one upper-case character
            new CharacterRule(EnglishCharacterData.UpperCase, 1),

            // at least one lower-case character
            new CharacterRule(EnglishCharacterData.LowerCase, 1),

            // at least one digit character
            new CharacterRule(EnglishCharacterData.Digit, 1),

            // at least one symbol (special character)
            new CharacterRule(EnglishCharacterData.Special, 1),

            // no whitespace
            new WhitespaceRule()

            // no common passwords
//            dictionaryRule
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        	context.buildConstraintViolationWithTemplate(messageTemplate)
        	.addConstraintViolation()
        		.disableDefaultConstraintViolation();
        return false;
    }
}
