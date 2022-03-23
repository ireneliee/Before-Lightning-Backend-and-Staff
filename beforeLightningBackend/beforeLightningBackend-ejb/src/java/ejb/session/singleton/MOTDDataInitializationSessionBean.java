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
import ejb.session.stateless.MessageOfTheDayEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.AccessoryEntity;
import entity.AccessoryItemEntity;
import entity.AddressEntity;
import entity.EmployeeEntity;
import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.MessageOfTheDayEntity;
import entity.PartChoiceEntity;
import entity.PartEntity;
import entity.ProductEntity;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import util.exception.AddressEntityNotFoundException;
import util.exception.CreateNewProductEntityException;
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

public class MOTDDataInitializationSessionBean {

	@EJB
	private EmployeeEntitySessionBeanLocal employeeEntitySessionBean;

    @EJB
    private MessageOfTheDayEntitySessionBeanLocal messageOfTheDayEntitySessionBeanLocal;
	
    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    public MOTDDataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {

        if (messageOfTheDayEntitySessionBeanLocal.retrieveAllMessagesOfTheDay().isEmpty()) {
            System.out.println("done");
            initializeData();
        }
    }

    private void initializeData() {

        try {
			EmployeeEntity dummy = new EmployeeEntity(EmployeeAccessRightEnum.ADMIN, "dummy", "1234qwer!@#$", "dummy","dummy", "dummy@gmail.com", "82828282");
			employeeEntitySessionBean.createNewEmployeeEntity(dummy);
            Date today = Calendar.getInstance().getTime();
            messageOfTheDayEntitySessionBeanLocal.createNewMessageOfTheDay(new MessageOfTheDayEntity("Live Laugh Love", "when life gets tough just live laugh love bro", today), dummy);
            messageOfTheDayEntitySessionBeanLocal.createNewMessageOfTheDay(new MessageOfTheDayEntity("SAF 8 core values", "loyalty to country, leadership, etc", today), dummy);
            messageOfTheDayEntitySessionBeanLocal.createNewMessageOfTheDay(new MessageOfTheDayEntity("Google please hire me", "willing to work long hours, will occasionally tell jokes #worthEveryBuck", today), dummy);
            System.out.print("hello irene");
        } catch (InputDataValidationException ex) {
            System.out.println("THIS IS THE ERROR");
            System.out.println(ex.getMessage());
        } catch (EmployeeEntityUsernameExistException ex) {
            System.out.println("employee name already exists bruh");
		} catch (UnknownPersistenceException ex) {
            System.out.println("unknwn persistence exception!");			
		} catch (EmployeeEntityNotFoundException ex) {
			System.out.println("employee name not found");
		}

    }
}
