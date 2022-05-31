package com.company;

import java.util.ArrayList;
import java.util.Locale;

public class SkipList <T>{
    private Node head;

    int size;

    public SkipList() {
        head = null;
        size = 0;
    }

    public void insert(Referral data){
        Node element = new Node();
        element.data = data;
        if(head != null){
            var temp = head;
            while (temp.down != null)
                temp = temp.down;
            while (temp.next != null)
                temp = temp.next;
            temp.next = element;
            temp.next.data = data;
        }
        else {
            head = element;
            head.data = data;
        }
        size++;
        sort();
    }


    public void print(){
        if (size == 0){
            System.out.println("Список записей пуст!");
        }
        else if(size == 1){
            Referral referral = head.data;
            System.out.println(referral.getNameOfDoctor() + " " + referral.getDay() + " " + referral.getTime() + " " + referral.getRegexNumber());
        }
        else {
            var temp = head;
            while (temp != null){
                Referral referral = temp.data;
                System.out.println(referral.getNameOfDoctor() + " " + referral.getDay() + " " + referral.getTime() + " " + referral.getRegexNumber());
                temp = temp.next;
            }
        }
    }

    // TODO: поиск списка пациентов
    public ArrayList<Referral> findListOfPatient(String name){
        ArrayList<Referral> list = new ArrayList<>();
        if(size == 0)
            return null;
        else{
            var temp = head;
            name = name.toLowerCase(Locale.ROOT);
            while (temp != null){
                String nameDoctor = temp.data.getNameOfDoctor().toLowerCase(Locale.ROOT);
                if(nameDoctor.equals(name))
                    list.add(temp.data);
                temp = temp.next;
            }
        }
        return list;
    }

    // TODO: поиск списка врачей
    public ArrayList<Referral> find(String regexNumber){
        ArrayList<Referral> list = new ArrayList<>();
        if (size == 0)
            return null;
        else{
            var temp = head;
            while (temp != null){
                if(temp.data.getRegexNumber().equals(regexNumber))
                    list.add(temp.data);
                temp = temp.next;
            }
        }
        return list;
    }


    public void remove(String day, String time, String regex){
        if(size == 0){
            System.out.println("Список пуст!");
        }
        else if (head.data.getTime().equals(time) && head.data.getRegexNumber().equals(regex)
                    && head.data.getDate().equals(day)) {
            if (size == 1)
                head = null;
            else
                head.next = head;
            size--;
            System.out.println("Удалено!");
        }
        else{
            var temp = head;
            while (temp.next != null){
                String tempTime = temp.next.data.getTime();
                String tempDate = temp.next.data.getDate();
                String tempRegex = temp.next.data.getRegexNumber();
                if(tempTime.equals(time) && tempRegex.equals(regex) && tempDate.equals(day)) {
                    if (temp.next.down == null) {
                        temp.next = temp.next.next;
                    } else if(temp.next.down == temp.next.next){
                        temp.down = temp.next.down;
                    } else {
                        temp.next.next = temp.next.down;
                    }
                    System.out.println("Удалено!!!");
                }
                else
                    temp = temp.next;
            }
            size--;
        }
    }



    public void sort(){
        Node[] array = new Node[size];
        Node temp, temp1 = head;
        int k;
        for(int i = 0; i < size; i++) array[i] = null;
        for(int i = 0; i < size; i++){
            k = 0;
            temp = head;
            for(int j = 0; j < size; j++){
                if(temp.data.getNameOfDoctor().compareTo(temp1.data.getNameOfDoctor()) < 0)
                    k++;
                temp = temp.next;
            }
            while(array[k] != null && size != 1)
                k++;
            array[k] = temp1;
            temp1 = temp1.next;
        }

        head = array[0];
        k = 0;
        for(int i = 0; i < size - 1; i++){
            array[i].next = array[i + 1];
            array[i].down = null;
            if(!array[i].data.getRegexNumber().equals(
                    array[i + 1].data.getRegexNumber())){
                array[k].down = array[i + 1];
            }
        }
        array[size - 1].next = null;
    }
    public void clear(){
        head = null;
    }

    public void removе(String day, String time, String regex){
        if(size == 0){
            System.out.println("Список пуст!");
            return;
        }
        ArrayList<Referral> list = new ArrayList<>();
        var temp = head;
        while (temp != null){
            list.add(temp.data);
            temp = temp.next;
        }
        list.removeIf(x -> x.getDate().equals(day) && x.getTime().equals(time) && x.getRegexNumber().equals(regex));
        System.out.println("Удалено!");
        clear();
        for(Referral x : list) insert(x);
    }

    public boolean checkTimeOfPatient(String regex, String day, String time){
        var temp = head;
        while (temp != null){
            if(temp.data.getRegexNumber().equals(regex)){
                if (temp.data.getDay().equals(day) && temp.data.getTime().equals(time))
                    return false;
            }
            temp = temp.next;
        }
        return true;
    }
}
