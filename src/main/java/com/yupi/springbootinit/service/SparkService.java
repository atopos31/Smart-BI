package com.yupi.springbootinit.service;

import io.github.briqt.spark4j.exception.SparkException;

public interface SparkService {

    /**
     * Communicates with the Spark service.
     *
     * @param userMessage The message sent by the user.
     * @return The response from the Spark service.
     * @throws SparkException If an error occurs while communicating with the Spark service.
     */
    String communicateWithSpark(String userMessage) throws SparkException;
}
