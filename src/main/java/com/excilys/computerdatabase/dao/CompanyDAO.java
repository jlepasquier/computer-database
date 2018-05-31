package main.java.com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import main.java.com.excilys.computerdatabase.model.Company;

@Repository("companyDAO")
public class CompanyDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager transactionManager;

    private static final int COMPANIES_PER_PAGE = 10;

    private static final String FIND_ALL = "SELECT * FROM company";
    private static final String FIND_PAGE = "SELECT * FROM company LIMIT ? OFFSET ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";
    private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id=?";

    public CompanyDAO(DataSource pDataSource, PlatformTransactionManager pPlatformTransactionManager,
            EntityManager pEntityManager) {
        jdbcTemplate = new JdbcTemplate(pDataSource);
        transactionManager = pPlatformTransactionManager;
        entityManager = pEntityManager;
    }

    /**
     * Gets the list of all companies.
     * @param offset the offset
     * @return the company page
     */
    public List<Company> getCompanyList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> company = criteriaQuery.from(Company.class);
        criteriaQuery.select(company);
        TypedQuery<Company> q = entityManager.createQuery(criteriaQuery);
        return q.getResultList();
    }

    /**
     * Gets the company page.
     * @param offset the offset
     * @return the company page
     */
    public Page<Company> getCompanyPage(int offset) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> company = criteriaQuery.from(Company.class);
        criteriaQuery.select(company);
        
        TypedQuery<Company> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult((offset - 1) * COMPANIES_PER_PAGE).setMaxResults(COMPANIES_PER_PAGE);
        List<Company> companyList = query.getResultList();
        
        return new Page<Company>(COMPANIES_PER_PAGE, offset, companyList);
    }

    /**
     * Deletes a company
     * @param id the id of the company to delete
     * @return whether the deletion was succesful
     */
    public boolean deleteCompany(Long id) {

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            jdbcTemplate.update(DELETE_COMPUTERS, id);
            int updatedRows = jdbcTemplate.update(DELETE_COMPANY, id);
            transactionManager.commit(status);
            return (updatedRows > 0);
        } catch (DataAccessException e) {
            transactionManager.rollback(status);
            return false;
        }
    }
}
