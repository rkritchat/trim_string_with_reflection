package com.rkritchat.trimstring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class TrimString {

    private static final String GET = "get";
    private static final String SET = "set";

    public static void main(String[] args) {
        Student st = new Student("1 ","   Krit chat ", "Rojanaphruk     ", 12);
        Teacher tc = new Teacher("1"," rk rith cat   ", "t eacher");
        trimStringWithReflection(st);
        trimStringWithReflection(tc);
        System.out.println(st);
        System.out.println(tc);

    }
    private static void trimStringWithReflection(Object obj){
        //Get all method inside object
        Class clazz = obj.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        Stream.of(declaredMethods).forEach(e->{
            //Check method start with get for get getter method
            if (e.getName().startsWith(GET)) {
                try {
                    Method getterMethod = clazz.getDeclaredMethod(e.getName());
                    Object invoke = getterMethod.invoke(obj);

                    //Check result returned is type String or not
                    if(invoke instanceof String){
                        //get method setter
                        Method setterMethod = clazz.getDeclaredMethod(e.getName().replace(GET,SET), String.class);
                        //invoke method setter with pass value that trim already
                        setterMethod.invoke(obj, invoke.toString().trim());
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}


class Student{
    private String stId;
    private String stName;
    private String stLastName;
    private int age;

    public Student(String stId, String stName, String stLastName, int age) {
        this.stId = stId;
        this.stName = stName;
        this.stLastName = stLastName;
        this.age = age;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStLastName() {
        return stLastName;
    }

    public void setStLastName(String stLastName) {
        this.stLastName = stLastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "com.rkritchat.trimstring.Student{" +
                "stId='" + stId + '\'' +
                ", stName='" + stName + '\'' +
                ", stLastName='" + stLastName + '\'' +
                '}';
    }
}

class Teacher{
    private String tcId;
    private String tcName;
    private String tcLastName;

    public Teacher(String tcId, String tcName, String tcLastName) {
        this.tcId = tcId;
        this.tcName = tcName;
        this.tcLastName = tcLastName;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getTcLastName() {
        return tcLastName;
    }

    public void setTcLastName(String tcLastName) {
        this.tcLastName = tcLastName;
    }

    @Override
    public String toString() {
        return "com.rkritchat.trimstring.Teacher{" +
                "tcId='" + tcId + '\'' +
                ", tcName='" + tcName + '\'' +
                ", tcLastName='" + tcLastName + '\'' +
                '}';
    }
}