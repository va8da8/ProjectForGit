package project_for_git;


import java.io.*;
import java.util.LinkedList;


//Этот класс записывает и корректирует данные в файле.
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
                splitLine=lineRead.split(" ");
                mem=new Employee(Integer.parseInt(splitLine[0]),splitLine[1],splitLine[2],splitLine[3],Integer.parseInt(splitLine[4]));
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


    //Этот метод добавляет строку к файлу.
    void appendFile(String mem) {
        //Чтобы данные присоединялись к файлу (вместо перезаписи), конструктору передается во втором аргументе передается true.
        try (BufferedWriter writer=new BufferedWriter(new FileWriter("employee.txt", true))) {
            //Для перевода курсора к следующей строке после присоединения mem.
            writer.write(mem + "\n");
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }


    //Этот метод получает параметр LinkedList<Employee> вызывается каждый раз, когда пользователь меняет статус сотрудника.
    void overwriteFile(LinkedList<Employee>em) {
        String s;
        //Создается временный файл employee.temp, в который записываются все данные из LinkedList.
        //Должен перезаписывать все существующие данные в файле, для чего во втором аргументе передается false.
        try(BufferedWriter writer=new BufferedWriter(new FileWriter("employee.temp", false))) {
            //Перебираем элементы переданной коллекции LinkedList.
            for(Employee employee:em) {
                //Для получения строкового представления элемента.
                s=employee.toString();
                writer.write(s + "\n");
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        try {
            //Внутри блока объявляются два объекта File f и tf:
            File f=new File("employee.txt");
            File tf=new File("employee.temp");
            //Метод delete() используется для удаления f,
            f.delete();
            //Метод renameTo() — для переименования tf
            tf.renameTo(f);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

