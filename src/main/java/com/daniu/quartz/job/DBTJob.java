package com.daniu.quartz.job;

import com.daniu.quartz.task.DefaultTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 新建 Quartz的Job 任务
 */
@Slf4j
public class DBTJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DefaultTask.timeTell();
    }
}
