package sk.danko.publications.service.api.error;

public enum ApiErrorCode implements ErrorCode {
    BAD_REQUEST(ErrorCodeType.BAD_REQUEST, "Invalid request parameters."),
    NOT_FOUND(ErrorCodeType.NOT_FOUND, "Contact with id '%s' was not found."),
    UNAUTHORIZED(ErrorCodeType.UNAUTHORIZED, "Access denied!."),
    INTERNAL_SERVER_ERROR(ErrorCodeType.INTERNAL_SERVER_ERROR, "Internal server error.");

    private final ErrorCodeType type;
    private final String template;
    ApiErrorCode(ErrorCodeType type, String template) {
        this.type = type;
        this.template = template;
    }
    @Override
    public String template() {
        return this.template;
    }
    @Override
    public ErrorCodeType type() {
        return this.type;
    }
}


