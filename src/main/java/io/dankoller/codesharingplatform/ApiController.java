package io.dankoller.codesharingplatform;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

// Mapping for the API controller
@RestController
public class ApiController {

    // Store code snippets
    private CodeSnippet snippet = new CodeSnippet();
    private ArrayList<CodeSnippet> allSnippets = new ArrayList<>();

    // Post new snippets
    @PostMapping("/api/code/new")
    public String postSnippet(@RequestBody CodeSnippet snippet) {
        snippet.setDateTime(LocalDateTime.now());
        this.snippet = snippet;
        allSnippets.add(this.snippet);
        return "Successfully uploaded " + snippet.getCode();
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
    public ArrayList<CodeSnippet> getAllSnippets() {
        return allSnippets;
    }
}
