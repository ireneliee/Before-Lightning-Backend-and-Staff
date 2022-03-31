/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AccessoryEntitySessionBeanLocal;
import ejb.session.stateless.AccessoryItemEntitySessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.ForumPostsEntitySessionBeanLocal;
import ejb.session.stateless.MemberEntitySessionBeanLocal;
import ejb.session.stateless.PartChoiceEntitySessionBeanLocal;
import ejb.session.stateless.PartEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.PromotionEntitySessionBeanLocal;
import ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Koh Wen Jie
 */
public class SessionBeanLookup {

    private final String ejbModuleJndiPath;

    public SessionBeanLookup() {
        ejbModuleJndiPath = "java:global/beforeLightningBackend/beforeLightningBackend-ejb/";
    }

    public EmployeeEntitySessionBeanLocal lookupEmployeeEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (EmployeeEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "EmployeeEntitySessionBean!ejb.session.stateless.EmployeeEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public MemberEntitySessionBeanLocal lookupMemberEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MemberEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "MemberEntitySessionBean!ejb.session.stateless.MemberEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public ForumPostsEntitySessionBeanLocal lookupForumPostsEntitySessionBeanLocal() {
        try {
            Context c = new InitialContext();
            return (ForumPostsEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath +"ForumPostsEntitySessionBean!ejb.session.stateless.ForumPostsEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public ProductEntitySessionBeanLocal lookupProductEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "ProductEntitySessionBean!ejb.session.stateless.ProductEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
//
//    public PartEntitySessionBeanLocal lookupPartEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (PartEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "PartEntitySessionBean!ejb.session.stateless.PartEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    public PartChoiceEntitySessionBeanLocal lookupPartChoiceEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (PartChoiceEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "PartChoiceEntitySessionBean!ejb.session.stateless.PartChoiceEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
    public AccessoryEntitySessionBeanLocal lookupAccessoryEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AccessoryEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "AccessoryEntitySessionBean!ejb.session.stateless.AccessoryEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
//
//    public AccessoryItemEntitySessionBeanLocal lookupAccessoryItemEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (AccessoryItemEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "AccessoryItemEntitySessionBean!ejb.session.stateless.AccessoryItemEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    public PromotionEntitySessionBeanLocal lookupPromotionEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (PromotionEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "PromotionEntitySessionBean!ejb.session.stateless.PromotionEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    public ForumPostsEntitySessionBeanLocal lookupForumEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (ForumPostsEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "ForumPostsEntitySessionBean!ejb.session.stateless.ForumPostsEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
//
//    public PurchaseOrderEntitySessionBeanLocal lookupPurchaseOrderEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (PurchaseOrderEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "PurchaseOrderEntitySessionBean!ejb.session.stateless.PurchaseOrderEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }

//    public PurchaseOrderLineItemEntitySessionBeanLocal lookupPurchaseOrderLineItemEntitySessionBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (PurchaseOrderLineItemEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "PurchaseOrderLineItemEntitySessionBean!ejb.session.stateless.PurchaseOrderLineItemEntitySessionBeanLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }

    
}
