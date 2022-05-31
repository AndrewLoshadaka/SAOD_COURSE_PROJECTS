package hash_table;

public class Patient {
    private final String regexNumber; //MM-NNNNNN
    private final String name;
    private final int yearOfBirth;
    private final String address;
    private final String placeOfWork;
    private boolean checkAppointment = false;

    public void setCheckAppointment(boolean checkAppointment) {
        this.checkAppointment = checkAppointment;
    }

    public boolean isCheckAppointment() {
        return checkAppointment;
    }


    public Patient(String regexNumber, String name, int yearOfBirth, String address, String placeOfWork) {
        this.regexNumber = regexNumber;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.placeOfWork = placeOfWork;
    }

    public String getRegexNumber() {
        return regexNumber;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getAddress() {
        return address;
    }


    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public static void showInform(Patient patient){
        Patient emptyPatient = new Patient("empty", "empty", 0, "empty", "empty");
        if(patient != null && patient != emptyPatient) {
            System.out.println(patient.getRegexNumber() + " " + patient.getName() + " " + patient.getYearOfBirth() + " " +
                    patient.getAddress() + " " + patient.getPlaceOfWork());
        }
        else System.out.println("Запись не существует или удалена!");
    }

}
