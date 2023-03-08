package zcd.ts4u.kanban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zcd.ts4u.kanban.domain.Project;

//to make this class a global exception handler for all the controller of this application we use
@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    //first let me write the method then i will explain this.
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex){
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
        // This method takes the exception object ex as a parameter and returns a response entity object, which represents an HTTP response that can be sent back to the client.
        // this method also creates a new instance of ProjectIdExceptionResponse object and then send the response to the client.
        //let's go back to project service.
    }
}
