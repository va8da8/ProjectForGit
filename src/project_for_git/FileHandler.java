package project_for_git;

import java.io.*;
import java.util.LinkedList;

//Этот класс записывает и удаляет данные из файла.
class FileHandler {
    //Открытый метод возвращает коллекцию LinkedList с объектами Employee
    LinkedList<Employee>readFile() {

        LinkedList<Employee>em=new LinkedList<>();
        String lineRead;
        String[] splitLine;
        Employee mem;

        //Блок try с ресурсами используется для создания объекта BufferedReader.
        try(BufferedReader reader=new BufferedReader(new FileReader("employee.txt"))) {
            //Чтения первой строки файла.
            lineRead=reader.readLine();
            //Обрабатывается строка за строкой, пока значение lineRead остается отличным от null.
            while(lineRead!=null) {
                //Используется для разбиения lineRead в массив объектов String по разделителю ", ".
                splitLine=lineRead.split(", ");
                mem=new Employee(Integer.parseInt(splitLine[0]),splitLine[1],splitLine[2],Integer.parseInt(splitLine[3]));
                em.add(mem);
                //Читаем следующую строку, и обновляем переменную lineRead
                lineRead = reader.readLine();
            }
            //Этот блок просто выводит сообщение об ошибке.
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        return em;
    }


    //Этот метод присоединяет новую строку к файлу
    void appendFile(String mem) {
        //Чтобы данные присоединялись к файлу (вместо перезаписи), конструктору передается во втором аргументе передается true.
        try (BufferedWriter writer=new BufferedWriter(new FileWriter("employee.txt", true))) {
            //Для перевода курсора к следующей строке после присоединения mem.
            writer.write(mem + "\n");
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

