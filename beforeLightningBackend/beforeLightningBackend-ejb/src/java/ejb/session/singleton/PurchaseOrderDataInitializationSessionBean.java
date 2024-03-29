/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.DeliverySlotSessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import entity.AddressEntity;
import entity.CreditCardEntity;
import entity.DeliverySlotEntity;
import entity.EmployeeEntity;
import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import entity.PurchaseOrderEntity;
import entity.PurchaseOrderLineItemEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.DeliveryStatusEnum;
import util.enumeration.EmployeeAccessRightEnum;
import util.enumeration.PurchaseOrderStatusEnum;
import util.enumeration.PurchaseOrderLineItemTypeEnum;
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.AccessoryNameExistsException;
import util.exception.AddressEntityNotFoundException;
import util.exception.CreateNewProductEntityException;
import util.exception.CreateNewPurchaseOrderException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;
import util.exception.MemberEntityUsernameExistException;
import util.exception.PartChoiceEntityExistException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityExistException;
import util.exception.PartEntityNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.PurchaseOrderEntityNotFoundException;
import util.exception.UnableToAddPartChoiceToPartChoiceException;
import util.exception.UnableToAddPartChoiceToPartException;
import util.exception.UnableToAddPartToProductException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Startup
@Singleton
@LocalBean
@DependsOn("ProductAccessoryDataInitializationSessionBean")
public class PurchaseOrderDataInitializationSessionBean {

    @EJB
    private DeliverySlotSessionBeanLocal deliverySlotSessionBean;

    @EJB
    private PurchaseOrderEntitySessionBeanLocal purchaseOrderEntitySessionBeanLocal;

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB
    private ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal;

    @EJB
    private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBeanLocal;

    @EJB
    private PartEntitySessionBeanLocal partEntitySessionBeanLocal;

    @EJB
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB
    private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBeanLocal;

