package doctor;

import hash_table.Patient;

public class Doctor implements Comparable {
    private final String name;
    private final String post;
    private final int numberOfRoom;

    private final String schedule;
    private final Week week;

    public Doctor(String name, String post, int numberOfRoom, String schedule, Week week) {
        this.name = name;
        this.post = post;
        this.numberOfRoom = numberOfRoom;
        this.schedule = schedule;
        this.week = week;
    }
    public String getName() {
        return name;
    }
    public String getPost() {
        return post;
    }
    public int getNumberOfRoom() {
        return numberOfRoom;
    }
    public String getSchedule() {
        return schedule;
    }


    public Week getWeek() {
        return week;
    }

    public void showListOfFreeTime(String key){
        DayOfTheWeek currentDay = week.getDay(key);
        int i = 1;
        for(String x : currentDay.getFreeTime()){
            System.out.println(i + ") " + x);
            i++;
        }
    }

    public void createNewAppointment(String day, String time, Patient patient){
        int k = Integer.parseInt(day) - 1;
        week.getDay(day).createNewAppointment(time, patient);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
