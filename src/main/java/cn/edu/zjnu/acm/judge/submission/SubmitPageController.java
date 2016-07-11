package cn.edu.zjnu.acm.judge.submission;

import cn.edu.zjnu.acm.judge.mapper.UserPerferenceMapper;
import cn.edu.zjnu.acm.judge.service.LanguageService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubmitPageController {

    @Autowired
    private UserPerferenceMapper userPerferenceMapper;
    @Autowired
    private LanguageService languageService;

    @Secured("ROLE_USER")
    @RequestMapping(value = {"/submitpage", "/submit"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String submitpage(HttpServletRequest request,
            @RequestParam(value = "problem_id", required = false) Long problemId,
            @RequestParam(value = "contest_id", required = false) Long contestId,
            Authentication authentication) {
        request.setAttribute("contestId", contestId);
        request.setAttribute("problemId", problemId);
        request.setAttribute("languages", languageService.getLanguages().values());
        String user = authentication != null ? authentication.getName() : null;
        int languageId = userPerferenceMapper.getLanguage(user);
        request.setAttribute("languageId", languageId);
        return "submit";
    }

}
