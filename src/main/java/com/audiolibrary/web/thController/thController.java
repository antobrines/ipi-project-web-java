package com.audiolibrary.web.thController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/th", method = RequestMethod.GET)
public class thController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(final ModelMap model) {
        return "home";
    }
}
