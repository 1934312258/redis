package com.kevin.reidstest.entity;

/**
 * <p>
 *
 * </p>
 *
 * @author zhaowenjian
 * @since 2021/7/2 13:24
 */
public class People {

    private String name;
    private Long id;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public People(){}

    public People(String name, Long id){
        this.id=id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
