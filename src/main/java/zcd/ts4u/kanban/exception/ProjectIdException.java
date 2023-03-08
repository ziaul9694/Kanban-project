package zcd.ts4u.kanban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException{
    // we will build a constructor here
    public ProjectIdException(String message){
        super(message);
    }
    //we also create a custom class for the response of this exception
}
