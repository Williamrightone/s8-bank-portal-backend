package cc.synpulse8.bankprotalbackend.infrastructure.handler;

public class GlobalServiceException extends RuntimeException {

    public enum GlobalServiceErrorType implements IErrorType{

        INPUT_FORMAT_ERROR("00001", GlobalErrorLevel.LOW),

        UNAUTHORIZED_REQUEST_ERROR("00002", GlobalErrorLevel.HIGH),

        TOKEN_PRASE_ERROR("00003", GlobalErrorLevel.HIGH),

        TOKEN_EXPIRED_ERROR("00004", GlobalErrorLevel.LOW),

        REFRESH_TOKEN_EXPIRED_ERROR("00005", GlobalErrorLevel.LOW),

        TOKEN_MISMATCH_ERROR("00006", GlobalErrorLevel.HIGH),

        TOKEN_EMPTY_ERROR("00007", GlobalErrorLevel.HIGH),

        ;


        private String customErrorCode;

        private GlobalErrorLevel customErrorLevel;

        GlobalServiceErrorType(String customErrorCode, GlobalErrorLevel customErrorLevel){
            this.customErrorCode = customErrorCode;
            this.customErrorLevel = customErrorLevel;
        }

        @Override
        public String getErrorCode() {
            return customErrorCode;
        }

        @Override
        public GlobalErrorLevel getErrorLevel() {
            return customErrorLevel;
        }
    }


    protected IErrorType errorType;


    protected String displayErrorMessage;


    public GlobalServiceException(IErrorType errorType,String aExceptionMsg) {
        super(aExceptionMsg);
        this.errorType = errorType;
        this.displayErrorMessage = aExceptionMsg;
    }

    public IErrorType getErrorType() {
        return errorType;
    }

    public String getDisplayErrorMessage(){
        return displayErrorMessage;
    }

}
