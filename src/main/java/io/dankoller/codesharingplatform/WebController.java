package io.dankoller.codesharingplatform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

// Mapping for the web interface (UI) controller
@Controller
public class WebController {

    // Return frontpage
    @GetMapping
    public String getTemplate(@ModelAttribute CodeSnippet snippet, Model model) {
        model.addAttribute("code", snippet.getCode());
        model.addAttribute("time", snippet.getDateTime());
        return "index";
    }
}
