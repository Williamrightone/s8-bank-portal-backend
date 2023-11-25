package cc.synpulse8.bankprotalbackend.infrastructure.handler;

public interface IErrorType {

    public String getErrorCode();

    public GlobalErrorLevel getErrorLevel();

}
