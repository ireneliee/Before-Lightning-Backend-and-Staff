package jsf.converter;

import entity.PurchaseOrderEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = PurchaseOrderEntity.class)

public class PurchaseOrderEntityConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("GETTING OBJECT UWU");
        if (value == null || value.length() == 0 || value.equals("null")) {
            return null;
        }

        try {
            Long objLong = Long.parseLong(value);

            List<PurchaseOrderEntity> purchaseOrderEntities = (List<PurchaseOrderEntity>) context.getExternalContext().getSessionMap().get("PurchaseOrderEntityConverter_purchaseOrderEntities");

            for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
                System.out.println("im inside the for loop");

                if (purchaseOrderEntity.getPurchaseOrderEntityId().equals(objLong)) {
                    System.out.println("========im returning the purchase order");

                    return purchaseOrderEntity;
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Please select a valid value");
        }

        return null;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return "";
        }

        if (value instanceof PurchaseOrderEntity) {
            PurchaseOrderEntity purchaseOrderEntity = (PurchaseOrderEntity) value;

            try {
                return purchaseOrderEntity.getPurchaseOrderEntityId().toString();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid value");
            }
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
