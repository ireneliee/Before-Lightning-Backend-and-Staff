/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AccessoryEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AccessoryNameExistsException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kaiyu
 */
@Local
public interface AccessoryEntitySessionBeanLocal {
	
		public Long createNewAccessoryEntity(AccessoryEntity newAccessoryEntity) throws AccessoryNameExistsException, UnknownPersistenceException, InputDataValidationException;
		public List<AccessoryEntity> retrieveAllAccessoryEntities();
		public AccessoryEntity retrieveAccessoryEntityById(Long id);
		public AccessoryEntity retrieveAccessoryEntityByAccessoryName(String accessoryName);
		public void updateAccessoryEntity(AccessoryEntity newAccessoryEntity);
		public void deleteAccessoryEntity(Long productTypeId);
		
}
