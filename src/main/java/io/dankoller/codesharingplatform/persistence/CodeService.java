package io.dankoller.codesharingplatform.persistence;

import io.dankoller.codesharingplatform.entity.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public void saveCode(Code code) {
        codeRepository.save(code);
    }

    @Transactional
    public void deleteCode(Code code) {
        codeRepository.delete(code);
    }

    public Code findCodeByUuid(String uuid) {
        return codeRepository.findCodeByUuid(uuid);
    }

    public List<Code> findTenRecentCodes() {
        List <Code> repoCodeList = codeRepository.findAll();
        List <Code> publicCodeList = new ArrayList<>();

        // Only display non ristricted snippets
        for (int i = 0; i < repoCodeList.size(); i++) {
            Code temp = repoCodeList.get(i);

            if (!temp.isRestrictedByTime() && !temp.isRestrictedByViews()) {
                publicCodeList.add(repoCodeList.get(i));
            }
        }

        return publicCodeList.stream().sorted(Comparator.comparing(Code::getDate).reversed()).limit(10).collect(Collectors.toList());
    }

    public void updateTimeAndViews(Code code) {

        // Update view restriction
        if (code.isRestrictedByViews()) {
            if (code.getViews() == 0) {
                codeRepository.delete(code);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            code.setViews(code.getViews() - 1);
            codeRepository.save(code);
        }

        // Update time restriction
        if (code.isRestrictedByTime()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime uploadDate = LocalDateTime.parse(code.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

            int duration = (int) Duration.between(uploadDate, now).toSeconds();

            // Check if view time has passed
            long difference = LocalDateTime.now().until(uploadDate.plusSeconds(code.getTime()), ChronoUnit.SECONDS);
            if (difference <= 0) {
                codeRepository.delete(code);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            code.setTime((int) difference);
        }
    }
}
