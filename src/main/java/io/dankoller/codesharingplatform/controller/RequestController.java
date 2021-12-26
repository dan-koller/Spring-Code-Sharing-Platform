package io.dankoller.codesharingplatform.controller;

import io.dankoller.codesharingplatform.entity.Code;
import io.dankoller.codesharingplatform.persistence.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class RequestController {

    @Autowired
    private CodeService codeService;

    /* API specific code below */

    // Return posted snippets via API
    @GetMapping("/api/code/{uuid}")
    public Code getCode(@PathVariable String uuid) {

        Code _code = codeService.findCodeByUuid(uuid);

        if (_code == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (_code.isRestrictedByTime() || _code.isRestrictedByViews()) {
            codeService.updateTimeAndViews(_code);
        }

        return _code;
    }

    // Post new code snippets via the API
    @PostMapping(path = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> setCode(@RequestBody Code code) {
        // Check for empty submissions
        if (!code.getCode().isEmpty()) {
            code.setDate(LocalDateTime.now());

            // Set restrictions
            code.setRestrictedByTime(code.getTime() != 0);
            code.setRestrictedByViews(code.getViews() != 0);

            // Create UUID for each uploaded code
            code.setUuid(UUID.randomUUID().toString());

            // Add code to db
            codeService.saveCode(code);
        }

        return Collections.singletonMap("id", code.getUuid());
    }

    // Return the 10 most recent snippets as JSON Array
    @GetMapping("/api/code/latest")
    public List<Code> getLatest() {
        return codeService.findTenRecentCodes();
    }

    // Web Interface for the app
    @Controller
    class WebInterface {
        // Main page
        @GetMapping("/")
        public String getIndexHtml(Model model) {
            List<Code> list = codeService.findTenRecentCodes();
            model.addAttribute("list", list);
            return "index";
        }

        // Get the n-th uploaded code snippet
        @GetMapping("/code/{uuid}")
        public String getCodeHtml(Model model, @PathVariable String uuid) {

            Code _code = codeService.findCodeByUuid(uuid);

            if (_code == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            if (_code.isRestrictedByTime() || _code.isRestrictedByViews()) {
                codeService.updateTimeAndViews(_code);
            }

            model.addAttribute("code", _code);
            return "code";
        }

        // Return the 10 most recent snippet via HTML
        @GetMapping("/code/latest")
        public String getLatestHtml(Model model) {
            List<Code> latest = codeService.findTenRecentCodes();
            model.addAttribute("latest", latest);
            return "latest";
        }

        // Post new code snippets page
        @GetMapping("code/new")
        public String getNewHtml() {
            return "new";
        }
    }

}
