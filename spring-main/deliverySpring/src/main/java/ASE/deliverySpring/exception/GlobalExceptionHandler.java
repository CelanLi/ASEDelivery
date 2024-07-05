package ASE.deliverySpring.exception;

import ASE.deliverySpring.base.BaseMessage;
import ASE.deliverySpring.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //handle exceptions of all classes
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("Internal Error");
    }

}
