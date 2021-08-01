package com.harshajayamanna.apiresources;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class HomeController {

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = { "application/json",
            "application/xml" })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Set<String> healthCheck() {
        return Collections.singleton("API is OKAY.");
    }

    @PreAuthorize("hasRole('ROLE_ROLE_ADMIN')")
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = { "application/json",
            "application/xml" })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, String> get() {

        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost()
                    .getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "Response from API");
        map.put("host", hostname);
        map.put("status", "up");
        return map;
    }

}
