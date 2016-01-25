package rabbit;

import config.ConfigExtractor;

/**
 * Extract configuration data for RabbitMQ
 */
public class RabbitMqConfig {
    private String host = null;
    private String receiveQueue = null;
    private String preseneterSendQueue = null;
    private String username = null;
    private String password = null;

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
        receiveQueue = ConfigExtractor.getProperty("rabbitReceiveQueue");
        preseneterSendQueue = ConfigExtractor.getProperty("rabbitSendQueue");
        username = ConfigExtractor.getProperty("rabbitUser");
        password = ConfigExtractor.getProperty("rabbitPassword");
    }

    public String getHost() {
        return host;
    }

    public String getReceiveQueue() {
        return receiveQueue;
    }

    public String getSendQueue() {
        return preseneterSendQueue;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
