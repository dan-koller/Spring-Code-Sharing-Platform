package io.dankoller.codesharingplatform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

// Mapping for the web interface (UI) controller
@Controller
public class WebController {

    // Return frontpage
    @GetMapping
    public String getTemplate(Model model) {
        model.addAttribute("code", Arrays.asList(
                new CodeSnippet().getCode()
        ));
        return "index";
    }
}
