package org.acme.producer;

import io.agroal.api.AgroalDataSource;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;

import javax.enterprise.inject.Any;
import javax.ws.rs.Produces;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

@Slf4j
public class JdbiProducer {

    @Produces
    @Any
    Jdbi jdbi(AgroalDataSource dataSource) {
        Jdbi jdbi = Jdbi.create(dataSource);
        initLog(jdbi);
        return jdbi;
    }

    private void initLog(Jdbi jdbi) {

        jdbi.setSqlLogger(new SqlLogger() {

            @Override
            public void logBeforeExecution(StatementContext context) {
                log.info("Execute query: \n\n{}", context.getRenderedSql());
                log.info(context.getBinding().toString());
            }

            @Override
            public void logAfterExecution(StatementContext context) {
                log.info("Query time: {} ", context.getElapsedTime(ChronoUnit.MILLIS) + " ms.\n");
            }

            @Override
            public void logException(StatementContext context, SQLException ex) {
                log.error("Error on execute query \n " + context.getRenderedSql() + "\n\n Error:", ex);
            }
        });

    }
}
