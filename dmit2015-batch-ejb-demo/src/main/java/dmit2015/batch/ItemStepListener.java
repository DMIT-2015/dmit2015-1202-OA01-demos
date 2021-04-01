package dmit2015.batch;

import javax.batch.api.listener.AbstractStepListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * This executes before and after a step execution runs.
 * To apply this listener to a batch job you must define a listener element in the Job Specification Language (JSL) file
 * BEFORE the step element as follows:
 *
 * <listeners>
 * <listener ref="itemStepListener" />
 * </listeners>
 */
@Named
public class ItemStepListener extends AbstractStepListener {

    @Inject
    private JobContext _jobContext;
    private long startTime;

    @Override
    public void beforeStep() throws Exception {
        System.out.println("beforeStep");
        startTime = System.currentTimeMillis();

        // Delete the output if it exists

        Properties jobParameters = _jobContext.getProperties();
        String output_file = jobParameters.getProperty("output_file");

        Path outputFilePath = Paths.get(output_file);
        Files.deleteIfExists(outputFilePath);
    }

    @Override
    public void afterStep() throws Exception {
        System.out.println("afterStep");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        String message = _jobContext.getJobName() + " completed in " + duration + " milliseconds";
        System.out.println(message);

    }

}