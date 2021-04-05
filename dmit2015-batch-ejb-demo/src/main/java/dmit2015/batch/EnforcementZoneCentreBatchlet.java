package dmit2015.batch;

import dmit2015.entity.EnforcementZoneCentre;
import dmit2015.repository.EnforcementZoneCentreRepository;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Batchlets are task oriented step that is called once.
 * It either succeeds or fails. If it fails, it CAN be restarted and it runs again.
 */
@Named
public class EnforcementZoneCentreBatchlet extends AbstractBatchlet {

    @Inject
    private EnforcementZoneCentreRepository _repository;

    @Inject
    private JobContext _jobContext;

    /**
     * Perform a task and return "COMPLETED" if the job has successfully completed
     * otherwise return "FAILED" to indicate the job failed to complete.
     */
    @Transactional
    @Override
    public String process() throws Exception {
        Properties jobParameters = _jobContext.getProperties();
        String inputFile = jobParameters.getProperty("input_file");

        // For reading external files outside of the project use the code below:
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile())))	{
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(_inputFile))))	{
            String line;
            final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            // Skip the first line as it is containing column headings
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(delimiter);

                EnforcementZoneCentre model = new EnforcementZoneCentre();
                model.setSiteId(Short.parseShort(values[0]));
                model.setLocationDescription(values[1]);
                model.setSpeedLimit(Short.parseShort(values[2]));
                model.setReasonCodes(values[3].replaceAll("[\"()]", ""));
                model.setLatitude(Double.valueOf(values[4]));
                model.setLongitude(Double.valueOf(values[5]));

                Point<G2D> geoLocation = DSL.point(CoordinateReferenceSystems.WGS84, DSL.g(model.getLongitude(), model.getLatitude()));
                model.setGeoLocation(geoLocation);

                // if the record exists update the record otherwise create a new record
                if (_repository.findOneById(model.getSiteId()).isEmpty()) {
                    _repository.create(model);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "FAILED";
        }

        return "COMPLETED";        // The job has successfully completed
    }
}