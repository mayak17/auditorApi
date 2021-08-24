package ru.vstu.AuditorApi.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import ru.vstu.AuditorApi.services.SemesterService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//Специальный класс, который подсовывется в главном классе спрингу
//в качестве источника данных(БД).
//
//Класс смотрит в http-сессию на выбранный семестр и в зависимости от семестра
//возвращает нужную БД. Дальше спринг как обычно использует ту БД, которую подсунул этот класс

public class DatasourceRouter extends AbstractRoutingDataSource {

    @Autowired
    protected SemesterService semesterService;

    /**
     * Посмотреть в сессию и вернуть код семестра,
     * который отвечает за выбор БД
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return this.semesterService.getActiveSemesterCode();
    }

    /**
     * Сложить подключения к разным БД в Map и дать ключи в виде кодов семестров
     * @param dataSource1
     * @param dataSource2
     */
    public void initDataSources(DataSource dataSource1, DataSource dataSource2) {
        Map<Object, Object> dsMap = new HashMap<Object, Object>();
        dsMap.put(Constants.hll1Code, dataSource1);
        dsMap.put(Constants.hll2Code, dataSource2);

        this.setTargetDataSources(dsMap);
    }
}
