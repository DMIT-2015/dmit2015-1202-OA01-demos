package dmit2015.ejb;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * A stateless session bean for creating a EJB Timer and for handling the event when the timer expires.
 *
 * To use this session session from another managed class, simply use the @Inject annotation to inject an instance of this bean:
 *
 *  @Inject
 *  private DownloadCsvFileTimerSessionBean _downloadTimerBean;
 *
 */
@Stateless  // Must be @Stateless or @Singleton. Timer service does not support Stateful session beans
public class DownloadFileTimerSessionBean {

    public static final String DOWNLOAD_URI = "downloadUri";
    public static final String DOWNLOAD_DIRECTORY = "downloadDirectory";

    @Resource   // This is a container created resource
    private TimerService _timerService;

   private Logger _logger = Logger.getLogger(DownloadFileTimerSessionBean.class.getName());
    // @Inject // Use only if your project includes a LoggerProducer
    // private Logger _logger;

    /**
     * The annotation @Timeout method is executed when a programmatic timer expires
     * @param timer contains information about the timer that expired
     */
    @Timeout
    public void timeout(Timer timer) {
        HttpClient client = HttpClient.newHttpClient();
        HashMap<String, String> info = (HashMap<String, String>) timer.getInfo();
        String downloadUriString = info.get(DOWNLOAD_URI);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(downloadUriString))
                .build();
        String downloadDirectory = info.get(DOWNLOAD_DIRECTORY);
        Path downloadPath = Path.of(downloadDirectory);
        try {
            HttpResponse<Path> response = client.send(request,
                    HttpResponse.BodyHandlers.ofFileDownload(downloadPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
            _logger.info("Finished download file to " + response.body());
        } catch (Exception e) {
            _logger.fine("Error downloading file. " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create a new Timer with info that contains an HashMap of the downloadUri and the downloadDirectory
     * @param info an HashMap with value for the downloadUri and downloadDirectory
     * @param eventDateTime the start date time of the event
     * @return
     */
    public Timer createTimer(HashMap<String, String> info, LocalDateTime eventDateTime) {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(info);
        ScheduleExpression eventScheduleExpression = TimerServiceHelper.toScheduleExpression(eventDateTime);
        return _timerService.createCalendarTimer(eventScheduleExpression, timerConfig);
    }

}