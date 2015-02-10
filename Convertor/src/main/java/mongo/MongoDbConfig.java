package mongo;

import config.ConfigExtractor;

/**
 * Extract configuration data for MongoDB
 */
public class MongoDbConfig {
    private String ip;
    private int port;

    /**
     * read configuration data for mongoDB
     */
    public MongoDbConfig() {
        ip = ConfigExtractor.getProperty("mongoHost");
        try {
            port = Integer.parseInt(ConfigExtractor.getProperty("mongoPort"));
        }
        catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
