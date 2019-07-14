package pl.sda;

import java.net.URL;
import java.nio.file.Path;

public interface DownloadManager {
    //z URL zapisuj do sciezki
    void download(URL url, Path path) throws DownloadManagerException;
}
