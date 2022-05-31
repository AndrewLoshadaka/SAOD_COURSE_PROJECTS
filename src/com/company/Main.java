package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import doctor.*;
import hash_table.Patient;
import hash_table.HashTable;

//316401
public class Main {
    private final AVLTree tree = new AVLTree();
    private final HashTable hashTable = new HashTable();
    private final SkipList<Referral> listOfReferral = new SkipList<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.testPatient();
        main.testDoctor();
        main.mainMenu();

    }

    public void mainMenu() throws IOException{
        System.out.println("\t\tМеню");
        System.out.println("/----------------------------------\\");
        System.out.println("| 1 | Параметры работы с врачами   |");
        System.out.println("| 2 | Параметры работы с пациентами|");
        System.out.println("| 3 | Меню для работы с записями   |");
        System.out.println("| 0 | Выход                        |");
        System.out.println("\\----------------------------------/");
        System.out.println("Введите номер команды");
        String command = reader.readLine();
        while (Integer.parseInt(command) > 4 || !checkInt(command)){
            System.out.println("Повторите ввод");
            command = reader.readLine();
        }
        switch (command){
            case "1" -> menuDoctor();
            case "2" -> menuPatient();
            case "3" -> menuAppointment();
            case "0" -> System.exit(1);
        }
        mainMenu();
    }

    public void titleMenuOfAppointment(){
        System.out.println("/----------------------------------------------\\");
        System.out.println("| 1 | Оформить для зарегистрированного пациента|");
        System.out.println("| 2 | Оформить для нового пациента             |");
        System.out.println("| 3 | Оформить возврат направления             |");
        System.out.println("| 4 | Все направления                          |");
        System.out.println("| 0 | Выход                                    |");
        System.out.println("\\----------------------------------------------/");
        System.out.println("Введите номер команды");
    }

    public void menuAppointment() throws IOException{
        titleMenuOfAppointment();
        String command1 = reader.readLine();
        while (Integer.parseInt(command1) > 4 || !checkInt(command1)){
            System.out.println("Повторите ввод");
            command1 = reader.readLine();
        }
        String regex = "0";
        switch (command1){
            case "1" -> {
                hashTable.showTable();
                System.out.println("Введите регистрационный номер: ");
                regex = reader.readLine();
                while (regex.length() != 9) {
                    System.out.println("Некорректный ввод!");
                    regex = reader.readLine();
                }
                menuForCreateNewAppointment(HashTable.find(regex));
            }
            case "2" -> {
                regex = createNumber();
                while (hashTable.checkRepeatOfHash(regex)){
                    regex = createNumber();
                }
                hashTable.insert(addPatient(regex));
                menuForCreateNewAppointment(HashTable.find(regex));
            }
            case "3" -> {
                System.out.println("Все направления:");
                listOfReferral.print();
                System.out.println("\nВсе записи:");
                hashTable.showTable();
                System.out.println("Введите регистрационный номер: ");
                String regex1 = reader.readLine();
                if(HashTable.find(regex1) == null)
                    System.out.println("Запись не существует или удалена!");
                else {
                    Patient.showInform(HashTable.find(regex1));
                    if (listOfReferral.find(regex1) == null)
                        System.out.println("Список направлений пуст");
                    else {
                        int i = 1;
                        for (Referral x : listOfReferral.find(regex1)) {
                            System.out.println(i + ") " + x.getNameOfDoctor() + " " + x.getTime());
                            i++;
                        }
                        System.out.println("Введите номер направления из списка: ");
                        int number = Integer.parseInt(reader.readLine()) - 1;
                        Referral referral = listOfReferral.find(regex1).get(number);
                        Doctor doctor = tree.find(referral.getNameOfDoctor());
                        DayOfTheWeek currentDay = doctor.getWeek().getDay(referral.getDate()); //текущий день недели для данного врача
                        currentDay.returnAppointment(referral.getTime()); //удаление времени
                        listOfReferral.remove(referral.getDate(), referral.getTime(), referral.getRegexNumber());
                    }
                }
            }
            case "4" -> listOfReferral.print();
            case "0" -> mainMenu();
        }
        menuAppointment();
    }

    // TODO: ПРОВЕРКИ
    // TODO: УДАЛЕНИЕ НАПРАВЛЕНИЯ, ПОИСК ПО НЕСУЩЕСТВУЮЩЕМУ ВРАЧУ, ПОИСК ДЛЯ НЕСУЩЕСТВУЮЩЕГО ПАЦИЕНТА

    public void menuDoctor() throws IOException {
        System.out.println();
        System.out.println("Меню для работы с докторами");
        menu();
        String command = reader.readLine();
        while (Integer.parseInt(command) > 6 || !checkInt(command)){
            System.out.println("Повторите ввод");
            command = reader.readLine();
        }
        switch (command) {
            case "1" -> addDoctor();
            case "2" -> {
                System.out.println("Введите ФИО");
                String name = reader.readLine();
                if(tree.find(name) == null)
                    System.out.println("Врача с таким именем нет!");
                else {
                    if(listOfReferral.findListOfPatient(name).size() != 0)
                        System.out.println("Нельзя удалить данные о враче, так как к нему есть запись(и)!");
                    else
                        tree.delete(name);
                }
            }
            case "3" -> tree.print();
            case "4" -> {
                System.out.println("Ключ поиска - ФИО");
                String name = reader.readLine();
                if(tree.find(name) == null)
                    System.out.println("Врача с таким именем нет!");
                else {
                    tree.printInformAboutDoctor(tree.find(name));
                    if (listOfReferral.findListOfPatient(name) == null)
                        System.out.println("Записей нет");
                    else {
                        System.out.println("Записи к " + name);
                        for (Referral x : listOfReferral.findListOfPatient(name)) {
                            Patient patient = HashTable.find(x.getRegexNumber());
                            assert patient != null;
                            System.out.println(x.getDay() + " " + x.getTime() + " " + patient.getRegexNumber() + " " + patient.getName());
                        }
                    }
                }
            }
            case "5" -> {
                System.out.println("Параметр для поиска - должность");
                String name = reader.readLine();
                if(tree.contains(name).size() == 0)
                    System.out.println("Отсутствуют врачи с такой должностью");
                else
                    tree.printListOfDoctor(tree.contains(name));
            }
            case "6" -> {
                tree.clearTree();
                System.out.println("Дерево очищено!");
            }
            case "0" -> mainMenu();
        }
        menuDoctor();
    }

    private void menu() {
        System.out.println("/--------------------------\\");
        System.out.println("| 1 | Добавить новую запись|");
        System.out.println("| 2 | Удалить данные       |");
        System.out.println("| 3 | Просмотр списка      |");
        System.out.println("| 4 | Поиск по ключу       |");
        System.out.println("| 5 | Поиск по параметру   |");
        System.out.println("| 6 | Очистить список      |");
        System.out.println("| 0 | Выход                |");
        System.out.println("\\--------------------------/");
        System.out.println("Введите номер команды");
    }

    public void menuPatient() throws IOException{
        System.out.println();
        System.out.println("Меню для работы с пациентами");
        menu();
        String command = reader.readLine();
        while (Integer.parseInt(command) > 6 || !checkInt(command)){
            System.out.println("Повторите ввод");
            command = reader.readLine();
        }
        switch (command){
            case "1" -> hashTable.insert(addPatient(createNumber()));
            case "2" -> {
                hashTable.showTable();
                System.out.println("Введите регистрационный номер для удаления!");
                String number = reader.readLine();
                if(!checkRegexNumber(number))
                    System.out.println("Некорректный регистрациооный номер");
                if(HashTable.find(number) == null)
                    System.out.println("Нет пациента с таким рег. номером");
                else {
                    if (HashTable.find(number).isCheckAppointment())
                        System.out.println("Нельзя удалить этого пациента, так как он имеет запись к врачу!");
                    else
                        System.out.println(hashTable.delete(number));
                }
            }
            case "3" -> hashTable.showTable();
            case "4" -> {
                System.out.println("Ключ поиска - рег. номер. Введите:");
                String number = reader.readLine();
                if (HashTable.find(number) == null)
                    System.out.println("Нет пациента с таким рег. номером");
                else {
                    Patient.showInform(HashTable.find(number));
                    if (listOfReferral.find(number) == null)
                        System.out.println();
                    else
                        for (Referral x : listOfReferral.find(number)) {
                            System.out.println(x.getDay() + " " + x.getTime() + " " + x.getNameOfDoctor());
                        }
                }
            }
            case "5" -> {
                System.out.println("Параметр для поиска - ФИО");
                ArrayList<Patient> list = hashTable.findByName(reader.readLine());
                if(list.size() == 0)
                    System.out.println("Список пуст!");
                else
                    for(Patient patient : list)
                        Patient.showInform(patient);
            }
            case "6" -> {
                if(hashTable.checkTable()) {
                    hashTable.clear();
                    System.out.println("Список очищен!");
                }
                else
                    System.out.println("Нельзя очистить список больных!");
            }
            case "0" -> mainMenu();
        }
        menuPatient();
    }

    public void menuForCreateNewAppointment(Patient patient) throws IOException{
        HashMap<String, String> week = new HashMap<>();
        week.put("1", "понедельник: ");
        week.put("2", "вторник: ");
        week.put("3", "среду: ");
        week.put("4", "четверг: ");
        week.put("5", "пятницу: ");
        System.out.println("Выбор врача");
        tree.showTree();
        System.out.println("Введите должность: ");
        String post = reader.readLine();
        while (tree.contains(post).size() == 0) {
            System.out.println("Не найдено врачей по такой должности!\nПовторите ввод");
            post = reader.readLine();
        }
        tree.printListOfDoctor(tree.contains(post));
        System.out.println("Введите номер в списке: ");
        String number = reader.readLine();
        Doctor doctor = tree.findByNumber(number, post);
        tree.printInformAboutDoctor(doctor);
        System.out.println("/----------------\\");
        System.out.println("| 1 | Понедельник|");
        System.out.println("| 2 | Вторник    |");
        System.out.println("| 3 | Среда      |");
        System.out.println("| 4 | Четверг    |");
        System.out.println("| 5 | Пятница    |");
        System.out.println("\\----------------/");
        System.out.println("Введите номер дня недели для записи");
        String day = reader.readLine();
        while (Integer.parseInt(day) > 5 || !checkInt(day)){
            System.out.println("Повторите ввод");
            day = reader.readLine();
        }
        System.out.println("Запись на " + week.get(day));
        tree.find(doctor.getName()).showListOfFreeTime(day);
        System.out.println("Введите время записи:");
        String position = reader.readLine();
        String time = tree.findByNumber(number, post).
                getWeek().
                getDay(day).
                getTime(position);
        while (!listOfReferral.checkTimeOfPatient(patient.getRegexNumber(), day, time)){
            System.out.println("На это время уже есть запись! Повторите ввод:");
            position = reader.readLine();
            time = tree.findByNumber(number, post).
                    getWeek().
                    getDay(day).
                    getTime(position);
        }
        Referral referral = new Referral(patient.getRegexNumber(),
                doctor.getName(), day, time); //в список
        System.out.println("Сохранить запись?");
        System.out.println("/-----------------\\");
        System.out.println("| 1 | Сохранить   |");
        System.out.println("| 0 | Не сохранять|");
        System.out.println("\\-----------------/");
        switch (reader.readLine()){
            case "1" -> {
                listOfReferral.insert(referral);
                System.out.println("Запись сохранена!");
                doctor.createNewAppointment(day, time, patient);
                patient.setCheckAppointment(true);
            }
            case "2" -> System.out.println("Запись не сохранена!");
        }
    }

    public void testDoctor(){
        tree.insert(new Doctor("Тремор", "Невролог", 13, "09:00-15:00", new Week()));
        tree.insert(new Doctor("Костоправ", "Хирург", 14, "09:00-15:00",new Week()));
        tree.insert(new Doctor("Лейтенант Роговец", "Терапевт", 15, "09:00-15:00", new Week()));
        tree.insert(new Doctor("Дядька Яр", "Окулист", 16, "09:00-15:00", new Week()));
        tree.insert(new Doctor("Сидорович", "Врач общей практики", 17, "09:00-15:00", new Week()));
        tree.insert(new Doctor("Бармен", "Врач общей практики", 18, "09:00-15:00", new Week()));
    }

    public void addDoctor() throws IOException{
        String name, post, schedule;
        System.out.println("Введите имя");
        name = reader.readLine();
        String room;
        while (tree.checkDuplicateKey(name)){
            System.out.println("Повторите ввод!");
            name = reader.readLine();
        }
        System.out.println("Введите должность");
        post = reader.readLine();
        System.out.println("Введите номер кабинета");
        room = reader.readLine();
        while (Integer.parseInt(room) > 1000 || !checkInt(room)){
            System.out.println("Повторите ввод");
            room = reader.readLine();
        }
        schedule = "09:00-15:00";
        tree.insert(new Doctor(name, post, Integer.parseInt(room), schedule, new Week()));
        System.out.println("Запись добавлена!");
    }

    public boolean checkInt(String data) {
        try {
            Integer.parseInt(data);
            return Integer.parseInt(data) >= 0;
        } catch (NumberFormatException ignored) {
        }
        return true;
    }

    public void testPatient(){
        for(int i = 0; i < 20; i++) {
            int a = (int) ((Math.random() * 89) + 10);
            int b = (int) ((Math.random() * 899999) + 100000);
            hashTable.insert((new Patient(a + "-" + b, "Меченый" + i, 1978, "Проспект Маршала Жукова 24", "НИИ Агропром")));
        }
    }

    public Patient addPatient(String number) throws IOException{
        String name, year, address, work;
        System.out.println("Введите имя");
        name = reader.readLine();
        System.out.println("Введите год рождения");
        year = reader.readLine();
        while (Integer.parseInt(year) > 2022 || Integer.parseInt(year) < 1910 || !checkInt(year)){
            System.out.println("Повторите ввод");
            year = reader.readLine();
        }
        System.out.println("Введите адрес");
        address = reader.readLine();
        System.out.println("Введите место работы");
        work = reader.readLine();
        return new Patient(number, name, Integer.parseInt(year), address, work);
    }

    public String createNumber(){
        int a = (int) ((Math.random() * 89) + 10);
        int b = (int) ((Math.random() * 899999) + 100000);
        return a + "-" + b;
    }

    public boolean checkRegexNumber(String number){
        return number.length() == 9;
    }
}
