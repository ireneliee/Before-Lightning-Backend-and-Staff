/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
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
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.AccessoryNameExistsException;
import util.exception.CreateNewPartEntityException;
import util.exception.CreateNewProductEntityException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceEntityExistException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.PartEntityExistException;
import util.exception.PartEntityNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnableToAddPartChoiceToPartChoiceException;
import util.exception.UnableToAddPartChoiceToPartException;
import util.exception.UnableToAddPartToProductException;
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

        //Product
        ProductEntity p1 = new ProductEntity("Forge 15S", "PROD001", 5.0, "Ultra-slim performance", "Delivers great performance at an unbeatable pricepoint. Featuring the latest and greatest in next gen mobile hardware.");
        ProductEntity p2 = new ProductEntity("Vapor 17X", "PROD002", 5.0, "Ultra powerful, ultra portable", "Ultra Long Battery Life | Ultraslim | RTX 30 Series");
        //Part
        PartEntity a = new PartEntity("Central Processing Unit", "Responsible for carrying out the instructions of computer programs. .");
        PartEntity b = new PartEntity("Graphic Cards", "Single most important piece of hardware for a gaming machine.");
        PartEntity c = new PartEntity("Memory (Notebook RAM)", "Random Access Memory (RAM) is the fast access data storage in which the computer stores files.");
        PartEntity d = new PartEntity("Thermal Compound", "Thermal Compound is a fluid substance which increases the thermal conductivity.");
        PartEntity e = new PartEntity("Operating System", "The Operating System (OS) is the core software that manages the computer hardware resources.");
        //Part Choice
