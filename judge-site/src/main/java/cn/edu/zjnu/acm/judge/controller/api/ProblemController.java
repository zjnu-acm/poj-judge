/*
 * Copyright 2016 ZJNU ACM.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.zjnu.acm.judge.controller.api;

import cn.edu.zjnu.acm.judge.data.form.ProblemForm;
import cn.edu.zjnu.acm.judge.domain.Problem;
import cn.edu.zjnu.acm.judge.service.ProblemService;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *
 * @author zhanhb
 */
@RequestMapping(value = "/api/problems", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
@Secured("ROLE_ADMIN")
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public Problem save(@RequestBody Problem problem) {
        return problemService.save(problem);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        problemService.delete(id);
    }

    @GetMapping("{id}/dataDir")
    public Map<String, String> dataDir(@PathVariable("id") long id) throws IOException {
        String dataDir = problemService.getDataDirectory(id).toString();
        return ImmutableMap.of("dataDir", dataDir);
    }

    @GetMapping("{id}")
    public Problem findOne(@PathVariable("id") long id,
            @RequestParam(value = "locale", required = false) @Nullable String lang) {
        return problemService.findOne(id, lang);
    }

    @GetMapping
    public Page<Problem> list(ProblemForm problemForm, @PageableDefault(100) Pageable pageable, Locale locale) {
        log.info("pageable: {}", pageable);
        return problemService.findAll(problemForm, null, pageable, locale);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") long problemId, @RequestBody Problem p,
            @RequestParam(value = "locale", required = false) @Nullable String requestLocale) {
        problemService.updateSelective(problemId, p, requestLocale);
    }

    @GetMapping("{id}/attachment")
    public List<String> attachment(@PathVariable("id") long problemId,
            @RequestParam(value = "locale", required = false) @Nullable String requestLocale) {
        return problemService.attachment(problemId, requestLocale);
    }

}
