package quartz;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);
    private static int PAGE_COUNT = 5;
    public static void main(String[] args) throws IOException {

        for (int i = 1; i <= PAGE_COUNT; i++) {
            Connection connection = Jsoup.connect(PAGE_LINK + i);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element dateElement = row.select(".vacancy-card__date").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String date = dateElement.child(0).attr("datetime");
                LocalDateTime formattedDate = new HabrCareerDateTimeParser().parse(date);
                System.out.printf("%s %s %s%n ", vacancyName, link, formattedDate);
            });
        }
    }
}