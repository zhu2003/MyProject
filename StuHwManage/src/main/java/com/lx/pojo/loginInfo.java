package com.lx.pojo;

public class loginInfo {
    private String status;//登录状态
    private String path;
    private String name;
    private Integer id;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "loginInfo{" +
                "status=" + status +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
