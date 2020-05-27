package ru.kan.otus.batch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BatchService {

    private final JobLauncher jobLauncher;
    private final Job migrateDataJob;

    public void launchJob() {
        try {
            jobLauncher.run(migrateDataJob, new JobParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
