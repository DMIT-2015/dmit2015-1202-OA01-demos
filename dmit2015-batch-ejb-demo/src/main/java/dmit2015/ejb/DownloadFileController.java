package dmit2015.ejb;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import javax.ejb.Timer;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;

@Named("currentDownloadFileController")
@RequestScoped
public class DownloadFileController {

    @NotBlank(message = "Download URI field value is required.")
    @Getter @Setter
    private String downloadUri;

    @NotBlank(message = "Download Directory field value is required.")
    @Getter @Setter
    private String downloadDirectory;

    @NotNull(message = "Scheduled Date Time field value is required.")
    @Future(message = "Scheduled Date Time field value must be in the future")
    @Getter @Setter
    private LocalDateTime scheduledDateTIme = LocalDateTime.now().plusMinutes(5);    // Set the default time to 15 minutes from now

    @Inject
    private DownloadFileTimerSessionBean _downloadCsvFileTimerSessionBean;

    public void scheduleFileDownload() {
       HashMap<String, String> info = new HashMap<>();
       info.put(DownloadFileTimerSessionBean.DOWNLOAD_URI, downloadUri);
       info.put(DownloadFileTimerSessionBean.DOWNLOAD_DIRECTORY, downloadDirectory);
       Timer timer =  _downloadCsvFileTimerSessionBean.createTimer(info, scheduledDateTIme);
       Messages.addGlobalInfo("Successfully created timer that will run at {0}", timer.getNextTimeout().toString());
    }

}