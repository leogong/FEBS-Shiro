package cc.mrbird.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author leo on 21/01/2018.
 */
@Configuration
@EnableTransactionManagement
@EnableScheduling
public class CoreSpringConfig {
//    @Autowired
//    private DataSource dataSource;

//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setConfigLocation(new ClassPathResource("/mybatis/mybatis-config.xml.bk"));
//        sqlSessionFactory.setFailFast(true);
//        return sqlSessionFactory.getObject();
//    }
}
