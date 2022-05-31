package doctor;

import hash_table.Patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DayOfTheWeek {

    public DayOfTheWeek(String day) {
    }
    private final HashMap<String, Patient> freeTime = new HashMap<>();

    public void createSchedule(){
        freeTime.put("09:00", null);
        freeTime.put("09:30", null);
        freeTime.put("10:00", null);
        freeTime.put("10:30", null);
        freeTime.put("11:00", null);
        freeTime.put("11:30", null);
        freeTime.put("12:00", null);
        freeTime.put("12:30", null);
        freeTime.put("13:00", null);
        freeTime.put("13:30", null);
        freeTime.put("14:00", null);
        freeTime.put("14:30", null);
    }

    public ArrayList<String> getFreeTime(){
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String, Patient> entry : freeTime.entrySet()){
            if(entry.getValue() == null)
                list.add(entry.getKey());
        }
        Collections.sort(list);
        return list;
    }

    private ArrayList<String> getNotFreeTime(){
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String, Patient> entry : freeTime.entrySet()){
            if(entry.getValue() != null)
                list.add(entry.getKey());
        }
        Collections.sort(list);
        return list;
    }

    public ArrayList<Patient> getListOfPatient(){
        ArrayList<Patient> list = new ArrayList<>();
        for(String x : getNotFreeTime()) {
            for (Map.Entry<String, Patient> entry : freeTime.entrySet()) {
                if(x.equals(entry.getKey())) {
                    list.add(entry.getValue());
                    break;
                }
            }
        }
        return list;
    }

    public void createNewAppointment(String time, Patient patient){
        freeTime.remove(time);
        freeTime.put(time, patient);
    }

    public void returnAppointment(String time){
        freeTime.remove(time);
        freeTime.put(time, null);
    }

    public String getTime(String number){
        return getFreeTime().get(Integer.parseInt(number) - 1);
    }
}
