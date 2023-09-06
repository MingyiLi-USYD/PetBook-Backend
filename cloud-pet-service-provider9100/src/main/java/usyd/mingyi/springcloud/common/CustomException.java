package usyd.mingyi.springcloud.common;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
