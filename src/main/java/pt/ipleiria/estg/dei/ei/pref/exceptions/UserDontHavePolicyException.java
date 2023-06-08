package pt.ipleiria.estg.dei.ei.pref.exceptions;

public class UserDontHavePolicyException extends Exception {

    public UserDontHavePolicyException() {
        super(
                "User can just create occurrences for his policies"
        );
    }

}
