package Utility;

import Stuff.*;

import java.io.*;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type Decoder.
 */
public class Decoder{

    public static Hashtable<Long, Worker> decodeIntoCollectionFromExecute(String s) {
        Hashtable<Long, Worker> collection = new Hashtable<>();
        String errorMessage="В ходе заполнения коллекции возникли следующие ошибки:\n";
        if (s.length()>0){
            String a[] = s.split("\n");
            for(int i=0;i<a.length;i++) {
                String b[] = a[i].split(";");
                Worker Worker = new Worker();
                Coordinates coordinates = new Coordinates();
                Person person = new Person();
                Worker.setId(WorkerCollection.generateId());


                try {

                    if (b[0].equals("") || b[0] == null) {
                        throw new Exception();
                    } else Worker.setName(b[0]);
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указано имя у работника №" + Worker.getId() + ",изменено на Name,можете исправить с помощью команды update."+"\n");
                    Worker.setName("Name");
                }


                try {
                    if (b[1].equals("") || b[1] == null ) {
                        throw new Exception();
                    } else coordinates.setX(Float.parseFloat(b[1]));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана координата Х у работника №" + Worker.getId() + ",изменено на 0,можете исправить с помощью команды update."+"\n");
                    coordinates.setX(0);
                }



                try {
                    if (b[2].equals("") || b[2] == null || Double.parseDouble(b[2])>289D) {
                        throw new Exception();
                    } else coordinates.setY(Double.parseDouble(b[2]));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана координата Y у работника №" + Worker.getId() + ",изменено на 0,можете исправить с помощью команды update."+"\n");
                    coordinates.setY((double) 0);
                }

                Worker.setCoordinates(coordinates);



                try {
                    if (Double.parseDouble(b[3]) <= 0) {
                        throw new Exception();
                    } else Worker.setSalary(Double.parseDouble(b[3]));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана зарплата у работника №" + Worker.getId() + ",изменено на 1,можете исправить с помощью команды update."+"\n");
                    Worker.setSalary(1);
                }

                try {
                    Worker.setStartDate(Timestamp.valueOf(b[4]));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана дата начала работы у работника №" + Worker.getId() + ",изменено на время сейчас,можете исправить с помощью команды update."+"\n");
                    Worker.setStartDate(Timestamp.from(Instant.now()));
                }

                try {
                    Worker.setEndDate(Timestamp.valueOf(b[5]));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана дата конца работы у работника №" + Worker.getId() + ",изменено на время сейчас,можете исправить с помощью команды update."+"\n");
                    Worker.setEndDate(Timestamp.from(Instant.now()));
                }
                try {
                    Worker.setPosition(Position.valueOf((b[7]).toUpperCase()));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана Должность у человека билетом №" + Worker.getId() + ",изменено на MANAGER,можете исправить с помощью команды update."+"\n");
                    Worker.setPosition((Position.MANAGER));
                }

                try {
                    if (b[8].equals("") || b[8] == null) {
                        throw new Exception();
                    } else {
                        String stringDateFormat = "EEE MMM dd HH:mm:ss z yyyy";
                        SimpleDateFormat format = new SimpleDateFormat(stringDateFormat, Locale.US);
                        person.setBirthday(format.parse(b[8]));
                    }
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана дата дня рождения у работника №" + Worker.getId() + ",изменено на нынешнее время,можете исправить с помощью команды update."+"\n");
                    person.setBirthday(Timestamp.from(Instant.now()));
                }

                try {
                    person.setEyeColor(Color.valueOf((b[9]).toUpperCase()));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указан цвет глаз у работника №" + Worker.getId() + ",изменено на BLACK,можете исправить с помощью команды update."+"\n");
                    person.setEyeColor(Color.BLACK);
                }

                try {
                    person.setHairColor(Color.valueOf((b[10]).toUpperCase()));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указан цвет волос у работника №" + Worker.getId() + ",изменено на BLACK,можете исправить с помощью команды update."+"\n");
                    person.setHairColor(Color.BLACK);
                }

                try {
                    person.setNationality(Country.valueOf((b[11]).toUpperCase()));
                }
                catch (Exception e){
                    CreateWorker.mistakes+=(errorMessage);
                    errorMessage = "";
                    CreateWorker.mistakes+=("Некорректно указана национальность у работника №" + Worker.getId() + ",изменено на RUSSIA,можете исправить с помощью команды update."+"\n");
                    person.setNationality(Country.RUSSIA);
                }
                Worker.setPerson(person);
                collection.put(Worker.getId(), Worker);
            }
        }
        return collection;
    }


}
