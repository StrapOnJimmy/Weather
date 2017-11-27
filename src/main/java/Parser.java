import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Администратор on 27.11.2017.
 */
public class Parser {
    private static Document getPage() throws IOException {
        String url = "http://primpogoda.ru/weather/vladivostok";
        Document page = Jsoup.parse(new URL(url),3000);
        return page;
    }
    //Регулярные выражение
    private static Pattern pattern = Pattern.compile("\\d{1,2}\\s.+\\s\\d{1,4}");
    private static String getDateFromString(String stringDate) throws Exception{
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) return matcher.group();
        throw new Exception("Can't extract date from string");
    }

    private static void printValues(Elements values, int index){
       Element element = values.get(index);
       Elements dayTimes = element.select("div[class=small-12 medium-6 columns]");
        for (Element dayTime: dayTimes
             ) {
            String dayTimeName = dayTime.select("h4").text();
            String wthrConditions = dayTime.select("div[class=weather-text]").text();
            String t = dayTime.select("div[class=temperature]").text();
            String wind = dayTime.select("div[class=wind]").text();
            System.out.println(dayTimeName + "   " + wthrConditions + "   " + t + "   " + "   " + wind);

        }
    }
    public static void main(String[] args) throws Exception {
        Document page = getPage();
        //css query language
        Element tableWthr = page.select("div[class=forecast]").first();
        Elements names = tableWthr.select("h3");
        Elements values = tableWthr.select("div[class=row data twelve-hour]");
        int index = 0;
        for (Element name:names
             ) {
            String stringDate = name.text();
            String date = getDateFromString(stringDate);
            System.out.println(date);
            printValues(values,index);
            index++;
        }

    }
}
