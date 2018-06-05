package main.java.com.excilys.computerdatabase.dao;

import java.util.ArrayList;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.excilys.computerdatabase.exception.InvalidIdException;
import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.mapper.QueryMapper;
import main.java.com.excilys.computerdatabase.model.Computer;

@Repository("computerDAO")
public class ComputerDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    private JdbcTemplate jdbcTemplate; // TODO : delete once updated

    private static final int COMPUTERS_PER_PAGE = 25;

    private static final String SEARCH = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ? ORDER BY cpu.name LIMIT ? OFFSET ? ";
    private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
    
    ComputerDAO(DataSource pDataSource, EntityManager pEntityManager) {
        jdbcTemplate = new JdbcTemplate(pDataSource);
        entityManager = pEntityManager;
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Computer> getComputerPage(int offset) {
        LOGGER.info("DAO : Get Computer Page");

        CriteriaQuery<Computer> criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> company = criteriaQuery.from(Computer.class);
        criteriaQuery.select(company);

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
        entityManager.joinTransaction();
        
        computer.setId(null);
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

        List<Computer> computerList;
        try {
            String searchString = "%" + search + "%";
            computerList = jdbcTemplate.query(SEARCH, preparedStatement -> {
                QueryMapper.prepareStatement(preparedStatement, searchString, searchString, COMPUTERS_PER_PAGE,
                        (offset - 1) * COMPUTERS_PER_PAGE);
            }, (resultSet, rowNum) -> {
                return ComputerMapper.createComputer(resultSet);
            });
        } catch (Exception e) {
            computerList = new ArrayList<>();
        }
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
        return jdbcTemplate.queryForObject(SEARCH_COUNT, new Object[] { searchString, searchString },
                (resultSet, rowNum) -> {
                    return Optional.of(resultSet.getLong(1));
                });
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
