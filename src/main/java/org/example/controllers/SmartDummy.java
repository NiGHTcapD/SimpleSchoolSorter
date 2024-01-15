package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SmartDummy {//it's a dummy, but it's not stupid. It exists as an endpoint; it doesn't do nothing, but it Does Nothing

    @PostMapping("new-index")
    public String doNoThing() {
        return "unused";
    }

}
