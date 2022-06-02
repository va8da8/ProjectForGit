package project_for_git;


//Этот класс содержит основную информацию о сотруднике.
class Employee {


    private int userID;
    private String name;
    private String surname;
    private String status;
    private int age;


    //Внутри конструктора четыре параметра присваиваются соответствующим полям
    Employee(int userID,String name,String surname,String status,int age){

        this.userID=userID;
        this.name=name;
        this.surname=surname;
        this.status=status;
        this.age=age;
    }


    //Методы set- и get-
    int getUserID() {return userID;}
    void setUserID(int userID) {this.userID = userID;}

    String getName() {return name;}
    void setName(String name) {this.name = name;}

    String getSurname() {return surname;}
    void setSurname(String surname) {this.surname = surname;}

    String getStatus() {return status;}
    void setStatus() {this.status = "УВОЛЕН";}

    int getAge() {return age;}
    void setAge(int age) {this.age=age;}


    @Override
    public String toString() {
        return  userID+" "+name+" "+surname+" "+status+" "+age;
    }
}

