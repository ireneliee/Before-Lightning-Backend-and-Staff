/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.ProductTypeSessionBeanLocal;
import entity.EmployeeEntity;
import entity.ProductTypeEntity;
import entity.UserEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InputDataValidationException;
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
    private ProductTypeSessionBeanLocal productTypeSessionBean;

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    public DataInitialisationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initializeData();
    }

    private void initializeData() {

       

    }
}
