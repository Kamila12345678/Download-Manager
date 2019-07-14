package pl.sda;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTTPDownloadManager implements DownloadManager {


    @Override
    public void download(URL url, Path path) {
        //bedziem pobierac URL
        HttpClient client = HttpClientBuilder.create().build();

        //lapiemy jeden z dwoch wyjatkow opakowanych w jednym bloku
        try {
            HttpResponse response = client.execute(new HttpGet(url.toURI()));
            try (InputStream content = response.getEntity().getContent()) {
                //po wykonaniu ponizszych operacji stream zostanie zamkniety
                //nawet jesli poleci wyjatek
                Files.copy(content, path);
            }
        } catch (IOException | URISyntaxException e) {
            throw new DownloadManagerException(String.format("Failed to download file %s", url), e);
        }

    }
}
