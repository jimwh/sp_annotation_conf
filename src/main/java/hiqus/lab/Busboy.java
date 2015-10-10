package hiqus.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class Busboy {

    private static final Logger log = LoggerFactory.getLogger(Busboy.class);
    private final JdbcTemplate jdbcTemplate;

    /**
     *
     * @param jdbcTemplate Autowired for Qualifier
     */
    @Autowired
    public Busboy(@Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void test() throws SQLException {
        log.info("{}-{}",
        jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName(),
        jdbcTemplate.queryForObject("select ora_database_name from dual", String.class));
    }
}
