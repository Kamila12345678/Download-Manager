package pl.sda;

public class DownloadManagerException extends RuntimeException {
    public DownloadManagerException(String message) {
        super(message);
    }

    public DownloadManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
