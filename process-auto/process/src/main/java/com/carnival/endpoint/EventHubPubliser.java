package com.carnival.endpoint;

import com.carnival.config.TemplateConfig;
import com.carnival.entity.TemplateInfo;
import com.carnival.utils.GsonUtils;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.azure.messaging.eventhubs.*;

import java.util.*;
import java.util.stream.Collectors;

@Component("event-hubs")
@Slf4j
public class EventHubPubliser extends OutBoundAdpter {

    private static final String connectionString = "Endpoint=sb://event-carnival.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=Xq9I91MWnh6nnkJ1b6JZaDoZt8t2jJf3l+AEhDnfaF8=";

    @Override
    public Object processOutbound(TemplateConfig config, List<Map<String, Object>> mapList) {
        TemplateInfo templateInfo = config.getTemplateInfo();
        String hub = templateInfo.getDestination();
        log.info(mapList.toString());
        publishEvents(hub, mapList);
        return super.processOutbound(config, mapList);
    }

    /**
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public void publishEvents(String eventHubName, List<Map<String, Object>> events) {
        // create a producer client
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();

        // sample events in an array

        List<EventData> allEvents = (List<EventData>) ((List) events).stream().map(e -> new EventData(GsonUtils.toJson(e))).collect(Collectors.toList());

        // create a batch
        EventDataBatch eventDataBatch = producer.createBatch();

        for (EventData eventData : allEvents) {
            // try to add the event from the array to the batch
            if (!eventDataBatch.tryAdd(eventData)) {
                // if the batch is full, send it and then create a new batch
                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        // send the last batch of remaining events
        if (eventDataBatch.getCount() > 0) {
            producer.send(eventDataBatch);
        }
        producer.close();
    }
}
