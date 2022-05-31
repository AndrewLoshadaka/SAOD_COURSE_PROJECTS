package doctor;

public class Week {
    private final DayOfTheWeek[] week = new DayOfTheWeek[5];
    {
        week[0] = new DayOfTheWeek("Понедельник");
        week[1] = new DayOfTheWeek("Вторник");
        week[2] = new DayOfTheWeek("Среда");
        week[3] = new DayOfTheWeek("Четверг");
        week[4] = new DayOfTheWeek("Пятница");
    }

    public Week() {
        for(int i = 0; i < 5; i++)
            this.week[i].createSchedule();
    }

    public DayOfTheWeek getDay(String day){
        return switch (day) {
            case "1" -> week[0];
            case "2" -> week[1];
            case "3" -> week[2];
            case "4" -> week[3];
            case "5" -> week[4];
            default -> null;
        };
    }
}
