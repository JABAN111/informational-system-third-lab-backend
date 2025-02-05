package is.fistlab.services.impl.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import is.fistlab.services.minio.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    private final String BUCKET_NAME = "import-files";

    @Value("${minio.root.username}")
    private String username;

    @Value("${minio.root.pwd}")
    private String password;

    private final MinioClient minioClient;

    public MinioServiceImpl() {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("JABA_SUPER_USER_MINIO", "jaba127!368601NO")
                .build();
        ensureBucketExists();
    }

    public String uploadFile(final String username, final String filename, final File file) {

        final String filenameForStoring = getNewFileName(username, filename);

        try (InputStream fileStream = new FileInputStream(file)) {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(filenameForStoring)
                    .stream(fileStream, file.length(), -1)
                    .contentType("application/octet-stream")
                    .build();

            minioClient.putObject(args);
            log.info("File successfully uploaded: {}", filenameForStoring);
            return filenameForStoring;
        } catch (MinioException e) {
            log.error("Minio error occurred while uploading file: {}", filenameForStoring, e);
            throw new RuntimeException("Minio error occurred while uploading file", e);
        } catch (IOException e) {
            log.error("IO error occurred while uploading file: {}", filenameForStoring, e);
            throw new RuntimeException("IO error occurred while uploading file", e);
        } catch (InvalidKeyException e) {
            log.error("Invalid key error occurred while uploading file: {}", filenameForStoring, e);
            throw new RuntimeException("Invalid key error occurred while uploading file", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm error occurred while uploading file: {}", filenameForStoring, e);
            throw new RuntimeException("No such algorithm error occurred while uploading file", e);
        }
    }

    @Override
    public byte[] downloadFile(final String filename) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(filename)
                    .build();

            InputStream fileStream = minioClient.getObject(args);
            byte[] fileBytes = fileStream.readAllBytes();

            fileStream.close();

            log.info("File successfully downloaded: {}", filename);

            return fileBytes;
        } catch (MinioException e) {
            log.error("Minio error occurred while downloading file: {}", filename, e);
            throw new RuntimeException("Minio error occurred while downloading file", e);
        } catch (IOException e) {
            log.error("IO error occurred while downloading file: {}", filename, e);
            throw new RuntimeException("IO error occurred while downloading file", e);
        } catch (InvalidKeyException e) {
            log.error("Invalid key error occurred while downloading file: {}", filename, e);
            throw new RuntimeException("Invalid key error occurred while downloading file", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm error occurred while downloading file: {}", filename, e);
            throw new RuntimeException("No such algorithm error occurred while downloading file", e);
        }
    }

    @Override
    public void removeFile(final String username, final String filename) {
        String filenameForStoring = getNewFileName(username, filename);

        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(filenameForStoring)
                    .build();
            minioClient.removeObject(args);
            log.info("File successfully removed: {}", filenameForStoring);
        } catch (ServerException | InsufficientDataException
                 | ErrorResponseException | IOException
                 | NoSuchAlgorithmException | InvalidKeyException
                 | InvalidResponseException | XmlParserException
                 | InternalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> listFilesWithPrefix(String username) {
        String prefix = username.trim() + "/";
        return listFilesWithPrefixOrWithout(prefix);
    }

    // method for admin only
    @Override
    public List<String> listFilesWithoutPrefix() {
        return listFilesWithPrefixOrWithout(null);
    }

    private List<String> listFilesWithPrefixOrWithout(String prefix) {
        ListObjectsArgs.Builder listObjectsArgsBuilder = ListObjectsArgs.builder().bucket(BUCKET_NAME);

        if (prefix != null) {
            listObjectsArgsBuilder.prefix(prefix);
        }

        Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgsBuilder.build());
        List<String> fileNames = new ArrayList<>();

        for (Result<Item> result : results) {
            try {
                Item item = result.get();
                fileNames.add(item.objectName());
            } catch (Exception e) {
                log.error("Error while processing item: {}", e.getMessage());
                throw new RuntimeException("Error while processing item", e);
            }
        }

        return fileNames;
    }

    private String getNewFileName(String username, String filename) {
        return username.trim() + "/" + filename.trim();
    }

    private void ensureBucketExists() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating bucket: " + BUCKET_NAME + " for MinIO", e);
        }
    }


}
