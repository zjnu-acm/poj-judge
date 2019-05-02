package cn.edu.zjnu.acm.judge.service.impl;

import cn.edu.zjnu.acm.judge.mapper.SubmissionMapper;
import cn.edu.zjnu.acm.judge.service.JudgePoolService;
import cn.edu.zjnu.acm.judge.service.RejudgeService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rejudgeService")
public class RejudgeServiceImpl implements RejudgeService {

    private final JudgePoolService judgePoolService;
    private final SubmissionMapper submissionMapper;

    @Autowired
    public RejudgeServiceImpl(JudgePoolService judgePoolService, SubmissionMapper submissionMapper) {
        this.judgePoolService = judgePoolService;
        this.submissionMapper = submissionMapper;
    }

    @Override
    public CompletableFuture<?> byProblemId(long problemId) {
        List<Long> submissions = submissionMapper.findAllByProblemIdAndResultNotAccept(problemId);
        return judgePoolService.addAll(submissions.stream().mapToLong(Long::longValue).toArray());
    }

    @Override
    public CompletableFuture<?> bySubmissionId(long submissionId) {
        return judgePoolService.add(submissionId).thenApply(__ -> submissionMapper.findOne(submissionId));
    }

}
