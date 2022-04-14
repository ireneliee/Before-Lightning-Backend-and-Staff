/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import entity.AccessoryItemEntity;
import entity.PartChoiceEntity;
import entity.PromotionEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AccessoryAlreadyExistsInPromotionException;
import util.exception.AccessoryItemEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PartChoiceAlreadyExistsInPromotionException;
import util.exception.PartChoiceEntityNotFoundException;
import util.exception.ProductEntityNotFoundException;
import util.exception.PromotionDiscountTypeExclusiveOrException;
import util.exception.PromotionEntityNameExistsException;
import util.exception.PromotionEntityNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kaiyu
 */
@Singleton
@LocalBean
@Startup
@DependsOn("PurchaseOrderDataInitializationSessionBean")

public class PromotionDataInitializationSessionBean {

	@EJB
	private AccessoryItemEntitySessionBeanLocal accessoryItemEntitySessionBean;

	@EJB
	private PartChoiceEntitySessionBeanLocal partChoiceEntitySessionBean;

	@EJB
	private PromotionEntitySessionBeanLocal promotionEntitySessionBean;

	@PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
	private EntityManager em;

	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")
	public PromotionDataInitializationSessionBean() {
	}

	@PostConstruct
	public void postConstruct() {
		try {
			promotionEntitySessionBean.retrievePromotionEntityById(1l);
		} catch (PromotionEntityNotFoundException ex) {
			initializeData();
		}
	}

	public void initializeData() {
//		String promotionName, LocalDate startDate, LocalDate endDate, Double discount, BigDecimal discountedPrice

		LocalDate aprilSaleStartDate = LocalDate.of(2022, Month.APRIL, 1);
		LocalDate aprilSaleEndDate = LocalDate.of(2022, Month.APRIL, 30);
		LocalDate yearRoundSaleStartDate = LocalDate.of(2022, Month.JANUARY, 1);
		LocalDate yearRoundSaleEndDate = LocalDate.of(2022, Month.DECEMBER, 31);

		try {

			//make list of promo accessories
			List<AccessoryItemEntity> listPromoAccessoryItems = new ArrayList<>();

			AccessoryItemEntity promoAccessory1 = accessoryItemEntitySessionBean.retrieveAccessoryItemByName("LOGITECH PRO 910-005940");
			AccessoryItemEntity promoAccessory2 = accessoryItemEntitySessionBean.retrieveAccessoryItemByName("KEF LS50 Meta");
			AccessoryItemEntity promoAccessory3 = accessoryItemEntitySessionBean.retrieveAccessoryItemByName("Elac Debut B5.2");

			listPromoAccessoryItems.add(promoAccessory1);
			listPromoAccessoryItems.add(promoAccessory2);
			listPromoAccessoryItems.add(promoAccessory3);

			//make list of promo partChoices
			List<PartChoiceEntity> listPromoPartChoices = new ArrayList<>();
			PartChoiceEntity promoPartChoice1 = partChoiceEntitySessionBean.retrievePartChoiceEntityByPartChoiceName("Beast 15S Chassis");
			PartChoiceEntity promoPartChoice2 = partChoiceEntitySessionBean.retrievePartChoiceEntityByPartChoiceName("Intel® Core™ i5-12500H Processor (12 Cores)");
			listPromoPartChoices.add(promoPartChoice1);
			listPromoPartChoices.add(promoPartChoice2);

			//promo with partchoice (chassis + CPU)
			PromotionEntity promotion1 = new PromotionEntity("April Fire Sale!", aprilSaleStartDate, aprilSaleEndDate, 20.0, new BigDecimal(0));
			Long promo1Id = promotionEntitySessionBean.createNewPromotionEntity(promotion1);

			//promo with accessoryitem
			PromotionEntity promotion2 = new PromotionEntity("Year-Round Sale!", yearRoundSaleStartDate, yearRoundSaleEndDate, 0.0, new BigDecimal(10));
			Long promo2Id = promotionEntitySessionBean.createNewPromotionEntity(promotion2);

			//add accessory and promo list to promo 1
			promotionEntitySessionBean.addPartChoicesToPromotion(promo1Id, listPromoPartChoices);

			System.out.println(":: printing promo tings from promo1 ::");
			for (AccessoryItemEntity a : listPromoAccessoryItems) {
				System.out.println(a);
			}
			for (PartChoiceEntity p : listPromoPartChoices) {
				System.out.println(p);
			}
			System.out.println("########## end of promo1 ##########\n" + "\n");

			//add accessory and promo list to promo2
			promotionEntitySessionBean.addAccessoryItemsToPromotion(promo2Id, listPromoAccessoryItems);
			List<AccessoryItemEntity> promo2Accessories = promotionEntitySessionBean.retrievePromotionEntityById(promo2Id).getAccessoryItemEntities();
			System.out.println(":: printing promo tings from promo2 ::");
			for (AccessoryItemEntity a : promo2Accessories) {
				System.out.println(a);
			}
			System.out.println("########## end of promo2 ##########\n" + "\n");

		} catch (AccessoryItemEntityNotFoundException ex) {
			System.out.println("accessory not found man");
		} catch (PartChoiceEntityNotFoundException ex) {
			System.out.println("partchoice not found man");
		} catch (PromotionEntityNameExistsException ex) {
			System.out.println("promo name exists man");
		} catch (InputDataValidationException ex) {
			System.out.println("errrrmmm input data got problem");
		} catch (PromotionDiscountTypeExclusiveOrException ex) {
			System.out.println("errrrmmm promo discount got problem");
		} catch (UnknownPersistenceException ex) {
			System.out.println("errrrmmm unknown persistence problem");
		} catch (PromotionEntityNotFoundException ex) {
			System.out.println("promo not found...");
		} catch (AccessoryAlreadyExistsInPromotionException ex) {
			System.out.println("accessory exists in promo");
		} catch (PartChoiceAlreadyExistsInPromotionException ex) {
			System.out.println("accessory exists in promo");
		}
	}
}
