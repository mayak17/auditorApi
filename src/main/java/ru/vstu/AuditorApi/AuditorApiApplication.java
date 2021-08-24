package ru.vstu.AuditorApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.vstu.AuditorApi.configurations.DatasourceRouter;

import javax.sql.DataSource;
import java.sql.SQLException;

//Конфигурировать DataSource вручную
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class})
public class AuditorApiApplication {

	// Все настройки из файла application.properties
	@Autowired
	private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(AuditorApiApplication.class, args);
    }


	//Глобально источник данных будет браться отсюда
	//Здесь определяется роутер, который внутри себя решает,
	// какое именно подключение использовать
	@Autowired
	@Bean
	@Primary
	public DataSource getDataSource(DataSource dataSourceHll1, DataSource dataSourceHll2) {
		DatasourceRouter dataSource = new DatasourceRouter();
		dataSource.initDataSources(dataSourceHll1, dataSourceHll2);
		return dataSource;
	}

	//Определить разные подключения, которые передаются в роутер подключений
	//Из них роутер будет выбирать, какое подключение использовать
	@Bean(name = "dataSourceHll1")
	public DataSource getDataSource1() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		// See: datasouce-cfg.properties
		dataSource.setDriverClassName(env.getProperty("spring.hll1-datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.hll1-datasource.url"));
		dataSource.setUsername(env.getProperty("spring.hll1-datasource.username"));
		dataSource.setPassword(env.getProperty("spring.hll1-datasource.password"));

		System.out.println("## DataSource hhl1: " + dataSource);
		return dataSource;
	}

	@Bean(name = "dataSourceHll2")
	public DataSource getDataSource2() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		// See: datasouce-cfg.properties
		dataSource.setDriverClassName(env.getProperty("spring.hll2-datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.hll2-datasource.url"));
		dataSource.setUsername(env.getProperty("spring.hll2-datasource.username"));
		dataSource.setPassword(env.getProperty("spring.hll2-datasource.password"));

		System.out.println("## DataSource hhl2: " + dataSource);

		return dataSource;
	}
}
