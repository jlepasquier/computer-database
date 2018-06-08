package com.excilys.computerdatabase.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.pagination.Page;

@Repository("companyDAO")
public class CompanyDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    private static final int COMPANIES_PER_PAGE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    public CompanyDAO(EntityManager pEntityManager) {
        entityManager = pEntityManager;
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * Gets the list of all companies.
     * @param offset the offset
     * @return the company page
     */
    public List<Company> getCompanyList() {
        LOGGER.info("DAO : Get Company List");
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
        LOGGER.info("DAO : Get Company Page");
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

    @Transactional(readOnly = false)
    public boolean deleteCompany(Long id) {
        LOGGER.info("DAO : Delete Company");
        entityManager.joinTransaction();

        CriteriaDelete<Computer> delComputer = criteriaBuilder.createCriteriaDelete(Computer.class);
        Root<Computer> computer = delComputer.from(Computer.class);
        delComputer.where(criteriaBuilder.equal(computer.get("company").get("id"), id));
        entityManager.createQuery(delComputer).executeUpdate();

        CriteriaDelete<Company> delCompany = criteriaBuilder.createCriteriaDelete(Company.class);
        Root<Company> company = delCompany.from(Company.class);
        delCompany.where(criteriaBuilder.equal(company.get("id"), id));
        return entityManager.createQuery(delCompany).executeUpdate() > 0;
    }
}
