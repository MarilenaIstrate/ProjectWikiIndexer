package com.endava.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public interface RequestService {
    /**
     * Requests a wiki article
     * @param title = title of the article
     * @return InputStream of the body
     * @throws MalformedURLException
     * @throws IOException
     */
    InputStream requestTitle(String title) throws MalformedURLException, IOException;
}
