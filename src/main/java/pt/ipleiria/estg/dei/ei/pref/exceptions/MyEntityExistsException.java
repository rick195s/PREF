package pt.ipleiria.estg.dei.ei.pref.exceptions;

import javax.ejb.EJBException;

public class MyEntityExistsException extends EJBException {
    public MyEntityExistsException(String message) {
        super(message);
    }
}
