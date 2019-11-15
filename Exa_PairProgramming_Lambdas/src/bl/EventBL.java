package bl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class EventBL {
    private List<Event> events;

    private static boolean test(Event event) {
        return event.getText().contains("PLF");
    }

    public void initEvents(){
        events = new ArrayList<>();
        LocalDate start = LocalDate.of(2019, 9, 9);
        LocalDate end = LocalDate.of(2019, 12, 20);

        Random rand = new Random();
        for (int i = 0; i < 25; i++) {
            LocalDate randomDate;
            int day;
            int month = rand.nextInt(4) + 9;
            if(month == 9){
                day = rand.nextInt(30 - 9 + 1) + 9;
            }else if(month == 12){
                day = rand.nextInt(20) + 1;
            }else if(month == 10){
                day = rand.nextInt(31) + 1;
            }else {
                day = rand.nextInt(30) + 1;
            }
            randomDate = LocalDate.of(2019, month, day);
            String[] subjects = {
                    "POS",
                    "DBI",
                    "NVS",
                    "TINF"
            };
            String[] activities = {
                    "PLF",
                    "Übung",
                    "Aufgabe"
            };
            String randomSubject = subjects[rand.nextInt(subjects.length)];
            String randomActivity = subjects[rand.nextInt(activities.length)];
            Event event = new Event(String.format("%s-%s", randomSubject, randomActivity), randomDate);
            events.add(event);
        }


    }
    public void sortEvents(boolean upwards){
      /*  events.sort(((Comparator<Event>) (o1, o2) -> {
            return o1.getDate().compareTo(o2.getDate());
        }).thenComparing((o1, o2) -> o1.getText().compareTo(o2.getText())));*/
        if(!upwards){
            events.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getText).reversed());
        }
        events.sort(Comparator.comparing(Event::getDate).thenComparing(Event::getText));

    }

    public void printEvents(){
        events.forEach(event -> System.out.println(event));
        events.forEach(System.out::println);
    }
    
    public void filterEvents(){
        events.removeIf(event -> event.getText().contains("PLF"));
        events.removeIf(EventBL::test);
    }

    public static void main(String[] args) {
        EventBL eventBL = new EventBL();
        eventBL.initEvents();
        eventBL.sortEvents(true);
        eventBL.filterEvents();
        eventBL.printEvents();
    }

}
