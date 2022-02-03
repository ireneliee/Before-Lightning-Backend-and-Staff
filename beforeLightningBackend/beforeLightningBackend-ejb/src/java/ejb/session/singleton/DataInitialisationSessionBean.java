/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.EmployeeEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;

/**
 *
 * @author Koh Wen Jie
 */
@Singleton
@LocalBean
@Startup
public class DataInitialisationSessionBean {


    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    public DataInitialisationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {

    }

    private void initializeData() {

    }
}

