package com.wyd.zmhkmiddleware.business.web.model.test;

/**
 * @author xh
 * @date 2025-01-05
 * @Description:
 */
public class UserEntity {

    public UserEntity() {

    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
