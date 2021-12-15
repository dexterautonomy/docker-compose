package com.hingebridge.devops.exception;

public class EmployeeException extends RuntimeException{
	private static final long serialVersionUID = 949878109853265734L;
	private static final String MESSAGE = "Operation failed";

	public EmployeeException()
    {
        super(MESSAGE);
    }

    public EmployeeException(Throwable cause)
    {
        super(MESSAGE, cause);
    }

    public EmployeeException(String message)
    {
        super(message);
    }

    public EmployeeException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
