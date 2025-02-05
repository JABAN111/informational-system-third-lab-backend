package is.fistlab.services.minio;

import java.io.File;
import java.util.List;

public interface MinioService {
    String uploadFile(String username, final String filename, final File byteContent);
    byte[] downloadFile(String fileName);
    void removeFile(String userId, String fileName);
    List<String> listFilesWithPrefix(String username);
    List<String> listFilesWithoutPrefix();
}
