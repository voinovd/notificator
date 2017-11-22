import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final String POSSIBLE_FORMATS = "Possible variants: today, tomorrow, date in format dd.mm.yyyy," +
            " current week, next week, current month, next month," +
            " current year, common period in format dd.mm.yyyy-dd.mm.yyyy.";

    public static void main(String[] args) {

        UserCreator userCreator = new UserCreator();
        User user = userCreator.create();

        System.out.println("All user tasks");
        for (Task task : user.getTasks() ) {
            System.out.println(task);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Enter time period for user " + user.getName() );
            System.out.println(POSSIBLE_FORMATS);

            String stringPeriod = br.readLine();
            DateTimeConverter converter = new DateTimeConverter();
            DateTimePeriod dateTimePeriod = converter.convertStringToDateTimePeriod(stringPeriod.trim());

            if ( dateTimePeriod != null) {
                user.viewTasksByDateTimePeriod(dateTimePeriod);
            } else {
                System.out.println("Invalid period format. You should use one of formats: \n" + POSSIBLE_FORMATS);
            }


        } catch ( IOException e){
            e.printStackTrace();
        }


    }

}