//      PartChoiceEntity(String partChoiceName, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String partOverview, String partDescription) {

        PartChoiceEntity a1 = new PartChoiceEntity("Intel® Core™ i5-12500H Processor (12 Cores)", 100, 10, "Intel", new BigDecimal(0), "Base CPU", "Description");
        PartChoiceEntity a2 = new PartChoiceEntity("Intel® Core™ i7-12700H Processor (14 Cores)", 100, 10, "Intel", new BigDecimal(165), "Recommended CPU", "Description");

        PartChoiceEntity b1 = new PartChoiceEntity("NVIDIA GEFORCE RTX 3050 4GB", 100, 10, "NVIDIA", new BigDecimal(0), "Base graphic card", "Description");
        PartChoiceEntity b2 = new PartChoiceEntity("NVIDIA GEFORCE RTX 3050Ti 4GB", 100, 10, "NVIDIA", new BigDecimal(75), "Recommended graphic card", "Description");

        PartChoiceEntity c1 = new PartChoiceEntity("8GB (8GB x 1) DDR4 3200MHZ", 100, 10, "Memo", new BigDecimal(0), "Base memory", "Description");
        PartChoiceEntity c2 = new PartChoiceEntity("16GB (8GB x 2) DDR4 3200MHZ", 100, 10, "Memo", new BigDecimal(60), "Dual channel", "Description");
        PartChoiceEntity c3 = new PartChoiceEntity("32GB (16GB x 2) DDR4 3200MHZ (Dual Channel)", 100, 10, "Memo", new BigDecimal(280), "Dual channel", "Description");

        PartChoiceEntity d1 = new PartChoiceEntity("Stock Thermal Compound", 100, 10, "Kryonaut", new BigDecimal(0), "Basic thermal compound", "Description");
        PartChoiceEntity d2 = new PartChoiceEntity("Premium Enthusiast Grade Thermal Compound", 100, 10, "Kryonaut", new BigDecimal(35), "Recommended thermal compound", "Description");

        PartChoiceEntity e1 = new PartChoiceEntity("No operating system", 100, 0, "NIL", new BigDecimal(0), "Base operating system", "Description");
        PartChoiceEntity e2 = new PartChoiceEntity("Windows 10 Home 64 Bit", 100, 10, "Windows", new BigDecimal(134), "Recommended operating system", "Description");
        PartChoiceEntity e3 = new PartChoiceEntity("Windows 10 Professional 64 Bit", 100, 10, "Windows", new BigDecimal(202), "Advanced operating system", "Description");
        PartChoiceEntity e4 = new PartChoiceEntity("Windows 11 Home 64 Bit", 100, 10, "Windows", new BigDecimal(130), "Basic operating system", "Description");
        PartChoiceEntity e5 = new PartChoiceEntity("Windows 11 Professional 64 Bit", 100, 10, "Windows", new BigDecimal(202), "Newest operating system", "Description");

        //Accessory
        AccessoryEntity ac1 = new AccessoryEntity("Mouse", "Accessory Description");
        AccessoryEntity ac2 = new AccessoryEntity("Speaker", "Accessory Description");

        //Accessory Item
        //AccessoryItemEntity(String accessoryItemName, String skuCode, Integer quantityOnHand, Integer reorderQuantity, String brand, BigDecimal price, String description)
        AccessoryItemEntity mouse1 = new AccessoryItemEntity("LOGITECH PRO 910-005940 HIGHLIGHTS", "ACSY001", 100, 10, "Logitech", new BigDecimal(150), "This is a mouse");
        AccessoryItemEntity mouse2 = new AccessoryItemEntity("LOGITECH G305 HIGHLIGHTS", "ACSY002", 100, 10, "Logitech", new BigDecimal(50), "This is a mouse");
        AccessoryItemEntity mouse3 = new AccessoryItemEntity("CORSAIR SABRE RGB HIGHLIGHTS", "ACSY003", 100, 10, "Corsair", new BigDecimal(50), "This is a mouse");

        AccessoryItemEntity speaker1 = new AccessoryItemEntity("KEF LS50 Meta", "ACSY004", 100, 10, "KEF", new BigDecimal(150), "This is a speaker");
        AccessoryItemEntity speaker2 = new AccessoryItemEntity("Wharfedale Diamond 12.3", "ACSY005", 100, 10, "Wharfedale", new BigDecimal(150), "This is a speaker");
        AccessoryItemEntity speaker3 = new AccessoryItemEntity("Elac Debut B5.2", "ACSY006", 100, 10, "Elac", new BigDecimal(150), "This is a speaker");

        try {
            productEntitySessionBeanLocal.createBrandNewProductEntity(p1, 10, 5, "Aftershock", new BigDecimal(500), "Overview", "Description");
            productEntitySessionBeanLocal.createBrandNewProductEntity(p2, 10, 5, "Aftershock", new BigDecimal(600), "Overview", "Description");

            partEntitySessionBeanLocal.createNewPartEntity(a);
            partEntitySessionBeanLocal.createNewPartEntity(b);
            partEntitySessionBeanLocal.createNewPartEntity(c);
            partEntitySessionBeanLocal.createNewPartEntity(d);
            partEntitySessionBeanLocal.createNewPartEntity(e);

            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(a1);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(a2);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(b1);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(b2);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(c1);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(c2);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(c3);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(d1);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(d2);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(e1);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(e2);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(e3);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(e4);
            partChoiceEntitySessionBeanLocal.createNewPartChoiceEntity(e5);

            ProductEntity product1 = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(p1.getProductEntityId());
            ProductEntity product2 = productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(p2.getProductEntityId());

            PartEntity part1 = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(a.getPartEntityId());
            PartEntity part2 = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(b.getPartEntityId());
            PartEntity part3 = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(c.getPartEntityId());
            PartEntity part4 = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(d.getPartEntityId());
            PartEntity part5 = partEntitySessionBeanLocal.retrievePartEntityByPartEntityId(e.getPartEntityId());

            PartChoiceEntity partChoiceA1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(a1.getPartChoiceEntityId());
            PartChoiceEntity partChoiceA2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(a2.getPartChoiceEntityId());
            PartChoiceEntity partChoiceB1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(b1.getPartChoiceEntityId());
            PartChoiceEntity partChoiceB2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(b2.getPartChoiceEntityId());
            PartChoiceEntity partChoiceC1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(c1.getPartChoiceEntityId());
            PartChoiceEntity partChoiceC2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(c2.getPartChoiceEntityId());
            PartChoiceEntity partChoiceC3 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(c3.getPartChoiceEntityId());
            PartChoiceEntity partChoiceD1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(d1.getPartChoiceEntityId());
            PartChoiceEntity partChoiceD2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(d2.getPartChoiceEntityId());
            PartChoiceEntity partChoiceE1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(e1.getPartChoiceEntityId());
            PartChoiceEntity partChoiceE2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(e2.getPartChoiceEntityId());
            PartChoiceEntity partChoiceE3 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(e3.getPartChoiceEntityId());
            PartChoiceEntity partChoiceE4 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(e4.getPartChoiceEntityId());
            PartChoiceEntity partChoiceE5 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceEntityId(e5.getPartChoiceEntityId());

            //Link PartChoice to Part
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceA1.getPartChoiceEntityId(), part1.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceA2.getPartChoiceEntityId(), part1.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceB1.getPartChoiceEntityId(), part2.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceB2.getPartChoiceEntityId(), part2.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceC1.getPartChoiceEntityId(), part3.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceC2.getPartChoiceEntityId(), part3.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceC3.getPartChoiceEntityId(), part3.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceD1.getPartChoiceEntityId(), part4.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceD2.getPartChoiceEntityId(), part4.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceE1.getPartChoiceEntityId(), part5.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceE2.getPartChoiceEntityId(), part5.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceE3.getPartChoiceEntityId(), part5.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceE4.getPartChoiceEntityId(), part5.getPartEntityId());
            partEntitySessionBeanLocal.addPartChoiceToPart(partChoiceE5.getPartChoiceEntityId(), part5.getPartEntityId());

            //Link Part To Product
            productEntitySessionBeanLocal.addPartToProduct(part1.getPartEntityId(), product1.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part2.getPartEntityId(), product1.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part3.getPartEntityId(), product1.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part4.getPartEntityId(), product1.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part5.getPartEntityId(), product1.getProductEntityId());

            productEntitySessionBeanLocal.addPartToProduct(part1.getPartEntityId(), product2.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part2.getPartEntityId(), product2.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part3.getPartEntityId(), product2.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part4.getPartEntityId(), product2.getProductEntityId());
            productEntitySessionBeanLocal.addPartToProduct(part5.getPartEntityId(), product2.getProductEntityId());

            //Compatibility Check
            PartChoiceEntity chassis1 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Forge 15S Chassis");
            PartChoiceEntity chassis2 = partChoiceEntitySessionBeanLocal.retrievePartChoiceEntityByPartChoiceName("Vapor 17X Chassis");

            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceA1.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceB1.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceC1.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceC2.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceD1.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceE1.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceE2.getPartChoiceEntityId(), chassis1.getPartChoiceEntityId());

            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceA2.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceB2.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceC2.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceC3.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceD2.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceE3.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceE4.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());
            partChoiceEntitySessionBeanLocal.addPartChoiceToChassisChoice(partChoiceE5.getPartChoiceEntityId(), chassis2.getPartChoiceEntityId());

            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(ac1);
            accessoryEntitySessionBeanLocal.createNewAccessoryEntity(ac2);

            AccessoryEntity ass1 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityById(ac1.getAccessoryEntityId());
            AccessoryEntity ass2 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityById(ac1.getAccessoryEntityId());

