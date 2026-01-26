package sk.danko.publications.service.api.error;

public enum ErrorCodeType {

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);
    private int numCode;
    ErrorCodeType(int numCode) {
        this.numCode = numCode;
    }
    public int getNumCode() {
        return numCode;
    }
}
