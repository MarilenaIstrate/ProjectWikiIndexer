package com.endava.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Service that sends a request for an article
 */
public interface WikiRequestService {

    /**
     * Sends a wikipedia rquest
     * @param title title of the article in the request
     * @return InputStream of the article
     * @throws MalformedURLException
     * @throws IOException
     */
    InputStream requestTitle(String title) throws MalformedURLException, IOException;
}
