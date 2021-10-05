package io.dankoller.codesharingplatform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

// Mapping for the API controller
@RestController
public class RequestController {

    // Store code snippets
    private CodeSnippet snippet = new CodeSnippet();
    private List<CodeSnippet> allSnippets = new ArrayList<>();

    // Post new snippets
    @PostMapping("/api/code/new")
    public String postSnippet(@RequestBody CodeSnippet snippet) {
        snippet.setDateTime(LocalDateTime.now());
        this.snippet = snippet;
        allSnippets.add(this.snippet);
        return "Successfully uploaded \"" + snippet.getCode() + "\"";
    }

    // Return the latest code snippet
    @GetMapping("/api/code/latest")
    public CodeSnippet getSnippet() {
        return snippet;
    }

    // Return the N most recent snippet
    @GetMapping("/api/code/latest/{id}")
    public CodeSnippet getRecents(@PathVariable int id) {
        return allSnippets.get(id);
    }

    // Return all snippets
    @GetMapping("/api/code/all")
    public Collection<CodeSnippet> getAllSnippets() {
        return allSnippets;
    }

    @Controller
    class WebInterface {
        // Return frontpage
        @GetMapping
        public String getTemplate(Model model) {
            model.addAttribute("codes", allSnippets);
            return "index";
        }
    }

}
