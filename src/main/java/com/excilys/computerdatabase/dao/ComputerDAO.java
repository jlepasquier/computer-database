package main.java.com.excilys.computerdatabase.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.model.Computer;

@Repository("computerDAO")
public class ComputerDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    
    private static final int COMPUTERS_PER_PAGE = 25;

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    ComputerDAO(DataSource pDataSource, EntityManager pEntityManager) {
         entityManager = pEntityManager;
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Computer> getComputerPage(int offset) {
        LOGGER.info("DAO : Get Computer Page");

        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> computer = criteriaQuery.from(Computer.class);
        criteriaQuery.select(computer);

        TypedQuery<Computer> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult((offset - 1) * COMPUTERS_PER_PAGE).setMaxResults(COMPUTERS_PER_PAGE);
        List<Computer> computerList = query.getResultList();

        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, computerList);
    }

    public Optional<Computer> getComputer(long id) {
        CriteriaQuery<Computer> query = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> computer = query.from(Computer.class);
        query.where(criteriaBuilder.equal(computer.get("id"), id));
        query.select(computer);

        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @Transactional(readOnly = false)
    public Optional<Long> createComputer(Computer computer) {
        LOGGER.info("DAO : Create Computer");
        computer.setId(null);
        entityManager.joinTransaction();
        entityManager.persist(computer);
        entityManager.flush();
        entityManager.refresh(computer);
        entityManager.clear();
        return Optional.of(computer.getId());
    }

    @Transactional(readOnly = false)
    public boolean updateComputer(Computer computer) {
        LOGGER.info("DAO : Update Computer");
        entityManager.joinTransaction();

        CriteriaUpdate<Computer> update = criteriaBuilder.createCriteriaUpdate(Computer.class);
        Root<Computer> cpuRoot = update.from(Computer.class);
        int updatedRows = 0;

        update.set("introduced", computer.getIntroduced());
        update.set("discontinued", computer.getDiscontinued());
        update.set("name", computer.getName());
        if (computer.getCompany() != null) {
            update.set("company", computer.getCompany());
        }

        update.where(criteriaBuilder.equal(cpuRoot.get("id"), computer.getId()));
        updatedRows = entityManager.createQuery(update).executeUpdate();

        return (updatedRows > 0);
    }

    @Transactional(readOnly = false)
    public boolean deleteComputers(Set<Long> ids) throws InvalidIdException {
        LOGGER.info("DAO : Delete Computer");
        entityManager.joinTransaction();

        CriteriaDelete<Computer> delete = criteriaBuilder.createCriteriaDelete(Computer.class);
        Root<Computer> computer = delete.from(Computer.class);
        delete.where(computer.get("id").in(ids));

        int updatedRows = entityManager.createQuery(delete).executeUpdate();
        return (updatedRows > 0);
    }

    public Page<Computer> searchComputer(String search, int offset) {
        LOGGER.info("DAO : Search Computer");
        String searchString = "%" + search + "%";

        CriteriaQuery<Computer> searchComputer = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> computer = searchComputer.from(Computer.class);
        searchComputer.where(criteriaBuilder.like(computer.get("name"), searchString));
        searchComputer.select(computer);

        TypedQuery<Computer> query = entityManager.createQuery(searchComputer)
                .setFirstResult((offset - 1) * COMPUTERS_PER_PAGE).setMaxResults(COMPUTERS_PER_PAGE);
        List<Computer> computerList = query.getResultList();

        return new Page<Computer>(COMPUTERS_PER_PAGE, offset, computerList);
    }

    public Optional<Long> getComputerCount() {
        LOGGER.info("DAO : Get Computer Count");
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(Computer.class)));
        return Optional.of(entityManager.createQuery(query).getSingleResult());
    }

    public Optional<Long> getComputerPageCount() {
        LOGGER.info("DAO : Get Computer Page Count");
        Long numberOfPages = null;
        Optional<Long> numberOfComputers = getComputerCount();

        if (numberOfComputers.isPresent()) {
            numberOfPages = (long) Math.ceil(numberOfComputers.get() / COMPUTERS_PER_PAGE);
        }
        return Optional.ofNullable(numberOfPages);
    }

    public Optional<Long> getSearchComputerCount(String search) {
        LOGGER.info("DAO : Get Search Computer Count");
        String searchString = "%" + search + "%";

        CriteriaQuery<Long> count = criteriaBuilder.createQuery(Long.class);
        Root<Computer> computer = count.from(Computer.class);

        count.where(criteriaBuilder.like(computer.get("name"), searchString));
        count.select(criteriaBuilder.count(computer));

        return Optional.of(entityManager.createQuery(count).getSingleResult());
    }

    public Optional<Long> getSearchComputerPageCount(String search) {
        LOGGER.info("DAO : Get Search Computer Page Count");
        Long numberOfPages = null;
        Optional<Long> numberOfComputers = getSearchComputerCount(search);

        if (numberOfComputers.isPresent()) {
            numberOfPages = (long) Math.ceil(numberOfComputers.get() / COMPUTERS_PER_PAGE);
        }

        return Optional.ofNullable(numberOfPages);
    }
}
