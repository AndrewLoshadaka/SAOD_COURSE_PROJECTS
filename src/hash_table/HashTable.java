package hash_table;

import java.util.ArrayList;

public class HashTable {
    private static Patient[] hashTable;
    //пустой пациент
    private static final Patient emptyPatient = new Patient("empty", "empty", 0, "empty", "empty");
    //начальный рамер
    private static int SIZE = 10;

    //создание
    public HashTable(){
        hashTable = new Patient[SIZE];
        for(int i = 0; i < SIZE; i++){
            hashTable[i] = null;
        }
    }
    //вставка
    public void insert(Patient patient){
        int hash = hashFunc(patient.getRegexNumber());
        if(hash >= SIZE)
            updateSize();
        if(hashTable[hash] == null) {
            hashTable[hash] = patient;
        }
        else if (hashTable[hash] == emptyPatient) {
            hashTable[hash] = patient;
        }
        else {
            int index = hash;
            while (hashTable[index] != null){
                if (hashTable[hash] == emptyPatient) {
                    hashTable[hash] = patient;
                    return;
                }
                index++;
                if(index >= SIZE) updateSize();
            }
            hashTable[index] = patient;
        }
    }

    //удаление
    public String delete(String key){
        int hash = hashFunc(key);
        if(hashTable[hash] == null || hash >= SIZE || hashTable[hash] == emptyPatient) return "No element!";
        if(hashTable[hash].getRegexNumber().equals(key)) hashTable[hash] = emptyPatient;
        else {
            int index = hash;
            while (!hashTable[index].getRegexNumber().equals(key) || hashTable[index] == null){
                index++;
                if(index == SIZE) return  "No element!";
            }
            hashTable[index] = emptyPatient;
        }
        return "Запись с ключом " + key + " удалена!";
    }

    //поиск
    public static Patient find(String key){
        Patient findPatient = null;
        int hash = hashFunc(key); //21
        if(hash >= SIZE) return null;
        if (hashTable[hash] != null && hashTable[hash].getRegexNumber().equals(key)) return hashTable[hash];
        if(hashTable[hash] == emptyPatient) {
            if (hashTable[hash + 1] == null) return null;
        }
        if(!hashTable[hash].getRegexNumber().equals(key)){
            for(int i = hash + 1; i < SIZE; i++){
                if(hashTable[i] != emptyPatient){
                    if(hashTable[i].getRegexNumber().equals(key)) {
                        findPatient = hashTable[i];
                        break;
                    }
                }
            }
        }
        return findPatient;
    }

    //просмотр всей таблицы
    public void showTable(){
        for(int i = 0; i < SIZE; i++) {
            if (hashTable[i] != null && hashTable[i] != emptyPatient) {
                //System.out.print(hashFunc(hashTable[i].getRegexNumber()) + " ");
                Patient.showInform(hashTable[i]);
            }
        }
    }

    //поиск по имени
    public ArrayList<Patient> findByName(String name){
        ArrayList<Patient> list = new ArrayList<>();
        for(int i = 0; i < SIZE; i++){
            if(hashTable[i] != null) {
                assert hashTable[i] != null;
                if(hashTable[i].getName().equals(name))
                    list.add(hashTable[i]);
            }
        }
        return list;
    }

    //очистка таблицы
    public void clear(){
        for (int i = 0; i < SIZE; i++)
            hashTable[i] = null;
    }

    public boolean checkTable(){
        for(int i = 0; i < SIZE; i++){

            if(hashTable[i] != null && hashTable[i] != emptyPatient)
                if(hashTable[i].isCheckAppointment())
                    return false;
        }
        return true;
    }

    //хещ-функция
    private static int hashFunc(String key) {
        if(key.equals("empty")) return -1;
        int intKey = (int) key.charAt(0) * (int) key.charAt(1) + (int) key.charAt(1) * (int) key.charAt(2) +
                (int) key.charAt(2) * (int) key.charAt(3) + (int) key.charAt(3) * (int) key.charAt(4) +
                (int) key.charAt(4) * (int) key.charAt(5) + (int) key.charAt(5) * (int) key.charAt(6)+
                (int) key.charAt(6) * (int) key.charAt(7);
        return intKey % SIZE;
    }

    //обновление размера таблицы
    private void updateSize(){
        Patient[] temp = hashTable;
        SIZE = (int) (SIZE * 1.5 + 1);
        hashTable = new Patient[SIZE];
        for(int i = 0; i < SIZE; i++) hashTable[i] = null;
        for(int i = 0; i < temp.length; i++){
            if(temp[i] == emptyPatient)
                hashTable[i] = emptyPatient;
            else if(temp[i] != null) {
                insert(temp[i]);
            }
        }
    }

    //поиск повторов в таблице при добавлении
    public boolean checkRepeatOfHash(String hash){
        for (Patient patient : hashTable) {
            if(patient == null) return false;
            if (patient.getRegexNumber().equals(hash) )
                return true;
        }
        return false;
    }


}
