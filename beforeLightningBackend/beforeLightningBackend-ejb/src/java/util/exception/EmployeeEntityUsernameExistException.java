package util.exception;



public class EmployeeEntityUsernameExistException extends Exception
{
    public EmployeeEntityUsernameExistException()
    {
    }
    
    
    
    public EmployeeEntityUsernameExistException(String msg)
    {
        super(msg);
    }
}