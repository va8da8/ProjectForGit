package project_for_git;


import java.util.Formatter;

//Этот класс содержит основную информацию о сотруднике.
class Employee {

    private int userID;
    private String name;
    private String surname;
    private int age;


    //Внутри конструктора четыре параметра присваиваются соответствующим полям
    Employee(int userID,String name,String surname,int age){

        this.userID=userID;
        this.name=name;
        this.surname=surname;
        this.age=age;
    }


    //Методы set- и get-
    int getUserID() {return userID;}
    void setUserID(int userID) {this.userID = userID;}

    String getName() {return name;}
    void setName(String name) {this.name = name;}

    String getSurname() {return surname;}
    void setSurname(String surname) {this.surname = surname;}

    int getAge() {return age;}
    void setAge(int age) {this.age=age;}


    //Метод возвращает строку с информацией о работнике
    @Override
    public String toString() {
        Formatter fmt = new Formatter();
        String a;
        a= String.valueOf(fmt.format("%6s %20s %20s %20s",userID,name,surname,age));
        return a;

    }
}
