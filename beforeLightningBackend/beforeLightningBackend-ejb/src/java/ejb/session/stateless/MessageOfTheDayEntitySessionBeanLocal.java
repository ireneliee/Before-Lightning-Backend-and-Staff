/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.EmployeeEntity;
import entity.MessageOfTheDayEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.EmployeeEntityNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kaiyu
 */
@Local
public interface MessageOfTheDayEntitySessionBeanLocal {

	MessageOfTheDayEntity createNewMessageOfTheDay(MessageOfTheDayEntity newMessageOfTheDayEntity, EmployeeEntity employee) throws InputDataValidationException, UnknownPersistenceException, EmployeeEntityNotFoundException;

	List<MessageOfTheDayEntity> retrieveAllMessagesOfTheDay();

}

