package jsf.converter;

import entity.AccessoryEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "accessoryEntityConverter", forClass = AccessoryEntity.class)

public class AccessoryEntityConverter implements Converter 
{
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.length() == 0 || value.equals("null")) 
        {
            return null;
        }

        try
        {            
            Long objLong = Long.parseLong(value);
            
            List<AccessoryEntity> accessoryEntities = (List<AccessoryEntity>)context.getExternalContext().getSessionMap().get("AccessoryEntityConverter_accessoryEntities");
            
            for(AccessoryEntity accessoryEntity:accessoryEntities)
            {
                if(accessoryEntity.getAccessoryEntityId().equals(objLong))
                {
                    return accessoryEntity;
                }
            }
        }
        catch(Exception ex)
        {
            throw new IllegalArgumentException("Please select a valid value");
        }
        
        return null;
    }

    
    
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null) 
        {
            return "";
        }
        
        if (value instanceof String)
        {
            return "";
        }

        
        
        if (value instanceof AccessoryEntity)
        {            
            AccessoryEntity accessoryEntity = (AccessoryEntity) value;                        
            
            try
            {
                return accessoryEntity.getAccessoryEntityId().toString();
            }
            catch(Exception ex)
            {
                throw new IllegalArgumentException("Invalid value");
            }
        }
        else 
        {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
