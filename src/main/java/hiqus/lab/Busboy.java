package hiqus.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Busboy {

    private static final Logger log = LoggerFactory.getLogger(Busboy.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Busboy(@Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        log.info(jdbcTemplate.queryForObject("select ora_database_name from dual", String.class));
    }

    public void test() {
        log.info(jdbcTemplate.queryForObject("select ora_database_name from dual", String.class));
    }
}
