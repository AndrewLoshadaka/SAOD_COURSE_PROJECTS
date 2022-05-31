package com.company;

public class Referral {
    private final String regexNumber;
    private final String nameOfDoctor;
    private final String date;
    private final String time;

    public Referral(String regexNumber, String nameOfDoctor, String date, String time) {
        this.regexNumber = regexNumber;
        this.nameOfDoctor = nameOfDoctor;
        this.date = date;
        this.time = time;
    }

    public String getNameOfDoctor() {
        return nameOfDoctor;
    }

    public String getRegexNumber() {
        return regexNumber;
    }

    public String getDate() {
        return date;
    }

    public String getDay(){
        return switch (getDate()) {
            case "1" -> "Понедельник";
            case "2" -> "Вторник";
            case "3" -> "Среда";
            case "4" -> "Четверг";
            case "5" -> "Пятница";
            default -> "";
        };
    }

    public String getTime() {
        return time;
    }
}
