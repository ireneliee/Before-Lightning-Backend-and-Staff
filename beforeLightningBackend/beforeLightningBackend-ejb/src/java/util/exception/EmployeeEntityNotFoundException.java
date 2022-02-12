package util.exception;



public class EmployeeEntityNotFoundException extends Exception
{
    public EmployeeEntityNotFoundException()
    {
    }
    
    
    
    public EmployeeEntityNotFoundException(String msg)
    {
        super(msg);
    }
}