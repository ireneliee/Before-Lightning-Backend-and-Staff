package util.exception;



// Newly added in v4.2

public class ProductTypeEntityNotFoundException extends Exception
{
    public ProductTypeEntityNotFoundException() 
    {
    }

    
    
    public ProductTypeEntityNotFoundException(String msg) 
    {
        super(msg);
    }
}
