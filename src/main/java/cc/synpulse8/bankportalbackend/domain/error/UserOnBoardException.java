package cc.synpulse8.bankportalbackend.domain.error;

import cc.synpulse8.bankportalbackend.infrastructure.handler.GlobalErrorLevel;
import cc.synpulse8.bankportalbackend.infrastructure.handler.GlobalServiceException;
import cc.synpulse8.bankportalbackend.infrastructure.handler.IErrorType;

public class UserOnBoardException extends GlobalServiceException {

    public enum UserServiceErrorType implements IErrorType {

        USER_NOT_FOUND("U10000", GlobalErrorLevel.LOW),

        USER_NO_ANY_PERMISSION("U10001", GlobalErrorLevel.LOW);

        private String userServiceErrorCode;

        private GlobalErrorLevel userServiceErrorLevel;

        UserServiceErrorType(String customErrorCode, GlobalErrorLevel customErrorLevel){
            this.userServiceErrorCode = customErrorCode;
            this.userServiceErrorLevel = customErrorLevel;
        }

        @Override
        public String getErrorCode() {
            return userServiceErrorCode;
        }

        @Override
        public GlobalErrorLevel getErrorLevel() {
            return userServiceErrorLevel;
        }
    }


    public UserOnBoardException(IErrorType errorType, String aExceptionMsg) {
        super(errorType, aExceptionMsg);
    }

}
