package dmit2015.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class LottoCanada {

    // Define a new data type named LottoType with two possible values
    public enum LottoType {LOTTO_MAX, LOTTO_649};

    public static final int LOTTO_MAX_HIGHEST_NUMBER = 50;
    public static final int LOTTO_649_HIGHEST_NUMBER = 49;
    public static final int LOTTO_MAX_SELECTIONS = 7;
    public static final int LOTTO_649_SELECTIONS = 6;

    /** A unique system assigned identifier */
    private UUID ticketId;

    /** The date and time the ticket was issued */
    private LocalDateTime issuedDateTime;

    /** The list of quick pick numbers. Quick pick numbers is an array of numbers*/
    private List<Integer[]> quickPicksNumbers;

    public LottoCanada(LottoType lottoType, int pickCount) {
        ticketId = UUID.randomUUID();
        issuedDateTime = LocalDateTime.now();
        // write code here or call a method to generate the list of quick pick numbers
    }

}
