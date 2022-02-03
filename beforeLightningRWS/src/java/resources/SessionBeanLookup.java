/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
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
}
