package project_for_git;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

//Этот класс реализует операции добавления и удаления сотрудников.
//Также присутствует метод для вывода информации о сотруднике.
class EmployeeManagement {

    //имя БД
    private static final String DB_USERNAME="";
    //пароль БД
    private static final String DB_PASSWORD="";
    //адрес для подключения БД
    private static final String DB_URL="";
    //соединяем наше приложение с БД
    private static Connection connection=null;
    //получение данных от пользователя
    final private Scanner reader = new Scanner(System.in);
    //
    private Statement statement;
    //SQL запрос
    private String sql;
    //
    private ResultSet resultSet;
    //
    private int userID;
    //
    private int age;


    //Метод для запроса у пользователя значение int.
    private int getIntInput() {
        int choice=0;
        //Цикл while раз за разом предлагает пользователю ввести целое число.
        while (choice==0) {
            try {
                //Метод для получения целого числа.
                choice = reader.nextInt();
                //Если пользователь вводит недопустимое число, программа должна выдать исключение InputMismatchException.
                //Это необходимо, потому что, если пользователь вводит недопустимое число, цикл while продолжит выполняться.
                //В этом случае будет выполнен блок catch, чтобы пользователю было предложено ввести новое значение.
                if (choice==0) throw new InputMismatchException();
                //После выдачи исключения, остается добавить команду reader.nextLine() в блок try. Это необходимо для
                //чтения символа новой строки который не поглощается методом nextInt()
                reader.nextLine();
            } catch (InputMismatchException e) {
                //Читаем весь ввод, который не был поглощен ранее, так как при неудачном выполнении блока try данные,
                //введенные пользователем, не будут полностью поглощены.
                reader.nextLine();
                System.err.print("\nОШИБКА: НЕВЕРНЫЙ ВВОД. Пожалуйста, попробуйте еще раз: ");
            }
        }
        return choice;
    }


    //Метод выводит текста:
    int getChoice() {
        int choice;
        System.out.println("\nДОБРО ПОЖАЛОВАТЬ ");
        System.out.println("======================");
        System.out.println("1) Добавить сотрудника");
        System.out.println("2) Удалить сотрудника");
        System.out.println("3) Показать информацию о сотруднике");
        System.out.print("\nДля выхода введите -1: ");
        //получения данных от пользователя
        choice = getIntInput();
        return choice;
    }


    //Метод получает коллекцию LinkedList и БД, добавляет в них данные нового сотрудника.
    String addEmployees(LinkedList<Employee> em) throws SQLException, IOException {

        String name;
        String surname;
        String mem;
        Employee emp;

        //Вычисление идентификатора сотрудника
        if (em.size() > 0)
            //Проверяем, не пуста ли коллекция LinkedList. Если не пуста, увеличиваем на 1.
            userID = em.getLast().getUserID()+1;

        else if(em.size() == 0) {

            Formatter fmt = new Formatter();
            String str=String.valueOf(fmt.format("%2s %20s %20s %20s","userID", "name", "surname", "age")+"\n");
            FileOutputStream fos=new FileOutputStream("employee.txt");
            byte[]b=str.getBytes(StandardCharsets.UTF_8);
            fos.write(b);
            userID = 1;

            try{
                //System.out.println("\nСоединение с БД");
                connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
                //SQL запрос для добавления сотрудника в БД.
                sql="CREATE TABLE employee(userID INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(20),surname VARCHAR(20),age int);";

                statement=connection.createStatement();
                //executeUpdate производит запись в БД
                statement.executeUpdate(sql);
            }catch (Exception e){
                System.err.println("\nОшибка соединения с БД");
            }finally {
                connection.close();
                //System.out.println("\nСоединение с БД закрыто");
            }
        }

        System.out.print("\nПожалуйста, введите имя сотрудника: ");
        name = reader.nextLine();
        //Приведение к верхнему регистру.
        name=name.toUpperCase();
        //Проверка на ввод только на русском.
        do {
            try {
                if (!name.matches("([А-Яа-я]+)")) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.err.println("Введите имя на русском");
                name = reader.nextLine();
                name=name.toUpperCase();
            }
        }while (!name.matches("([А-Яа-я]+)"));

        System.out.print("\nПожалуйста, введите фамилию сотрудника: ");
        surname = reader.nextLine();
        surname=surname.toUpperCase();
        do {
            try {
                if (!surname.matches("([А-Яа-я]+)")) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.err.println("Введите фамилию на русском");
                surname = reader.nextLine();
                surname=surname.toUpperCase();
            }
        }while (!surname.matches("([А-Яа-я]+)"));

        System.out.print("\nПожалуйста, введите возраст сотрудника: ");
        while (!reader.hasNextInt()) {
            System.err.println("Введите число");
            reader.next();
        }
        age=reader.nextInt();

        try{
            //System.out.println("\nСоединение с БД");
            connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            //SQL запрос для добавления сотрудника в БД.
            sql=String.format("INSERT INTO employee(name, surname,age)VALUES('%s', '%s',%s)",name,surname,age);

            statement=connection.createStatement();
            //executeUpdate производит запись в БД
            statement.executeUpdate(sql);

        }catch (Exception e){
            System.err.println("\nОшибка соединения с БД");
        }finally {
            connection.close();
            //System.out.println("\nСоединение с БД закрыто");
        }

        //Добавление нового сотрудника
        emp = new Employee(userID,name, surname, age);
        //добавляется в коллекцию LinkedList еm методом add().
        em.add(emp);
        //Метод генерирует строку, представляющую нового сотрудника.
        mem = emp.toString();
        System.out.println("\nДобавлен один сотрудник\n");
        return mem;
    }


    //Этот метод удаляет данные сотрудника из БД.
    void removeEmployee() throws SQLException {

        System.out.print("\nВведите табель сотрудника, чтобы удалить:");

        //Читает введенное значение и присваивает его переменной
        userID = getIntInput();

        try{
            //System.out.println("Соединение с БД");
            connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            sql=String.format("delete from employee where userID=%s;",userID);

            statement=connection.createStatement();
            //Удаляем строку которая равна userID из 1 колонки.
            statement.executeUpdate(sql,1);

            System.out.print("\nСотрудник удален\n");
        }catch (Exception e){
            System.err.println("\nОшибка соединения с БД");
        }finally {
            connection.close();
            //System.out.println("\nСоединение с БД закрыто");
        }
        System.out.println("\nТабель сотрудника не найден\n");
    }


    //Получения информации о конкретном сотруднике по табелю.
    void printEmployeeInfo() throws SQLException {

        System.out.print("\nВведите табель сотрудника для отображения информации: ");

        userID = getIntInput();

        try {
            //System.out.println("\nСоединение с БД");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            sql = String.format("select * from employee where userID=%s;", userID);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("\n\nТабель сотрудника   " + resultSet.getInt(1));
                System.out.println("Имя сотрудника      " + resultSet.getString(2));
                System.out.println("Фамилия сотрудника  " + resultSet.getString(3));
                System.out.println("Возраст сотрудника  " + resultSet.getInt(4));
            }
        }catch (Exception e) {
            System.err.println("\nОшибка соединения с БД");
        } finally {
            connection.close();
            //System.out.println("\nСоединение с БД закрыто");
        }
        System.out.println("\nТабель сотрудника не найден\n");
    }
}
