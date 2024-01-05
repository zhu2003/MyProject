package com.lx.pojo;
public class User {
    private int id;
    private String name;
    private String sex;
    private String username;
    private String password;
    private Integer status;
    private String CheckCode;
    //逻辑视图
    public String getStatusStr(){
        if (status == null){
            return "未知";
        }
        return status == 0 ? "未完成":"已完成";
    }
    public String getCheckCode() {
        return CheckCode;
    }

    public void setCheckCode(String checkCode) {
        this.CheckCode = checkCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", CheckCode='" + CheckCode + '\'' +
                '}';
    }
}
