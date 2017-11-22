import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    private static final String POSSIBLE_FORMATS = "Possible variants: today, tomorrow, date in format dd.mm.yyyy," +
            " current week, next week, current month, next month," +
            " current year, common period in format dd.mm.yyyy-dd.mm.yyyy.";

    public static void main(String[] args) {

        TaskCreator taskCreator = new TaskCreator();

        List<Task> tasks = taskCreator.createTaskListFromFile("mikkiTaskList.txt");

        System.out.println("All user tasks");
        for (Task task : tasks) {
            System.out.println(task);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

//            System.out.println("Enter time period for user " + user.getName() );
            System.out.println(POSSIBLE_FORMATS);

            String stringPeriod = br.readLine();
            DateTimeConverter converter = new DateTimeConverter();
            DateTimePeriod dateTimePeriod = converter.convertStringToDateTimePeriod(stringPeriod.trim());

            if (dateTimePeriod != null) {
                viewTasksByDateTimePeriod(dateTimePeriod, tasks);
            } else {
                System.out.println("Invalid period format. You should use one of formats: \n" + POSSIBLE_FORMATS);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewTasksByDateTimePeriod(DateTimePeriod dateTimePeriod, List<Task> tasks) {
        int taskCount = getTaskCountForPeriod(dateTimePeriod, tasks);
        if ( taskCount == 0 ) {
            System.out.println( "No tasks in this period for user "  );
            return;
        }

        System.out.println( "User "
                + " has " + taskCount + " tasks for period "
                + dateTimePeriod.getStartDateTime() + "-" + dateTimePeriod.getEndDateTime()
        );

        for ( Task task : tasks ) {
            if ( taskIsInPeriod(task, dateTimePeriod)) {
                System.out.println("*  " + task.getName());
            }
        }
    }

    private static int getTaskCountForPeriod(DateTimePeriod dateTimePeriod, List<Task> tasks) {
        int count = 0;
        for ( Task task : tasks ) {
            if ( taskIsInPeriod(task, dateTimePeriod)) {
                count++;
            }
        }
        return count;
    }

    private static boolean taskIsInPeriod(Task task, DateTimePeriod dateTimePeriod) {
        LocalDateTime taskStart = task.getStartDateTime();
        LocalDateTime periodStart = dateTimePeriod.getStartDateTime();
        LocalDateTime periodEnd = dateTimePeriod.getEndDateTime();

        if ( (taskStart.isAfter(periodStart) || taskStart.isEqual(periodStart) )
                && ( periodEnd.isAfter(taskStart) || periodEnd.isEqual(taskStart))) {
            return true;
        } else {
            return false;
        }
    }


}
