package zcd.ts4u.kanban.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

//first let me write this code, then I will explain it.
@Service
public class MapValidationErrorService {
    public ResponseEntity<?> mapValidationService(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
        //So, this method MapValidationService takes a BindingResult object which contains information about any validation errors that occurs during form validation. If the BindingResult object contains any error we will create a hashMap object called errorMap which will store the validation errors. this method will iterate through each validation error and will add the field name and error message to the errorMap. Finally, this method will create a new responseEntity object that will contain the errorMap.
        // now that we have finished writing our logics for the application, we need to build controller that will handle HTTP requests from clients calling these business logics.
    }
}
