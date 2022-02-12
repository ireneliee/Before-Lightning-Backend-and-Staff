package util.exception;



public class MemberEntityUsernameExistException extends Exception
{
    public MemberEntityUsernameExistException()
    {
    }
    
    
    
    public MemberEntityUsernameExistException(String msg)
    {
        super(msg);
    }
}