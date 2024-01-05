package com.lx.pojo;

public class User_Cscore {
    private int stuId;
    private String stuName;
    private int stuScore;
    public int getStuId() {
        return stuId;
    }
    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getStuScore() {
        return stuScore;
    }

    public void setStuScore(int stuScore) {
        this.stuScore = stuScore;
    }

    @Override
    public String toString() {
        return "User_Cscore{" +
                "stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", stuScore=" + stuScore +
                '}';
    }
}
