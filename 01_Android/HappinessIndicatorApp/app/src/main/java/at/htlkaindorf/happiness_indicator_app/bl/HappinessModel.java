package at.htlkaindorf.happiness_indicator_app.bl;

import android.support.v4.app.INotificationSideChannel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;

public class HappinessModel {
    private Map<String, List<Integer>> happinessValues;

    public HappinessModel() {
        Random rand = new Random();
        String[] names = {"Marge", "Lisa", "Bart", "Homer"};
        happinessValues = new HashMap<>();
        for (String name : names) {
            List<Integer> values = new ArrayList<>();
            values.add(rand.nextInt(10) + 1);   // add at least one value
            happinessValues.put(name, values);
        }

        List<Integer> randomValues = new ArrayList<>();
        for (int i = 0; i < 20 - names.length; i++) {
            randomValues.add(rand.nextInt(10)+1);
        }

        // add the rest of the values
        for (Integer randomValue : randomValues) {
            happinessValues.get(names[rand.nextInt(names.length)]).add(randomValue);
        }
    }

    public Map<String, List<Integer>> getHappinessValues() {
        return happinessValues;
    }

    public void setHappinessValues(Map<String, List<Integer>> happinessValues) {
        this.happinessValues = happinessValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HappinessModel that = (HappinessModel) o;
        return Objects.equals(happinessValues, that.happinessValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(happinessValues);
    }

    public void addHappinessValue(String name, int value){
        if(happinessValues.containsKey(name)){
            happinessValues.get(name).add(value);
        }else {
            List<Integer> values = new ArrayList<>();
            values.add(value);
            happinessValues.put(name, values);
        }
    }

    public String getTopThreeString(){
        String output = "";
        // ToDo: !!!!!!!!
        Map<String, Double> avgValues = new TreeMap<>();
        for (String key : happinessValues.keySet()) {
            double avgValue = 0;
            for(int value : happinessValues.get(key)){
                avgValue += value;
            }
            avgValue /= happinessValues.get(key).size();
            avgValues.put(key, avgValue);
        }

        Map<String, Double> greatestAVGValues = new TreeMap<>();
        List<Double> values = new ArrayList<>();
        for (String key : avgValues.keySet()) {
            values.add(avgValues.get(key));
        }
        values.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return (int)(o1 - o2);
            }
        });
        for (String key : avgValues.keySet()) {
            if(avgValues.get(key).equals(values.get(values.size() -1)) || avgValues.get(key).equals(values.get(values.size() -2))
                    || avgValues.get(key).equals(values.get(values.size() -3))){
                greatestAVGValues.put(key, avgValues.get(key));
            }
        }
        for (String key : greatestAVGValues.keySet()) {
            output += key + " - " + greatestAVGValues.get(key);
        }



        return output;
    }
}
