package util.exception;



// Newly added in v4.2

public class ProductTypeNotFoundException extends Exception
{
    public ProductTypeNotFoundException() 
    {
    }

    
    
    public ProductTypeNotFoundException(String msg) 
    {
        super(msg);
    }
}
