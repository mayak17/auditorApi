package ru.vstu.AuditorApi.entities;

import lombok.Data;

//Сущность берется не из базы данных
//Список семестров заполняется в сервисе
@Data
public class Semester {

    private String name;
    private String code;
    private String dbName;
    private Boolean active;

    public Semester(){
        this.setActive(false);
    }

    public Semester(String name, String code, String dbName) {
        this.setActive(false);
        this.name = name;
        this.code = code;
        this.dbName = dbName;
    }

}
