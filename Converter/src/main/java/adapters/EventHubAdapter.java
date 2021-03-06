package adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import datamodel.EventHubMessage;
import dmon.core.commons.converters.JsonConverter;
import dmon.core.commons.datamodel.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Adapt message to the format required by the event-hub used in SPECS project
 */
public class EventHubAdapter implements Adapter {
    private static final Logger logger = LoggerFactory.getLogger(EventHubAdapter.class);
    public static final UUID uuid = UUID.randomUUID();

    /**
     * Create a new message that is compliant with the event-hub from SPECS project
     * @param filteredJson
     * @param measurement
     * @return
     */
    public String adaptMessage(String filteredJson, Measurement measurement) {
        String command = measurement.getUserCommand();
        String usedTool = command.substring(0, command.indexOf(' '));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = null;

        try {
            data = mapper.readTree(filteredJson);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        EventHubMessage eventHubMessage = new EventHubMessage();
        eventHubMessage.setComponent(uuid.toString());
        eventHubMessage.setObject(usedTool);
        eventHubMessage.setLabels(new String[]{"userId-" + measurement.getClientId(), "jobId-" + measurement.get_id().get$oid()});
        eventHubMessage.setType("metric");
        eventHubMessage.setData(data);
        eventHubMessage.setTimestamp((new Date().getTime()) / 1000);
        return JsonConverter.objectToJsonString(eventHubMessage);
    }
}
