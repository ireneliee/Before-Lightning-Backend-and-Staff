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
import ejb.session.stateless.SupportTicketEntitySessionBeanLocal;
import entity.AddressEntity;
import entity.EmployeeEntity;
import entity.ForumPostEntity;
import entity.MemberEntity;
import entity.ReplyEntity;
import entity.SupportTicketEntity;
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
import util.exception.CreateNewSupportTicketEntityException;
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
    private SupportTicketEntitySessionBeanLocal supportTicketEntitySessionBeanLocal;

    @EJB
    private MemberEntitySessionBeanLocal memberEntitySessionBeanLocal;

    @EJB
    private ForumPostsEntitySessionBeanLocal forumPostsEntitySessionBeanLocal;

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @PersistenceContext(unitName = "beforeLightningBackend-ejbPU")
    private EntityManager em;

    public MemberEmployeeDataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            employeeEntitySessionBeanLocal.retrieveEmployeeEntityByEmployeeEntityId(3l);
        } catch (EmployeeEntityNotFoundException ex) {
            System.out.println("done");
            initializeData();
        }
    }

    private void initializeData() {
        try {

            MemberEntity m1 = new MemberEntity("marymary", "password", "Mary", "Magdalene", "mary_magdalene@gmail.com", "96726568");
            MemberEntity m2 = new MemberEntity("bobbybob", "password", "Bobby", "Bob", "bobby_bob@gmail.com", "90418041");
            MemberEntity m3 = new MemberEntity("wonderland", "password", "Alice", "lilili", "alice_alice@gmail.com", "84756473");
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

                ForumPostEntity f1 = new ForumPostEntity("Custom built laptops?", "Hello peeps!\n"
                        + "\n"
                        + "I was doing a google search (as you do) for people/companies that do custom built laptops & every single one that I tried did not do what I wanted.\n"
                        + "\n"
                        + "These are the two things (specs) I was looking for to start off with;\n"
                        + "\n"
                        + "At least 17\" screen (most did this...but...)\n"
                        + "\n"
                        + "Touch Screen (...most didn't offer this & of the ones that did they did not do the two together...)\n", m1);
                ForumPostEntity f2 = new ForumPostEntity("Custom laptop built", "Have we reached a point in geekdom where PC system builders can cross-over to laptops, safely unlatching ourselves from the teet of the big-time manufacturers (Dell, Apple, etc.)?\n"
                        + "\n"
                        + "I sure hope so.\n"
                        + "\n"
                        + "I posed a similar question to Google, which returned seemingly helpful articles that detailed some pros and (mostly) cons of starting with a barebones, modular laptop case and building up from there. Unfortunately, said articles were quite dated; ie. tomshardware circa 2005 and pcmech crica 2007.", m2);
                ForumPostEntity f3 = new ForumPostEntity("Thinking about building your first PC? Don't – buy a gaming laptop instead", "I think Main Gear looks good. Anyone have a better site.", m3);
                ForumPostEntity f4 = new ForumPostEntity("I need a warranty, what should I do?!", "I broke my laptop within 3 months, it's expensive I don't know what to do!", m1);

                try {
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f1);
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f2);
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f3);
                    forumPostsEntitySessionBeanLocal.createNewForumPostEntity(f4);

                    ReplyEntity r1 = new ReplyEntity("Looks like PCSpecialist can make laptops with up to three drives (one SSD, two M.2s). Not sure about touchscreen you'd need to dig through the specs on each one", m1, f1);
                    ReplyEntity r2 = new ReplyEntity("Yeah, I checked that out before & as far as I can tell they are not touchscreen.", m2, f1);

                    try {
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r1, m1.getUserEntityId());
                        forumPostsEntitySessionBeanLocal.createNewReplyEntity(r2, m2.getUserEntityId());
                    } catch (ForumPostNotFoundException ex) {
                        Logger.getLogger(MemberEmployeeDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MemberEntityNotFoundException ex) {
                    System.out.println("problem here");
                }

                SupportTicketEntity s1 = new SupportTicketEntity(m1.getEmail(), "My laptop is broken");
                SupportTicketEntity s2 = new SupportTicketEntity(m2.getEmail(), "Do you have a warranty?");
                SupportTicketEntity s3 = new SupportTicketEntity("hihihihi@gmail.com", "Laptop not received even after 10 days.");
                SupportTicketEntity s4 = new SupportTicketEntity(m3.getEmail(), "Can I sell my laptop back to you?");
                SupportTicketEntity s5 = new SupportTicketEntity(m2.getEmail(), "Do you have a laptop recommendation?");
                SupportTicketEntity s6 = new SupportTicketEntity("dummydumb@gmail.com", "Do you have a laptop recommendation?");

                try {
                    supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(s1);
                    supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(s2);
                    supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(s3);
                    supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(s4);
                    supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(s5);
                    supportTicketEntitySessionBeanLocal.createNewSupportTicketEntity(s6);
                } catch (CreateNewSupportTicketEntityException ex) {
                    Logger.getLogger(MemberEmployeeDataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public void persist(Object object) {
        em.persist(object);
    }
}
