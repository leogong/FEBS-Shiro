package cc.mrbird.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author leo on 13/01/2018.
 */
@Controller
public class Index {
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Yep! You finally found me.";
    }
}
