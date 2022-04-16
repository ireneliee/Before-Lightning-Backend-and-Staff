/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author irene
 */
@Stateless
public class TimerSessionBean implements TimerSessionBeanLocal {

    @EJB
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBean;

    @EJB
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean;

    public TimerSessionBean() {

    }

    @Schedule(hour = "*/6")
    public void productReorderQuantityCheckTimer() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        List<PartChoiceEntity> listOfAllPartChoiceEntities = partChoiceEntitySessionBean.retrieveAllPartChoiceEntities();
        for (PartChoiceEntity p : listOfAllPartChoiceEntities) {
            if (p.getQuantityOnHand().compareTo(p.getReorderQuantity()) <= 0) {
                System.out.println("********************Quantity of part choice entity " + p.getPartChoiceEntityId() + " : " + p.getPartChoiceName() + " is below reorder quantity*************************************");
            }
        }

    }

    @Schedule(hour = "*/6")
    public void accessoryReorderQuantityCheckTimer() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        List<AccessoryItemEntity> listOfAllAccessories = accessoryItemEntitySessionBean.retrieveAllAccessoryItemEntities();
        for (AccessoryItemEntity a : listOfAllAccessories) {
            if (a.getQuantityOnHand().compareTo(a.getReorderQuantity()) <= 0) {
                System.out.println("********************Quantity of accessory entity " + a.getAccessoryItemEntityId() + " : " + a.getAccessoryItemName() + " is below reorder quantity*************************************");
            }
        }
    }

    public AccessoryItemEntitySessionBeanLocal getAccessoryItemEntitySessionBean() {
        return accessoryItemEntitySessionBean;
    }

    public void setAccessoryItemEntitySessionBean(AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBean) {
        this.accessoryItemEntitySessionBean = accessoryItemEntitySessionBean;
    }

    public PartChoiceEntitySessionBeanLocal getPartChoiceEntitySessionBean() {
        return partChoiceEntitySessionBean;
    }

    public void setPartChoiceEntitySessionBean(PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean) {
        this.partChoiceEntitySessionBean = partChoiceEntitySessionBean;
    }
}
