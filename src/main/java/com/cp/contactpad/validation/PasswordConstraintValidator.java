package com.cp.contactpad.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(final ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        //customizing validation messages
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // length between 8 and 16 character
        new LengthRule(8, 16),
        // at least one upper-case character
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        // at least one lower-case character
        new CharacterRule(EnglishCharacterData.LowerCase, 1),
        // at least one digit character
        new CharacterRule(EnglishCharacterData.Digit, 1),
        // at least one symbol (special character)
        new CharacterRule(EnglishCharacterData.Special, 1),
        // no whitespace
        new WhitespaceRule(),
        // rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)
        new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
        // rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345)
        new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        String messages = "Password must contain at least one digit, capital letter, and symbol - # $ %";
        context.buildConstraintViolationWithTemplate(messages)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