//            mouse1.setAccessoryEntity(ac1);
//            mouse2.setAccessoryEntity(ac1);
//            mouse3.setAccessoryEntity(ac1);
//            ac1.getAccessoryItemEntities().add(mouse1);
//            ac1.getAccessoryItemEntities().add(mouse2);
//            ac1.getAccessoryItemEntities().add(mouse3);
//
//            speaker1.setAccessoryEntity(ac2);
//            speaker2.setAccessoryEntity(ac2);
//            speaker3.setAccessoryEntity(ac2);
//
//            ac2.getAccessoryItemEntities().add(speaker1);
//            ac2.getAccessoryItemEntities().add(speaker2);
//            ac2.getAccessoryItemEntities().add(speaker3);

            accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse1, ac1);
            accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse2, ac1);
            accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse3, ac1);
            accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker1, ac2);
            accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker2, ac2);
            accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker3, ac2);

        } catch (CreateNewProductEntityException | InputDataValidationException | UnknownPersistenceException | ProductSkuCodeExistException | PartEntityExistException | PartChoiceEntityExistException | PartEntityNotFoundException | PartChoiceEntityNotFoundException | UnableToAddPartChoiceToPartException | ProductEntityNotFoundException | UnableToAddPartToProductException | UnableToAddPartChoiceToPartChoiceException ex) {
            System.out.println("THIS IS THE ERROR");
            System.out.println(ex.getMessage());
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessoryNameExistsException ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessoryItemNameExists ex) {
            Logger.getLogger(DataInitialisationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessoryEntityNotFoundException ex) {
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
