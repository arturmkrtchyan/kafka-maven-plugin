package com.arturmkrtchyan.kafka;

public class KafkaPluginException extends RuntimeException {

    private static final long serialVersionUID = -2715142907876721085L;

    public KafkaPluginException() {
        super();
    }

    public KafkaPluginException(final String message) {
        super(message);
    }

    public KafkaPluginException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public KafkaPluginException(final Throwable cause) {
        super(cause);
    }

}
