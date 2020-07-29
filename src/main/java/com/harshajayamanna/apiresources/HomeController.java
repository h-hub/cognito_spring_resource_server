package com.harshajayamanna.apiresources;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
public class HomeController {

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = { "application/json",
            "application/xml" })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Set<String> healthCheck() {
        return Collections.singleton("Morning Core API : Status is OK2");
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ROASTER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = { "application/json",
            "application/xml" })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Set<String> get() {
        return Collections.singleton("Test");
    }

}
