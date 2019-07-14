package pl.sda;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HTTPDownloadManagerTest {

    private Server server;

    @BeforeEach
    void setup() throws Exception {
        server = prepareServer();
        server.start();
    }
    @AfterEach
    void tearDown() throws Exception {
        server.stop();
    }

    @DisplayName(
            "given http://localhost:8080/file" +
                    "when download " +
                    "then file is downloaded to download dir"
    )
    @Test

    public void dummy() throws Exception {
        //given
        //tworze tymczasowy katalog do ktorego chce pobrac plik,
        //definiuje jego prefix oraz doklejam sciezke do pliku.
        Path downloadPath = Files.createTempDirectory(
                "download-dir")
                .resolve(Paths.get("file"));
        HTTPDownloadManager manager = new HTTPDownloadManager();


        //when
        //pobieram plik z podanego adresu URL
        manager.download(new URL("http://localhost:8080/file"), downloadPath);

        //then
        //sprawdzamy czy taki plik istnieje
        assertThat(downloadPath).exists();

    }

    private Server prepareServer() {
        Server server = new Server(8080);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
                httpServletResponse.getWriter().write("Hello");
                httpServletResponse.setStatus(200);
                httpServletResponse.setContentType("text/plain");
                request.setHandled(true);
            }
        });
        return server;
    }
}
