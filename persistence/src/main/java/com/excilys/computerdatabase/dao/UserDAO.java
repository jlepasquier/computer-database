package com.excilys.computerdatabase.dao;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.User;

@Repository("userDAO")
public class UserDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    UserDAO(EntityManager pEntityManager) {
         entityManager = pEntityManager;
    }
    
    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }


	protected final Log LOGGER = LogFactory.getLog(getClass());
	
	public Optional<User> findByUsername(String username) {
	    LOGGER.info("Find by username : " + username);
	    
	    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> user = query.from(User.class);
        query.where(criteriaBuilder.equal(user.get("name"), username));
        query.select(user);

        return entityManager.createQuery(query).getResultList().stream().findFirst();
	}

}