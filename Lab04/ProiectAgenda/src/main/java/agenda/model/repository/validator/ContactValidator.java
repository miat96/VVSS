package agenda.model.repository.validator;

import agenda.model.base.Contact;

public class ContactValidator {

    public ContactValidator() {
    }

    public boolean isValid(Contact contact){
        boolean isNameValid = contact.getName().matches("^[a-zA-Z][a-zA-Z ]*[a-zA-Z]$") && contact.getName().length() > 0;
        boolean isValidPhoneNumber = contact.getTelefon().matches("[0-9]*") && contact.getTelefon().length() == 10;

        return isNameValid && isValidPhoneNumber;
    }
}
