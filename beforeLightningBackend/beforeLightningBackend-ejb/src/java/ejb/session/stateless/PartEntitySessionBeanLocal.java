/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PartEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewPartEntityException;
import util.exception.InputDataValidationException;
import util.exception.PartEntityNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePartEntityException;

/**
 *
 * @author irene
 */
@Local
public interface PartEntitySessionBeanLocal {

    public PartEntity createNewPartEntity(List<String> productCodes, PartEntity newPartEntity) throws CreateNewPartEntityException, UnknownPersistenceException, InputDataValidationException;

    public void updatePartEntity(PartEntity updatedPartEntity) throws PartEntityNotFoundException, UpdatePartEntityException, InputDataValidationException;
    
}
