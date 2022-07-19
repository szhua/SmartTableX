package com.fencer.lock;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable(name="用户信息列表")
public class UserInfo {


    @SmartColumn(id =1,name = "姓名",fixed = true)
    private String name;
    @SmartColumn(id=2,name="年龄")
    private int age;

    @SmartColumn(id =3,name = "姓名1")
    private String name1;
    @SmartColumn(id=4,name="年龄1")
    private int age1;


    @SmartColumn(id =5,name = "姓名2")
    private String name2;
    @SmartColumn(id=6,name="年龄2")
    private int age2;

    private String riverName ;


    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getAge1() {
        return age1;
    }

    public void setAge1(int age1) {
        this.age1 = age1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
    }
}
