package in.main.Product.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<Map<String,Object>> ResourceAlreadyExists(ResourceAlreadyExists e){
        Map<String,Object> map = Map.of("error","Conflict" ,"message",e.getMessage());
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String,Object>> ResourceNotFound(ResourceNotFound e){
        Map<String,Object> map = Map.of("error","Not Found" ,"message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> Exception(Exception e){
        Map<String,Object> map = Map.of("error","Internal Server Error" ,"message",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> IllegalArgumentException(IllegalArgumentException e){
        Map<String,Object> map = Map.of("error","Bad Request" ,"message",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String,Object>> UnauthorizedException(UnauthorizedException e){
        Map<String,Object> map = Map.of("error","Unauthorized" ,"message",e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }
}
