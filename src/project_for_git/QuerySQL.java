package project_for_git;

import java.sql.*;


class QuerySQL {


    private static final String DB_USERNAME = "";
    private static final String DB_PASSWORD = "";
    //Я указываю, что SSL не будет использоваться и что часовым поясом будет московский часовой пояс.
    private static final String DB_URL_START = "jdbc:mysql://localhost:3306/mysql?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee?serverTimezone=Europe/Moscow&useSSL=false";
    //соединяем наше приложение с БД
    private static Connection connection = null;
    //
    private Statement statement;
    //SQL запрос
    private String sql;


    public void createDatabase() throws SQLException {
        try{
            //System.out.println("\nСоединение с БД");
            connection= DriverManager.getConnection(DB_URL_START,DB_USERNAME,DB_PASSWORD);
            //SQL запрос для создания БД.
            sql="CREATE DATABASE employee;";

            statement=connection.createStatement();
            //executeUpdate производит запись в БД
            statement.executeUpdate(sql);
            //Подключаемся к БД
            sql="USE employee;";

            statement=connection.createStatement();
            //executeUpdate производит запись в БД
            statement.executeUpdate(sql);
        }catch (Exception e){
            System.err.println("\nОшибка соединения с БД");
        }finally {
            if(connection!=null)
            connection.close();
//            //System.out.println("\nСоединение с БД закрыто");
        }
    }



    public void createTable() throws SQLException {
        try{
            //System.out.println("\nСоединение с БД");
            connection= DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            //SQL запрос для добавления сотрудника в БД.
            sql="CREATE TABLE employee(userID INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(20),surname VARCHAR(20),status VARCHAR(10),age int);";

            statement=connection.createStatement();
            //executeUpdate производит запись в БД
            statement.executeUpdate(sql);
        }catch (Exception e){
            System.err.println("\nОшибка соединения с БД");
        }finally {
            if(connection!=null)
            connection.close();
//            //System.out.println("\nСоединение с БД закрыто");
        }
    }


    public void addEmployees(int userID, String name, String surname,String status, int age)throws SQLException{
        try{
            //System.out.println("\nСоединение с БД");
            connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            //SQL запрос для добавления сотрудника в БД.
            sql=String.format("INSERT INTO employee(name,surname,status,age)VALUES('%s', '%s', '%s', '%s')",name,surname,status,age);

            statement=connection.createStatement();
            //executeUpdate производит запись в БД
            statement.executeUpdate(sql);

        }catch (Exception e){
            System.err.println("\nОшибка соединения с БД");
        }finally {
            if(connection!=null)
            connection.close();
//            //System.out.println("\nСоединение с БД закрыто");
        }
    }


    public void printEmployeeInfo(int userID) throws SQLException {
        try {
            //System.out.println("\nСоединение с БД");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            sql = String.format("select * from employee where userID=%s;", userID);

            statement = connection.createStatement();
            //
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("\n\nТабель сотрудника   " + resultSet.getInt(1));
                System.out.println("Имя сотрудника      " + resultSet.getString(2));
                System.out.println("Фамилия сотрудника  " + resultSet.getString(3));
                System.out.println("Статус сотрудника   " + resultSet.getString(4));
                System.out.println("Возраст сотрудника  " + resultSet.getInt(5));
            }
        }catch (Exception e) {
            System.err.println("\nОшибка соединения с БД");
        } finally {
            if(connection!=null)
            connection.close();
            //System.out.println("\nСоединение с БД закрыто");
        }
    }


    public void removeEmployee(int userID) throws SQLException {
        try{
            //System.out.println("Соединение с БД");
            connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            sql=String.format("UPDATE employee SET status='УВОЛЕН' WHERE userID=%s;",userID);

            statement=connection.createStatement();
            //Увольняем сотрудника userID которого=.
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            System.out.print("\nСотрудник уволен\n");
        }catch (Exception e){
            System.err.println("\nОшибка соединения с БД");
        }finally {
            if(connection!=null)
            connection.close();
            //System.out.println("\nСоединение с БД закрыто");
        }
    }
}