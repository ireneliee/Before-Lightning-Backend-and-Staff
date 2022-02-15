package util.exception;



public class ProductSkuNotFoundException extends Exception
{
    public ProductSkuNotFoundException()
    {
    }
    
    
    
    public ProductSkuNotFoundException(String msg)
    {
        super(msg);
    }
}