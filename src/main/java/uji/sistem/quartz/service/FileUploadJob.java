package uji.sistem.quartz.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileUploadJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String dataFolder = "D:\\Kuliah\\Semester 8\\Pengujian Sistem\\Data\\Data-1";
        File folder = new File(dataFolder);
        List<File> listOfFiles = Arrays.asList(folder.listFiles());

        try {
            String start = String.valueOf(LocalTime.now());
            sendToWs("http://localhost:24/api/upload", listOfFiles, start);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToWs(String url, List<File> files, String start) throws IOException {

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        for(File f : files) {
            builder.addBinaryBody("file", f, ContentType.MULTIPART_FORM_DATA, f.getName()).build();
        }
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        try {

            HttpResponse response = httpClient.execute(httpPost);

            try (InputStream content = response.getEntity().getContent()) {

                String responseBody = new String(content.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println(responseBody + start);

            }

        } catch (Exception e) {

            System.out.println("Data gagal dikirim");
        }
    }
}
