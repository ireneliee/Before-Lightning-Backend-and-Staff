/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReviewEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.ReviewEntityNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Koh Wen Jie
 */
@Local
public interface ReviewEntitySessionBeanLocal {

    public List<ReviewEntity> retrieveAllReviewEntities();

    public Long createNewReviewEntity(ReviewEntity newReviewEntity) throws InputDataValidationException, UnknownPersistenceException;

    public ReviewEntity retrieveReviewEntityByReviewEntityId(Long partEntityId) throws ReviewEntityNotFoundException;

    public List<ReviewEntity> retrieveReviewEntitiesByUsername(String username) throws ReviewEntityNotFoundException;
    
}
