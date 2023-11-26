package cc.synpulse8.bankportalbackend.infrastructure.handler;

public interface IErrorType {

    public String getErrorCode();

    public GlobalErrorLevel getErrorLevel();

}
