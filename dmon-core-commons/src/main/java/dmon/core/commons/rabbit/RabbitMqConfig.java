package dmon.core.commons.rabbit;


import dmon.core.commons.config.ConfigExtractor;

/**
 * Extract configuration data for RabbitMQ
 */
public class RabbitMqConfig {
    private String host = null;
    private String receiveQueue = null;
    private String sendQueue = null;
    private String delayQueue = null;
    private String username = null;
    private String password = null;

    public RabbitMqConfig() {
        host = ConfigExtractor.getProperty("rabbitHost");
        receiveQueue = ConfigExtractor.getProperty("rabbitReceiveQueue");
        sendQueue = ConfigExtractor.getProperty("rabbitSendQueue");
        delayQueue = ConfigExtractor.getProperty("rabbitDelayQueue");
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
        return sendQueue;
    }

    public String getDelayQueue() {
        return delayQueue;
    }

    public void setDelayQueue(String delayQueue) {
        this.delayQueue = delayQueue;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
