package io.dankoller.codesharingplatform.controller;

import io.dankoller.codesharingplatform.entity.Code;
import io.dankoller.codesharingplatform.persistence.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Web Interface for the app
@Controller
public class WebController {

    @Autowired
    private CodeService codeService;

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
