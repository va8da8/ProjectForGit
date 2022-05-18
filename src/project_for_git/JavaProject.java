package project_for_git;

import java.util.LinkedList;


//Данная программа записывает сотрудников в БД и в файл. Из БД можно удалять и просматривать актуальных сотрудников с
//уникальным табелем.
//В файл происходит только добавление сотрудников. Файл содержит всех сотрудников(не удаляет).
class JavaProject {

    public static void main(String... args) throws Exception {

        String str;
        EmployeeManagement em = new EmployeeManagement();
        FileHandler fh = new FileHandler();
        LinkedList<Employee>employees = fh.readFile();
        int choice = em.getChoice();


        //Цикл while раз за разом предлагает пользователю выбрать вариант.
        while (choice !=-1)
        {
            switch (choice) {
                case 1 -> {
                    str = em.addEmployees(employees);
                    fh.appendFile(str);
                }
                case 2 -> em.removeEmployee();

                case 3 ->em.printEmployeeInfo();
                default -> System.err.print(""" 
                        
                        Вы выбрали неверный вариант 
                        
                        """);
            }
            //Предложить пользователю ввести новый вариант.
            choice = em.getChoice();
        }
        System.out.println("\nПрограмма закрыта");
    }
}
