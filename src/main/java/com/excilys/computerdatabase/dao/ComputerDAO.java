package main.java.com.excilys.computerdatabase.dao;

import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.print.PrintServiceLookup;
import javax.sql.DataSource;

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

    private static final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE `computer` SET `name`=?,`introduced`=?,`discontinued`=?,`company_id`=? WHERE id=?";
    private static final String FIND_PAGE = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id LIMIT ? OFFSET ?";
    private static final String FIND_BY_ID = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.id=?";
    private static final String DELETE = "DELETE FROM `computer` WHERE id IN %s";
    private static final String COUNT = "SELECT COUNT(*) FROM `computer`";
    private static final String SEARCH = "SELECT cpu.id AS id, cpu.name AS cpuname, cpu.introduced AS introduced, cpu.discontinued AS discontinued, cpy.name AS companyname, cpy.id AS companyid FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ? ORDER BY cpu.name LIMIT ? OFFSET ? ";
    private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer as cpu LEFT JOIN company as cpy ON cpy.id = cpu.company_id WHERE cpu.name LIKE ? OR cpy.name LIKE ?";

    ComputerDAO(DataSource pDataSource, EntityManager pEntityManager) {
        jdbcTemplate = new JdbcTemplate(pDataSource);
        entityManager = pEntityManager;
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Computer> getComputerPage(int offset) {
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
        Root<Computer> company = query.from(Computer.class);
        query.where(criteriaBuilder.equal(company.get("id"), id));
        query.select(company);

        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    @Transactional(readOnly = false)
    public Optional<Long> createComputer(Computer computer) {
        computer.setId(null);
        entityManager.joinTransaction();
        entityManager.persist(computer);
        entityManager.flush();
        entityManager.refresh(computer);
        entityManager.clear();
        return Optional.of(computer.getId());
    }

    public boolean updateComputer(Computer cpu) {

        long updatedRows = jdbcTemplate.update(connection -> {
            return QueryMapper.prepareStatement(connection, UPDATE, cpu.getName(),
                    cpu.getIntroduced() == null ? Types.NULL : Date.valueOf(cpu.getIntroduced()),
                    cpu.getDiscontinued() == null ? Types.NULL : Date.valueOf(cpu.getDiscontinued()),
                    cpu.getCompany() == null ? null : cpu.getCompany().getId(), cpu.getId());
        });

        return (updatedRows > 0);
    }

    public boolean deleteComputers(String ids) throws InvalidIdException {
        long updatedRows = jdbcTemplate.update(connection -> {
            return QueryMapper.prepareStatement(connection, String.format(DELETE, ids));
        });
        return (updatedRows > 0);
    }

    public Page<Computer> searchComputer(String search, int offset) {

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
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        query.select(criteriaBuilder.count(query.from(Computer.class)));
        return Optional.of(entityManager.createQuery(query).getSingleResult());
    }

    public Optional<Long> getComputerPageCount() {

        Long numberOfPages = null;
        Optional<Long> numberOfComputers = getComputerCount();

        if (numberOfComputers.isPresent()) {
            numberOfPages = (long) Math.ceil(numberOfComputers.get() / COMPUTERS_PER_PAGE);
        }

        return Optional.ofNullable(numberOfPages);
    }

    public Optional<Long> getSearchComputerCount(String search) {
        String searchString = "%" + search + "%";
        return jdbcTemplate.queryForObject(SEARCH_COUNT, new Object[] { searchString, searchString },
                (resultSet, rowNum) -> {
                    return Optional.of(resultSet.getLong(1));
                });
    }

    public Optional<Long> getSearchComputerPageCount(String search) {
        Long numberOfPages = null;
        Optional<Long> numberOfComputers = getSearchComputerCount(search);

        if (numberOfComputers.isPresent()) {
            numberOfPages = (long) Math.ceil(numberOfComputers.get() / COMPUTERS_PER_PAGE);
        }

        return Optional.ofNullable(numberOfPages);
    }
}
