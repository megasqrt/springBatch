import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import ru.kan.otus.batch.config.MigrationBatchConfig;
import ru.kan.otus.batch.repositories.BooksMongoRepository;
import ru.kan.otus.batch.repositories.BooksSqlRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {MigrationBatchConfig.class})
public class BatchTest {
    private static final String IMPORT_USER_JOB_NAME = "";
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private BooksSqlRepository booksSqlRepository;
    @Autowired
    private BooksMongoRepository booksMongoRepository;

    @Autowired
    private Job migrateDataJob;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        assertEquals(booksSqlRepository.findAll().size(), 50);
        assertEquals(booksMongoRepository.findAll(), 0);
        Job job = jobLauncherTestUtils.getJob();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        assertEquals(jobExecution.getExitStatus().getExitCode(), "COMPLETED");

        assertEquals(booksMongoRepository.findAll(), 50);
    }

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("importDataJob", String.valueOf(System.currentTimeMillis()));
        return paramsBuilder.toJobParameters();
    }

}
