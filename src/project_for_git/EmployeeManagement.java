package project_for_git;


import java.sql.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


//Этот класс реализует операции добавления и удаления сотрудников.
//Также присутствует метод для вывода информации о сотруднике.
class EmployeeManagement {


    //получение данных от пользователя
    final private Scanner reader = new Scanner(System.in);
    //
    private int userID;
    //
    private int age;
    //
    private static final String STATUS="РАБОТАЕТ";
    //создаем объект класса
    QuerySQL sql=new QuerySQL();


    //Метод для запроса у пользователя значение int.
    private int getIntInput() {
        int choice=0;
        choice=number(choice);
        return choice;
    }


    //Метод выводит текста:
    int getChoice() {
        int choice;
        System.out.println("\nДОБРО ПОЖАЛОВАТЬ ");
        System.out.println("======================");
        System.out.println("1) Добавить сотрудника");
        System.out.println("2) Уволить сотрудника");
        System.out.println("3) Показать информацию о сотруднике");
        System.out.print("\nДля выхода введите 4: ");
        //получения данных от пользователя
        choice =getIntInput();
        return choice;
    }


    String addEmployees(LinkedList<Employee> em) throws SQLException{

        String name;
        String surname;
        String mem;
        Employee emp;

        if(em.size()==0) {

            userID = 1;

            sql.createDatabase();
            sql.createTable();
        }

        //Вычисление идентификатора сотрудника
        if (em.size()>0) {
            //Проверяем, не пуста ли коллекция LinkedList. Если не пуста, увеличиваем на 1.
            userID = em.getLast().getUserID() + 1;
        }

        System.out.print("\nПожалуйста, введите имя сотрудника: ");
        //Приведение к верхнему регистру.
        name = reader.nextLine().toUpperCase();
        //Проверка на ввод только на русском.
        name=title(name);

        System.out.print("\nПожалуйста, введите фамилию сотрудника: ");
        surname = reader.nextLine().toUpperCase();
        surname =title(surname);

        System.out.print("\nПожалуйста, введите возраст сотрудника: ");
        age=number(age);

        sql.addEmployees(userID,name,surname,STATUS,age);

        //Добавление нового сотрудника
        emp = new Employee(userID,name, surname,STATUS, age);
        //добавляется в коллекцию LinkedList еm методом add().
        em.add(emp);
        //Метод генерирует строку, представляющую нового сотрудника.
        mem = emp.toString();

        System.out.println("\nДобавлен один сотрудник\n");
        return mem;
    }


    //Метод для проверки на ввод только на русском.
    private String title(String s){
        do {
            try {
                if (!s.matches("([А-Яа-я]+)")) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.err.println("Введите на русском");
                s = reader.nextLine().toUpperCase();
            }
        }while (!s.matches("([А-Яа-я]+)"));
        return s;
    }


    //Метод для проверки на ввод только натуральных чисел.
    private int number(int i){
        while (i<=0) {
            try {
                i = reader.nextInt();
                if (i <= 0) throw new InputMismatchException();
                reader.nextLine();
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.err.println("Введите натуральное число");
            }
        }
        return i;
    }


    //Этот метод меняет статус сотрудника.
    void removeEmployee(LinkedList<Employee> em) throws SQLException {

        System.out.print("\nВведите табель сотрудника, чтобы уволить:");

        //Читает введенное значение и присваивает его переменной
        userID = getIntInput();

        sql.removeEmployee(userID);

        for (Employee employee : em) {
            //команда if сравнивает ID каждого элемента
            //с идентификатором, введенным пользователем:
            if (employee.getUserID() == userID) {
                //метод m.remove(i)используется для удаления
                //элемента с индексом i из LinkedList.
                employee.setStatus();
                System.out.print("\nСотрудник уволен\n");
                return;
            }
        }
        System.out.println("\nТабель сотрудника не найден\n");
    }


    //Получения информации о конкретном сотруднике по табелю из БД.
    void printEmployeeInfo() throws SQLException {

        System.out.print("\nВведите табель сотрудника для отображения информации: ");

        userID = getIntInput();

        sql.printEmployeeInfo(userID);

        System.out.println("\nТабель сотрудника не найден\n");
    }
}