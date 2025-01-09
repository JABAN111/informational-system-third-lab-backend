package is.fistlab.configurations;

import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;

import org.eclipse.persistence.config.BatchWriting;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "is.fistlab.database.repositories")
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {

    protected EclipseLinkJpaConfiguration(final DataSource dataSource,
                                          final JpaProperties properties,
                                          final ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(dataSource, properties, jtaTransactionManager);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final Map<String, Object> ret = new HashMap<>();
        ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
        return ret;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactory(
            final EntityManagerFactoryBuilder builder,
            final DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("is.fistlab.database")
                .persistenceUnit("FirstLabPersistenceUnit")
                .properties(initJpaProperties()).build();
    }

    @Bean
    public static DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/first");
//        dataSource.setUrl("jdbc:postgresql://postgres:5432/first");
        dataSource.setUsername("user");
        dataSource.setPassword("user");
        return dataSource;
    }

    @Bean
    public static PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


    @Bean
    @Primary
    public static JpaProperties properties() {
        final JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setShowSql(true);
        jpaProperties.setDatabasePlatform("org.eclipse.persistence.platform.database.PostgreSQLPlatform");
        return jpaProperties;
    }

    private static Map<String, ?> initJpaProperties() {
        final Map<String, Object> ret = new HashMap<>();
        ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC); // Включаем batch writing
        ret.put(PersistenceUnitProperties.BATCH_WRITING_SIZE, "50"); // Размер батча
//        ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.FINEST_LABEL);
//        ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.JPA);
//        ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.OFF); // Полностью отключаем логирование

        ret.put(PersistenceUnitProperties.PERSISTENCE_CONTEXT_COMMIT_ORDER, "Changes");
//        ret.put(PersistenceUnitProperties.ORDER_UPDATES, "true"); // Упорядочиваем UPDATE
        ret.put(PersistenceUnitProperties.WEAVING, "false");
        ret.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_ONLY);
        ret.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
        return ret;
    }
//    private static Map<String, ?> initJpaProperties() {
//        final Map<String, Object> ret = new HashMap<>();
//        ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
////        ret.put(PersistenceUnitProperties.BATCH_WRITING_SIZE, "50"); // Размер батча
//        ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.FINEST_LABEL);
//        ret.put(PersistenceUnitProperties.WEAVING, "false");
//        ret.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_ONLY);
//        ret.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
//        return ret;
//    }

//    private static Map<String, ?> initJpaProperties() {
//        final Map<String, Object> ret = new HashMap<>();
//        ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
//        ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.FINEST_LABEL);
//        ret.put(PersistenceUnitProperties.WEAVING, "false");
//        ret.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_ONLY);
//        ret.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
//        return ret;
//    }
}
