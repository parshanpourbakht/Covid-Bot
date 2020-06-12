package CovidParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class CovidParser {
    public final String STATS_LINK = "https://www.worldometers.info/coronavirus/";
    private HashMap<String, HashMap<String, String>> countries;

    //parser = new CovidParser()
    //parser.getCountry("canada")
    public CovidParser() {
        countries = parseSite();
    }

    public HashMap<String, String> getCountry(String key) {
        return countries.get(key);
    }

    private static void replaceInListToLower(String[] list, String first, String second) {
        for (int i = 0; i < list.length; i++)
            list[i] = list[i].toLowerCase().replace(first, second);
    }

    private Document getCoronaSite() throws IOException {
        return Jsoup.connect(STATS_LINK).get();
    }

    public HashMap<String, String> worldStats() {

        HashMap<String, String> data = new HashMap<>();
        try {
            Document doc = getCoronaSite();
            Elements stats = doc.select("div.maincounter-number");
            data.put("total_cases", stats.get(0).text());
            data.put("total_deaths", stats.get(1).text());
            data.put("total_recovered", stats.get(2).text());


        } catch (IOException e) {
            System.out.println(e);
        }

        return data;

    }

    public HashMap<String, HashMap<String, String>> parseSite() {

        HashMap<String, HashMap<String, String>> Countries = new HashMap<>();
        try {
            // Get Website text
            Document doc = getCoronaSite();
            Element coronaTable = doc.select("table#main_table_countries_today").first();


            Elements columnElementNames = coronaTable.select("th");
            String[] columnNames = new String[columnElementNames.size()];
            for (int i = 0; i < columnElementNames.size(); i++) {
                columnNames[i] = columnElementNames.get(i).text();
            }
            replaceInListToLower(columnNames, " ", "_");
            //<tr style>
            Elements rows = coronaTable.select("tr[style]:not(.row_continent)");
            for (Element row : rows) {
                HashMap<String, String> countryEntry = new HashMap<>();
                Elements countryCols = row.select("td");
                String countryName = countryCols.get(1).text()
                        .replace(" ", "_").toLowerCase();

                for (int i = 2; i < countryCols.size(); i++) {
                    String colText = countryCols.get(i).text();
                    // if text in this column is empty, that means the data is not in the website hence we  do not store in our hashmap.
                    if (colText.isEmpty()) continue;
                    countryEntry.put(columnNames[i], colText);
                }
                Countries.put(countryName, countryEntry);
            }
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return Countries;
    }


}
