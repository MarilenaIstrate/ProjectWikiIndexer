package com.endava.services.impl;

import com.endava.services.RequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class RequestServiceImpl implements RequestService {

    @Value("${url}")
    private String urlString;

    public InputStream requestTitle(String title) throws MalformedURLException, IOException {

        /* Set the title of the article */
        String realUrlString = urlString.replaceFirst("TITLE", title);

        /* Set the connection */
        URL url = new URL(realUrlString);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        /* Check return code */
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;
    }
}