    @EJB
    private AccessoryEntitySessionBeanLocal accessoryEntitySessionBeanLocal;

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    public PurchaseOrderDataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        if (purchaseOrderEntitySessionBeanLocal.retrieveAllPurchaseOrders().size() == 0) {
            System.out.println("done");
            initializeData();
        }
    }

    private void initializeData() {
        try {
            //Product
            ProductEntity product1 = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(1l);
            ProductEntity product2 = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(2l);

            //Part Choice
            PartChoiceEntity a1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Intel® Core™ i5-12500H Processor (12 Cores)");
            PartChoiceEntity a2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Intel® Core™ i7-12700H Processor (14 Cores)");

            PartChoiceEntity b1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("NVIDIA GEFORCE RTX 3050 4GB");
            PartChoiceEntity b2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("NVIDIA GEFORCE RTX 3050Ti 4GB");

            PartChoiceEntity c1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("8GB (8GB x 1) DDR4 3200MHZ");
            PartChoiceEntity c2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("16GB (8GB x 2) DDR4 3200MHZ");
            PartChoiceEntity c3 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("32GB (16GB x 2) DDR4 3200MHZ (Dual Channel)");

            PartChoiceEntity d1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Stock Thermal Compound");
            PartChoiceEntity d2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Premium Enthusiast Grade Thermal Compound");

            PartChoiceEntity e1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("No operating system");
            PartChoiceEntity e2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Windows 10 Home 64 Bit");
            PartChoiceEntity e3 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Windows 10 Professional 64 Bit");
            PartChoiceEntity e4 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Windows 11 Home 64 Bit");
            PartChoiceEntity e5 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Windows 11 Professional 64 Bit");

            //Accessory
            AccessoryEntity ac1 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityByAccessoryName("Mouse");
            AccessoryEntity ac2 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityByAccessoryName("Speaker");

            //Accessory Item
            AccessoryItemEntity mouse1 = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemBySkuCode("ACSY001");
            AccessoryItemEntity mouse2 = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemBySkuCode("ACSY002");
            AccessoryItemEntity mouse3 = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemBySkuCode("ACSY003");

            AccessoryItemEntity speaker1 = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemBySkuCode("ACSY004");
            AccessoryItemEntity speaker2 = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemBySkuCode("ACSY005");
            AccessoryItemEntity speaker3 = accessoryItemEntitySessionBeanLocal.retrieveAccessoryItemBySkuCode("ACSY006");

            PartChoiceEntity chassis1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Beast 15S Chassis");
            PartChoiceEntity chassis2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Ghost 17X Chassis");

            try {

                AccessoryEntity ass1 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityById(ac1.getAccessoryEntityId());
                AccessoryEntity ass2 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityById(ac2.getAccessoryEntityId());

                MemberEntity m1 = memberEntitySessionBeanLocal.retrieveMemberEntityByMemberEntityId(1l);

                try {
                    PurchaseOrderLineItemEntity l1 = new PurchaseOrderLineItemEntity(23532515, 2, PurchaseOrderLineItemTypeEnum.ACCESSORY, mouse1);
                    PurchaseOrderLineItemEntity l2 = new PurchaseOrderLineItemEntity(23532434, 1, PurchaseOrderLineItemTypeEnum.ACCESSORY, mouse2);
                    PurchaseOrderLineItemEntity l3 = new PurchaseOrderLineItemEntity(23523423, 1, PurchaseOrderLineItemTypeEnum.ACCESSORY, mouse3);
                    PurchaseOrderLineItemEntity l4 = new PurchaseOrderLineItemEntity(32432535, 1, PurchaseOrderLineItemTypeEnum.BUILD, product2);
                    List<PartChoiceEntity> list1 = new ArrayList<>();
                    list1.add(partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName(product2.getProductName() + " Chassis"));
                    list1.add(a2);
                    list1.add(b2);
                    list1.add(c2);
                    list1.add(d2);
                    list1.add(e2);

                    l4.setPartChoiceEntities(list1);

                    PurchaseOrderEntity po = new PurchaseOrderEntity("12341234", new BigDecimal("1150"), LocalDateTime.now(), PurchaseOrderStatusEnum.IN_PROGRESS);
                    po.getPurchaseOrderLineItems().add(l1);
                    po.getPurchaseOrderLineItems().add(l2);
                    po.getPurchaseOrderLineItems().add(l3);
                    po.getPurchaseOrderLineItems().add(l4);

                    PurchaseOrderEntity po1 = new PurchaseOrderEntity("12341235", new BigDecimal("2150"), LocalDateTime.now(), PurchaseOrderStatusEnum.READY_FOR_SHIPMENT);
                    PurchaseOrderEntity po3 = new PurchaseOrderEntity("12341237", new BigDecimal("1700"), LocalDateTime.now(), PurchaseOrderStatusEnum.REFUNDED);
                    purchaseOrderEntitySessionBeanLocal.createNewPurchaseOrder(m1.getUserEntityId(), po);
                    purchaseOrderEntitySessionBeanLocal.createNewPurchaseOrder(m1.getUserEntityId(), po1);
                    purchaseOrderEntitySessionBeanLocal.createNewPurchaseOrder(m1.getUserEntityId(), po3);

                    try {
                        DeliverySlotEntity ds1 = new DeliverySlotEntity(DeliveryStatusEnum.INSTORE, LocalDateTime.of(2022, Month.MARCH, 12, 13,0));
                        DeliverySlotEntity ds2 = new DeliverySlotEntity(DeliveryStatusEnum.OUTSTORE, LocalDateTime.of(2022, Month.MARCH, 12, 16,0));

                        deliverySlotSessionBean.createInStoreDelivery(po1.getPurchaseOrderEntityId(), ds1);
                        deliverySlotSessionBean.createOutStoreDelivery(po.getPurchaseOrderEntityId(), 1l, ds2);

                        CreditCardEntity card1 = new CreditCardEntity(1234567891234567l, "srini", "03/23");
                        CreditCardEntity card2 = new CreditCardEntity(1234567831234567l, "wenjie", "05/23");

                        memberEntitySessionBeanLocal.createNewCreditCardEntity(m1.getUserEntityId(), card1);
                        memberEntitySessionBeanLocal.createNewCreditCardEntity(m1.getUserEntityId(), card2);

                    } catch (PurchaseOrderEntityNotFoundException ex) {
                        Logger.getLogger(PurchaseOrderDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (AddressEntityNotFoundException ex) {
                        Logger.getLogger(PurchaseOrderDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InputDataValidationException ex) {
                        Logger.getLogger(PurchaseOrderDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnknownPersistenceException ex) {
                        Logger.getLogger(PurchaseOrderDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (CreateNewPurchaseOrderException ex) {
                    System.out.println("THIS IS THE ERROR");
                    System.out.println(ex.getMessage());
                }

            } catch (PartChoiceEntityNotFoundException | AccessoryEntityNotFoundException | MemberEntityNotFoundException ex) {
                System.out.println("THIS IS THE ERROR");
                System.out.println(ex.getMessage());
            }

        } catch (PartChoiceEntityNotFoundException ex) {
            System.out.println("THIS IS THE ERROR");
            System.out.println(ex.getMessage());
        } catch (AccessoryEntityNotFoundException | AccessoryItemEntityNotFoundException | ProductEntityNotFoundException ex) {
            Logger.getLogger(PurchaseOrderDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void persist(Object object) {
        em.persist(object);
    }
}
