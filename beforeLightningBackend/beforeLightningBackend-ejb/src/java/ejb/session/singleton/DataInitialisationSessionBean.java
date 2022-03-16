/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import entity.EmployeeEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.AccessoryItemNameExists;
import util.exception.AccessoryNameExistsException;
import util.exception.CreateNewPartEntityException;
import util.exception.CreateNewProductEntityException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Singleton
@LocalBean
@Startup
public class DataInitialisationSessionBean {

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

    public DataInitialisationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            employeeEntitySessionBeanLocal.retrieveEmployeeEntityByEmployeeEntityId(1l);
        } catch (EmployeeEntityNotFoundException ex) {
            System.out.println("done");
            initializeData();
        }
    }

    private void initializeData() {
//        
//        Long createBrandNewProductEntity(ProductEntity newProductEntity, Integer quantityOnHand, Integer reorderQuantity, String brand,
//            BigDecimal price, String partOverview, String partDescription) throws CreateNewProductEntityException, InputDataValidationException,
//            UnknownPersistenceException, ProductSkuCodeExistException {

        ProductEntity p1 = new ProductEntity("Forge 15S", "PROD001", 5.0, "Ultra-slim performance", "Delivers great performance at an unbeatable pricepoint. Featuring the latest and greatest in next gen mobile hardware.");
        ProductEntity p2 = new ProductEntity("Vapor 17X", "PROD002", 5.0, "Ultra powerful, ultra portable", "Ultra Long Battery Life | Ultraslim | RTX 30 Series");
        try {
            productEntitySessionBeanLocal.createBrandNewProductEntity(p1, 10, 5, "Aftershock", new BigDecimal(500), "Overview", "Description");
            productEntitySessionBeanLocal.createBrandNewProductEntity(p2, 10, 5, "Aftershock", new BigDecimal(600), "Overview", "Description");

        } catch (CreateNewProductEntityException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProductSkuCodeExistException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

//        // product related
//        PartEntity a = new PartEntity("Central Processing Unit", "Responsible for carrying out the instructions of computer programs. .");
//        PartEntity b = new PartEntity("Graphic Cards", "Single most important piece of hardware for a gaming machine.");
//        PartEntity c = new PartEntity("Memory (Notebook RAM)", "Random Access Memory (RAM) is the fast access data storage in which the computer stores files.");
//        PartEntity d = new PartEntity("Thermal Compound", "Thermal Compound is a fluid substance which increases the thermal conductivity.");
//        PartEntity e = new PartEntity("Operating System", "The Operating System (OS) is the core software that manages the computer hardware resources.");
//
//        PartChoiceEntity a1 = new PartChoiceEntity("Intel® Core™ i5-12500H Processor (12 Cores)", 100, 10, "Intel", new BigDecimal(0), "Base CPU");
//        PartChoiceEntity a2 = new PartChoiceEntity("Intel® Core™ i7-12700H Processor (14 Cores)", 100, 10, "Intel", new BigDecimal(165), "Recommended CPU");
//        a.getPartChoices().add(a1);
//        a.getPartChoices().add(a2);
//
//        PartChoiceEntity b1 = new PartChoiceEntity("NVIDIA GEFORCE RTX 3050 4GB", 100, 10, "NVIDIA", new BigDecimal(0), "Base graphic card");
//        PartChoiceEntity b2 = new PartChoiceEntity("NVIDIA GEFORCE RTX 3050Ti 4GB", 100, 10, "NVIDIA", new BigDecimal(75), "Recommended graphic card");
//        b.getPartChoices().add(b1);
//        b.getPartChoices().add(b2);
//
//        PartChoiceEntity c1 = new PartChoiceEntity("8GB (8GB x 1) DDR4 3200MHZ", 100, 10, "Memo", new BigDecimal(0), "Base memory");
//        PartChoiceEntity c2 = new PartChoiceEntity("16GB (8GB x 2) DDR4 3200MHZ", 100, 10, "Memo", new BigDecimal(60), "Dual channel");
//        PartChoiceEntity c3 = new PartChoiceEntity("32GB (16GB x 2) DDR4 3200MHZ (Dual Channel)", 100, 10, "Memo", new BigDecimal(280), "Dual channel");
//        c.getPartChoices().add(c1);
//        c.getPartChoices().add(c2);
//        c.getPartChoices().add(c3);
//
//        PartChoiceEntity d1 = new PartChoiceEntity("Stock Thermal Compound", 100, 10, "Kryonaut", new BigDecimal(0), "Basic thermal compound");
//        PartChoiceEntity d2 = new PartChoiceEntity("Premium Enthusiast Grade Thermal Compound", 100, 10, "Kryonaut", new BigDecimal(35), "Recommended thermal compound");
//        d.getPartChoices().add(d1);
//        d.getPartChoices().add(d2);
//
//        PartChoiceEntity e1 = new PartChoiceEntity("No operating system", 100, 0, "NIL", new BigDecimal(0), "Base operating system");
//        PartChoiceEntity e2 = new PartChoiceEntity("Windows 10 Home 64 Bit", 100, 10, "Windows", new BigDecimal(134), "Recommended operating system");
//        PartChoiceEntity e3 = new PartChoiceEntity("Windows 10 Professional 64 Bit", 100, 10, "Windows", new BigDecimal(202), "Advanced operating system");
//        PartChoiceEntity e4 = new PartChoiceEntity("Windows 11 Home 64 Bit", 100, 10, "Windows", new BigDecimal(130), "Basic operating system");
//        PartChoiceEntity e5 = new PartChoiceEntity("Windows 11 Professional 64 Bit", 100, 10, "Windows", new BigDecimal(202), "Newest operating system");
//        e.getPartChoices().add(e1);
//        e.getPartChoices().add(e2);
//        e.getPartChoices().add(e3);
//        e.getPartChoices().add(e4);
//
//        try {
//            partEntitySessionBeanLocal.createNewPartEntity(a);
//            partEntitySessionBeanLocal.createNewPartEntity(b);
//            partEntitySessionBeanLocal.createNewPartEntity(c);
//            partEntitySessionBeanLocal.createNewPartEntity(d);
//            partEntitySessionBeanLocal.createNewPartEntity(e);
//        } catch (CreateNewPartEntityException | UnknownPersistenceException | InputDataValidationException ex) {
//            System.out.println("An error has occured while creating new part: " + ex.getMessage());
//        }
//        ProductEntity p1 = new ProductEntity("Laptop", "Forge 15S", "PROD001", "Ultra-slim performance", "Delivers great performance at an unbeatable pricepoint. Featuring the latest and greatest in next gen mobile hardware.", new BigDecimal(1385), 5);;
//        ProductEntity p2 = new ProductEntity("Laptop", "Vapor 17X", "PROD002", "Ultra powerful, ultra portable", "Ultra Long Battery Life | Ultraslim | RTX 30 Series\n", new BigDecimal(2425), 5);
//
//        ProductEntity p3 = new ProductEntity("Laptop", "Apex 15S", "PROD003", "High performance notebook", "Introducing the APEX 15S, a high performance ultra portable 15.6 inch laptop.", new BigDecimal(1755), 5);
//
//        try {
//            productEntitySessionBeanLocal.createNewProduct(p1);
//            productEntitySessionBeanLocal.createNewProduct(p2);
//            productEntitySessionBeanLocal.createNewProduct(p3);
//
//            List<PartEntity> listOfParts = new ArrayList<>();
//            listOfParts.add(a);
//            listOfParts.add(b);
//            listOfParts.add(c);
//            listOfParts.add(d);
//            listOfParts.add(e);
//
//            for (PartEntity p : listOfParts) {
//                p1.getParts().add(p);
//                p2.getParts().add(p);
//                p3.getParts().add(p);
//                p.getProducts().add(p1);
//                p.getProducts().add(p2);
//                p.getProducts().add(p3);
//            }
//        } catch (CreateNewProductEntityException | InputDataValidationException | UnknownPersistenceException | ProductSkuCodeExistException ex) {
//            System.out.println("An error has occured while creating new product: " + ex.getMessage());
//        }
//
//        System.out.println("All product persisted");
//
//        AccessoryEntity ac1 = new AccessoryEntity("Accessory", "Mouse");
//        AccessoryEntity ac2 = new AccessoryEntity("Accessory", "Speaker");
//
//        AccessoryItemEntity mouse1 = new AccessoryItemEntity("LOGITECH PRO 910-005940 HIGHLIGHTS", 100, new BigDecimal(150), ac1);
//        AccessoryItemEntity mouse2 = new AccessoryItemEntity("LOGITECH G305 HIGHLIGHTS", 100, new BigDecimal(50), ac1);
//        AccessoryItemEntity mouse3 = new AccessoryItemEntity("CORSAIR SABRE RGB HIGHLIGHTS", 90, new BigDecimal(50), ac1);
//
//        AccessoryItemEntity speaker1 = new AccessoryItemEntity("KEF LS50 Meta", 90, new BigDecimal(400), ac2);
//        AccessoryItemEntity speaker2 = new AccessoryItemEntity("Wharfedale Diamond 12.3", 90, new BigDecimal(200), ac2);
//        AccessoryItemEntity speaker3 = new AccessoryItemEntity("Elac Debut B5.2", 90, new BigDecimal(400), ac2);
//
//        try {
//            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(ac1);
//            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(ac2);
//
//            try {
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse1);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse2);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse3);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker1);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker2);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker3);
//            } catch (AccessoryItemNameExists ex) {
//                System.out.println("An error has occured while creating the new accessory item: " + ex.getMessage());
//            }
//
//        } catch (AccessoryNameExistsException | UnknownPersistenceException | InputDataValidationException ex) {
//            System.out.println("An error has occured while creating the new accessory: " + ex.getMessage());
//        }
//        ac1.getAccessoryItem().add(mouse1);
//        ac1.getAccessoryItem().add(mouse2);
//        ac1.getAccessoryItem().add(mouse3);
//
//        ac2.getAccessoryItem().add(speaker1);
//        ac2.getAccessoryItem().add(speaker2);
//        ac2.getAccessoryItem().add(speaker3);
//
//        try {
//            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(ac1);
//            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(ac2);
//
//            try {
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse1);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse2);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse3);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker1);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker2);
//                accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker3);
//            } catch (AccessoryItemNameExists ex) {
//                System.out.println("An error has occured while creating the new accessory item: " + ex.getMessage());
//            }
//
//        } catch (AccessoryNameExistsException | UnknownPersistenceException | InputDataValidationException ex) {
//            System.out.println("An error has occured while creating the new accessory: " + ex.getMessage());
//        }
        EmployeeEntity newEmployee1 = new EmployeeEntity(EmployeeAccessRightEnum.ADMIN, "manager", "password", "manager", "one", "manager@gmail.com", "99999999");
        EmployeeEntity newEmployee2 = new EmployeeEntity(EmployeeAccessRightEnum.OPERATION, "operationstaff", "password", "operation", "one", "operation@gmail.com", "99999999");
        EmployeeEntity newEmployee3 = new EmployeeEntity(EmployeeAccessRightEnum.PRODUCT, "productstaff", "password", "product", "one", "product@gmail.com", "99999999");
        EmployeeEntity newEmployee4 = new EmployeeEntity(EmployeeAccessRightEnum.SALES, "salesstaff", "password", "sales", "one", "sales@gmail.com", "99999999");

        try {
            employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee1);
            employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee2);
            employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee3);
            employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee4);

        } catch (EmployeeEntityUsernameExistException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }
}
