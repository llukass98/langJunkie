package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class JsoupRequest extends AbstractRequest<Document> implements Request<Document> {

    @Override
    public Document getRequest(String url) {
        String ref = url.split("\\?")[0];
        Document response = new Document("");

        try {
            response = constructConnection(url).referrer(ref).get();
        } catch (IOException e) {
            log.warn("{}, {}", url, e.getMessage());
        }

        return response;
    }

    @Override
    public Document postRequest(String url, String payload) {
        Document response = new Document("");

        try {
            response = constructConnection(url)
                    .referrer(url)
                    .requestBody(payload)
                    .post();
        } catch (IOException e) {
            log.warn("{}, {}", url, e.getMessage());
        }

        return response;
    }

    private Connection constructConnection(String url) {
        String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) "
                +"Gecko/20100101 Firefox/89.0";
        String accept = "text/html,application/xhtml+xml,application/xml;"
                +"q=0.9,image/webp,*/*;q=0.8";

        return Jsoup.connect(url)
                .userAgent(userAgent)
                .header("Accept", accept)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Host", url.split("//")[1].split("/")[0])
                .followRedirects(true)
                .timeout(4 * 1000);
    }
}
