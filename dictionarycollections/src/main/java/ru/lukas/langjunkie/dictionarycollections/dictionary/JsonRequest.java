package ru.lukas.langjunkie.dictionarycollections.dictionary;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author Dmitry Lukashevich
 */
@Slf4j
public class JsonRequest extends AbstractRequest<String> implements Request<String> {

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .connectTimeout(Duration.ofSeconds(4))
            .build();

    @Override
    public String getRequest(String url) {
        HttpRequest request = constructConnection(url).GET().build();
        String response = "[]";

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
        } catch (IOException e) {
            log.warn("{}, {}", url, e.getMessage());
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return response;
    }

    private HttpRequest.Builder constructConnection(String url) {
        String accept = "text/html,application/xhtml+xml,application/xml;"
                +"q=0.9,image/webp,*/*;q=0.8";

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Accept", accept)
                .setHeader("Accept-Language", "en-US,en;q=0.5");
    }
}
