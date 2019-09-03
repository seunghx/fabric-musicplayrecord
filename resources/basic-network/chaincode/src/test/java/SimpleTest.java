import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SimpleTest {

    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse("2019-08-08 00:00:00", formatter);
        LocalDateTime nextDate = date.plusDays(2);

        System.out.println(ChronoUnit.DAYS.between(date, nextDate));
    }
}
