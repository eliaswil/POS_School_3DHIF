package functions;

import io.Company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyFunctions {
    private List<Company> companies;

    public void loadData() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"), "src", "res", "company_data.csv");
        companies = Files.lines(path)
                .skip(1)
                .map(Company::new)
                .collect(Collectors.toList());

        companies.forEach(System.out::println);

    }

    public void performStreamingTest(){
        // sortieren
        companies.sort(Comparator.comparing(Company::getCountry)
                .thenComparing(Company::getFounded));

        List<Company> sorted = companies.stream()
                .sorted(Comparator.comparing(Company::getCountry).thenComparing(Company::getFounded))
                .collect(Collectors.toList());

        // filtern
        List<Company> filtered = companies.stream()
                .filter(c -> c.getFounded().isBefore(LocalDate.of(2000, 1, 1)))
                .collect(Collectors.toList());

        // sum of all turnovers of all companies
        double totalSum = companies.stream().mapToDouble(Company::getTurnover).sum();
        System.out.println(totalSum);

        String[] countries = companies.stream()
                .map(Company::getCountry)
                //.collect(Collectors.toSet())
                .distinct()
                .toArray(String[]::new);

        Arrays.stream(countries).forEach(System.out::println); // Array Stream -> Ausgabe

    }

    public static void main(String[] args) {

        String line = "Name;Date;Age;Gender;Lol;xy;asdf";
        String[] tokens = line.split(";",2);

        Arrays.stream(tokens).forEach(System.out::println);


        CompanyFunctions cf = new CompanyFunctions();
//        try {
//            cf.loadData();
//            cf.performStreamingTest();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
