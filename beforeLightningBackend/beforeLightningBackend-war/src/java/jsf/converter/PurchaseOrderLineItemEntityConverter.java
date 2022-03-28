package jsf.converter;

import entity.PurchaseOrderLineItemEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = PurchaseOrderLineItemEntity.class)

public class PurchaseOrderLineItemEntityConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("GETTING OBJECT UWU");
        if (value == null || value.length() == 0 || value.equals("null")) {
            return null;
        }

        try {
            Long objLong = Long.parseLong(value);

            List<PurchaseOrderLineItemEntity> purchaseOrderLineItemEntities = (List<PurchaseOrderLineItemEntity>) context.getExternalContext().getSessionMap().get("PurchaseOrderLineItemEntityConverter_purchaseOrderLineItemEntities");

            for (PurchaseOrderLineItemEntity purchaseOrderLineItemEntity : purchaseOrderLineItemEntities) {
                System.out.println("im inside the for loop");

                if (purchaseOrderLineItemEntity.getPurchaseOrderLineItemEntityId().equals(objLong)) {
                    System.out.println("========im returning the purchase order line item");

                    return purchaseOrderLineItemEntity;
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

        if (value instanceof PurchaseOrderLineItemEntity) {
            PurchaseOrderLineItemEntity purchaseOrderLineItemEntity = (PurchaseOrderLineItemEntity) value;

            try {
                return purchaseOrderLineItemEntity.getPurchaseOrderLineItemEntityId().toString();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid value");
            }
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
