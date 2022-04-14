/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.ReviewEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import entity.ReviewEntity;
import java.math.BigDecimal;
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
import util.exception.AccessoryEntityNotFoundException;
import util.exception.AccessoryItemNameExists;
import util.exception.AccessoryNameExistsException;
import util.exception.CreateNewProductEntityException;
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
@Startup
@Singleton
@LocalBean
@DependsOn("MemberEmployeeDataInitializationSessionBean")
public class ProductAccessoryDataInitializationSessionBean {

	@EJB(name = "ReviewEntitySessionBeanLocal")
	private ReviewEntitySessionBeanLocal reviewEntitySessionBeanLocal;

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

	public ProductAccessoryDataInitializationSessionBean() {
	}

	@PostConstruct
	public void postConstruct() {
		try {
			productEntitySessionBeanLocal.retrieveProductEntityByProductEntityId(1l);
		} catch (ProductEntityNotFoundException ex) {
			System.out.println("done");
			initializeData();
		}
	}

	private void initializeData() {
		System.out.println("============CALLED INIT DATA IN PRODUCT ACCESSORY DATA INIT ===========");
		//Product
		ProductEntity p1 = new ProductEntity("Beast 15S", "PROD001", "Delivers great performance at an unbeatable pricepoint. Featuring the latest and greatest in next gen mobile hardware.", "Ultra-slim performance");
		ProductEntity p2 = new ProductEntity("Ghost 17X", "PROD002", "Ultra Long Battery Life | Ultraslim | RTX 30 Series", "Ultra powerful, ultra portable");
//                p1.setImageLink("beast15S.png");
//                p2.setImageLink("ghost17x.png");

		//Part
		PartEntity a = new PartEntity("Central Processing Unit", "Responsible for carrying out the instructions of computer programs. .");
		PartEntity b = new PartEntity("Graphic Cards", "Single most important piece of hardware for a gaming machine.");
		PartEntity c = new PartEntity("Memory (Notebook RAM)", "Random Access Memory (RAM) is the fast access data storage in which the computer stores files.");
		PartEntity d = new PartEntity("Thermal Compound", "Thermal Compound is a fluid substance which increases the thermal conductivity.");
		PartEntity e = new PartEntity("Operating System", "The Operating System (OS) is the core software that manages the computer hardware resources.");

		//Part Choice
		PartChoiceEntity a1 = new PartChoiceEntity("Intel® Core™ i5-12500H Processor (12 Cores)", 100, 10, "Intel", new BigDecimal(250), "Base CPU", "18M Cache, up to 4.50 GHz. With 4 Performance Cores and 8 Efficient Cores, The new Intel I5 chip is what you need to boost your productivity.");
		PartChoiceEntity a2 = new PartChoiceEntity("Intel® Core™ i7-12700H Processor (14 Cores)", 100, 10, "Intel", new BigDecimal(350), "Recommended CPU", "24M Cache, up to 4.70 GHz. With 6 Performance Cores and 8 Efficient Cores, The new Intel I7 chip is what you need to be the best in the game.");

		PartChoiceEntity b1 = new PartChoiceEntity("NVIDIA GEFORCE RTX 3050 4GB", 100, 10, "NVIDIA", new BigDecimal(300), "Base graphic card", "The GeForce RTX™ 3050 is built with the powerful graphics performance of the NVIDIA Ampere architecture. It offers dedicated 2nd gen RT Cores and 3rd gen Tensor Cores.");
		PartChoiceEntity b2 = new PartChoiceEntity("NVIDIA GEFORCE RTX 3050Ti 4GB", 100, 10, "NVIDIA", new BigDecimal(500), "Recommended graphic card", "The GeForce RTX™ 3050TI is built with the powerful graphics performance of the NVIDIA Ampere architecture. It offers dedicated 2nd gen RT Cores and 3rd gen Tensor Cores.");

		PartChoiceEntity c1 = new PartChoiceEntity("8GB (8GB x 1) DDR4 3200MHZ", 100, 10, "Memo", new BigDecimal(80), "Base memory", "Blaze through your games and productive work with our lightning fast RAM");
		PartChoiceEntity c2 = new PartChoiceEntity("16GB (8GB x 2) DDR4 3200MHZ", 100, 10, "Memo", new BigDecimal(160), "Dual channel", "Be at the peak of the game with our powerful 16GB RAM");
		PartChoiceEntity c3 = new PartChoiceEntity("32GB (16GB x 2) DDR4 3200MHZ (Dual Channel)", 100, 10, "Memo", new BigDecimal(300), "Dual channel", "Beat the competition and be unstoppable with 32GB of our lightning fast RAM");

		PartChoiceEntity d1 = new PartChoiceEntity("Stock Thermal Compound", 100, 10, "Kryonaut", new BigDecimal(0), "Basic thermal compound", "Basic thermal compound provided to ensure that your CPU runs cool at all time");
		PartChoiceEntity d2 = new PartChoiceEntity("Premium Enthusiast Grade Thermal Compound", 100, 10, "Kryonaut", new BigDecimal(35), "Recommended thermal compound", "Advanced thermal compound made for champions. Experience the cool performance of our Kryonaut Thermal Compound");

		PartChoiceEntity e1 = new PartChoiceEntity("No operating system", 100, 0, "NIL", new BigDecimal(0), "Base operating system", "No Operating System provided");
		PartChoiceEntity e2 = new PartChoiceEntity("Windows 10 Home 64 Bit", 100, 10, "Windows", new BigDecimal(134), "Recommended operating system", "Windows 10 Home 64 bit is the basic operating system that any household would require");
		PartChoiceEntity e3 = new PartChoiceEntity("Windows 10 Professional 64 Bit", 100, 10, "Windows", new BigDecimal(202), "Advanced operating system", "Windows 10 Professional 64 bit, for all your professional needs");
		PartChoiceEntity e4 = new PartChoiceEntity("Windows 11 Home 64 Bit", 100, 10, "Windows", new BigDecimal(130), "Basic operating system", "Windows 11 Home 64 bit is the basic operating system that any household would require");
		PartChoiceEntity e5 = new PartChoiceEntity("Windows 11 Professional 64 Bit", 100, 10, "Windows", new BigDecimal(202), "Newest operating system", "Windows 11 Professional 64 bit, for all your professional needs");

		//Accessory
		AccessoryEntity ac1 = new AccessoryEntity("Mouse", "The most versatile laptop peripheral in the world. View our specially curated selection of only the finest gaming mice, with both performance and comfort in mind.");
		AccessoryEntity ac2 = new AccessoryEntity("Speaker", "A special collection of hand-picked brands that will make any audiophille live in the music they play.");

		//Accessory Item
		AccessoryItemEntity mouse1 = new AccessoryItemEntity("LOGITECH PRO 910-005940", "ACSY001", 100, 10, "Logitech", new BigDecimal(150), "Beat the competition with one of the highest performing gaming mouse.");
		AccessoryItemEntity mouse2 = new AccessoryItemEntity("LOGITECH G305", "ACSY002", 100, 10, "Logitech", new BigDecimal(80), "Experience the comfort and performance of a high end gaming mouse");
		AccessoryItemEntity mouse3 = new AccessoryItemEntity("CORSAIR SABRE RGB", "ACSY003", 100, 10, "Corsair", new BigDecimal(120), "Apart from its lightning performance and comfort, match the color of your gaming mouse to any setup");

		AccessoryItemEntity speaker1 = new AccessoryItemEntity("KEF LS50 Meta", "ACSY004", 100, 10, "KEF", new BigDecimal(170), "Best in class HIFI speakers, made specially for audiophilles");
		AccessoryItemEntity speaker2 = new AccessoryItemEntity("Creative Pebble V3", "ACSY005", 100, 10, "Creative", new BigDecimal(130), "HIFI Speakers with added bass for any music lover.");
		AccessoryItemEntity speaker3 = new AccessoryItemEntity("Elac Debut B5.2", "ACSY006", 100, 10, "Elac", new BigDecimal(120), "Experience the amazing sound quality of Elac, made by the best in the industry");

		ReviewEntity review1 = new ReviewEntity("testUser1", 5, "I love this product, it has been a game changer and i will recommend it to anyone!");
		ReviewEntity review2 = new ReviewEntity("testUser2", 5, "Good mouse, will buy again when i need it again next time");
		ReviewEntity review3 = new ReviewEntity("testUser3", 3, "Not really that good, was really hyped but i guess its okay");
		ReviewEntity review4 = new ReviewEntity("testUser4", 5, "I have been missing this from my life for too long... THANKS BEFORELIGHTNING!!!");
		ReviewEntity review5 = new ReviewEntity("testUser5", 4, "Great Product!");
		ReviewEntity review6 = new ReviewEntity("testUser5", 3, "Did not like this Product at all...");

		try {
			productEntitySessionBeanLocal.createBrandNewProductEntity(p1, 10, 5, "BeforeLightning", new BigDecimal(500));
			productEntitySessionBeanLocal.createBrandNewProductEntity(p2, 10, 5, "BeforeLightning", new BigDecimal(600));

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
			AccessoryEntity ass2 = accessoryEntitySessionBeanLocal.retrieveAccessoryEntityById(ac2.getAccessoryEntityId());

			accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse1, ass1.getAccessoryEntityId());
			accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse2, ass1.getAccessoryEntityId());
			accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(mouse3, ass1.getAccessoryEntityId());
			accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker1, ass2.getAccessoryEntityId());
			accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker2, ass2.getAccessoryEntityId());
			accessoryItemEntitySessionBeanLocal.createNewAccessoryItemEntity(speaker3, ass2.getAccessoryEntityId());

			reviewEntitySessionBeanLocal.createNewReviewEntityForAcc(review1, mouse1.getAccessoryItemEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForAcc(review2, mouse1.getAccessoryItemEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForAcc(review3, mouse1.getAccessoryItemEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForAcc(review4, mouse1.getAccessoryItemEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForAcc(review5, mouse1.getAccessoryItemEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForProduct(review1, product1.getProductEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForProduct(review2, product1.getProductEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForProduct(review3, product1.getProductEntityId());
			reviewEntitySessionBeanLocal.createNewReviewEntityForProduct(review6, product2.getProductEntityId());

		} catch (CreateNewProductEntityException | InputDataValidationException | UnknownPersistenceException | ProductSkuCodeExistException | PartEntityExistException | PartChoiceEntityExistException | PartEntityNotFoundException | PartChoiceEntityNotFoundException | UnableToAddPartChoiceToPartException | ProductEntityNotFoundException | UnableToAddPartToProductException | UnableToAddPartChoiceToPartChoiceException | AccessoryNameExistsException | AccessoryItemNameExists | AccessoryEntityNotFoundException ex) {
			System.out.println("THIS IS THE ERROR");
			System.out.println(ex.getMessage());
		}

	}
}
