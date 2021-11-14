package exceptions;

public class ApiException extends RuntimeException{

    private final int statusCode;

    public ApiException (int statusCode, String msg){
        super(msg); //Caused by
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
