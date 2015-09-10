package webserver;

import converters.JsonConverter;
import datamodel.Job;
import datamodel.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rabbit.Sender;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

@RestController
public class RequestController {
    private Sender sender;

    @PostConstruct
    private void instantiateSender() {
        sender = new Sender();
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ResponseEntity<Job> jobRequest(@RequestBody String requestBody, HttpServletRequest httpRequest) {
        Request request = JsonConverter.jsonStringToObject(requestBody, Request.class);

        if ((request.getClientId() != null) && (request.getCommand() != null)) {
            String jobId = sender.send(request, httpRequest.getRemoteAddr());
            return new ResponseEntity<Job>(new Job(jobId), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<Job>(new Job("-1"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreDestroy
    private void closeSender() {
        sender.closeConnection();
    }
}