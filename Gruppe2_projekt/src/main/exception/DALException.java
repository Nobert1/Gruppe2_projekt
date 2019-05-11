package exception;

public class DALException extends Exception {
    private static final long serialVersionUID = 7355418246336739229L;

    public DALException(String msg, Throwable e) {
        super(msg,e);
    }

    public DALException(String msg) {
        super(msg);
    }

}
