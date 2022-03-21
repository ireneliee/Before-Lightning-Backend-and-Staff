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
import entity.AddressEntity;
import entity.EmployeeEntity;
import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.ReplyEntity;
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
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.AddressEntityNotFoundException;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.EmployeeEntityUsernameExistException;
import util.exception.ForumPostNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.MemberEntityNotFoundException;
import util.exception.MemberEntityUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Startup
@Singleton
@LocalBean
@DependsOn("MOTDDataInitializationSessionBean")
public class MemberEmployeeDataInitializationSessionBean {

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

    public MemberEmployeeDataInitializationSessionBean() {
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
        try {

            MemberEntity m1 = new MemberEntity("marymary", "password", "Mary", "Magdalene", "mary_magdalene@gmail.com", "96726568");
            MemberEntity m2 = new MemberEntity("bobbybob", "password", "Bobby", "Bob", "bobby_bob@gmail.com", "90418041");
            MemberEntity m3 = new MemberEntity("aliceinwonderland", "password", "Alice", "lilili", "alice_alice@gmail.com", "84756473");
            AddressEntity ad1 = new AddressEntity("23", "23", "196758", "Singapore");
            AddressEntity ad2 = new AddressEntity("24", "266", "196658", "Singapore");
            AddressEntity ad3 = new AddressEntity("25", "54", "195758", "Singapore");

            memberEntitySessionBeanLocal.createNewAddressEntity(ad1);
            memberEntitySessionBeanLocal.createNewAddressEntity(ad2);
            memberEntitySessionBeanLocal.createNewAddressEntity(ad3);

            try {
                memberEntitySessionBeanLocal.createNewMemberEntity(m1, ad1);
                memberEntitySessionBeanLocal.createNewMemberEntity(m2, ad2);
                memberEntitySessionBeanLocal.createNewMemberEntity(m3, ad3);

                ForumPostEntity f1 = new ForumPostEntity("I like my new laptop!", "My new laptop is xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  the specification is xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", m1);
                ForumPostEntity f2 = new ForumPostEntity("I don't like my new laptop!", "I hate my new laptop it sucks big timexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", m2);
                ForumPostEntity f3 = new ForumPostEntity("I like my new mouse!", "I like my new mouse  it moves so fast xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", m3);
                ForumPostEntity f4 = new ForumPostEntity("I need a warranty, what should I do?!", "I broke my laptop within 3 months, it's expensive I don't know what to doxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", m1);

                try {
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f1);
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f2);
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f3);
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f4);

                    ReplyEntity r1 = new ReplyEntity("I like this post!", m1, f1);
                    ReplyEntity r2 = new ReplyEntity("Thank you for sharing this :)", m2, f1);
                    ReplyEntity r3 = new ReplyEntity("Do you mind sharing more", m3, f1);
                    ReplyEntity r4 = new ReplyEntity("I don't like this", m3, f1);
                    ReplyEntity r5 = new ReplyEntity("Do you mind sharing more hehe", m1, f1);

                    try {
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r1, m1.getUserEntityId());
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r2, m2.getUserEntityId());
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r3, m3.getUserEntityId());
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r4, m3.getUserEntityId());
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r5, m1.getUserEntityId());
                    } catch (ForumPostNotFoundException ex) {
                        Logger.getLogger(MemberEmployeeDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MemberEntityNotFoundException ex) {
                    System.out.println("problem here");
                }

            } catch (MemberEntityUsernameExistException | AddressEntityNotFoundException | InputDataValidationException | UnknownPersistenceException ex) {
                System.out.println("problem here");
            }

            EmployeeEntity newEmployee1 = new EmployeeEntity(EmployeeAccessRightEnum.ADMIN, "manager", "password", "manager", "one", "manager@gmail.com", "99999999");
            EmployeeEntity newEmployee2 = new EmployeeEntity(EmployeeAccessRightEnum.OPERATION, "operationstaff", "password", "operation", "one", "operation@gmail.com", "99999999");
            EmployeeEntity newEmployee3 = new EmployeeEntity(EmployeeAccessRightEnum.PRODUCT, "productstaff", "password", "product", "one", "product@gmail.com", "99999999");
            EmployeeEntity newEmployee4 = new EmployeeEntity(EmployeeAccessRightEnum.SALES, "salesstaff", "password", "sales", "one", "sales@gmail.com", "99999999");

            try {
                employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee1);
                employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee2);
                employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee3);
                employeeEntitySessionBeanLocal.createNewEmployeeEntity(newEmployee4);

            } catch (EmployeeEntityUsernameExistException | InputDataValidationException | UnknownPersistenceException ex) {
                System.out.println("problem here");
            }

        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            System.out.println("problem here");
        }

    }
}
