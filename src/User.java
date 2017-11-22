import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String login;
    private String password;
    private String pathToTasksFile;

    private List<Task> tasks = new ArrayList<>();

    public User() {
    }

    public void viewTasksByDateTimePeriod(DateTimePeriod dateTimePeriod) {
        int taskCount = getTaskCountForPeriod(dateTimePeriod);
        if ( taskCount == 0 ) {
            System.out.println( "No tasks in this period for user " + name );
            return;
        }

        System.out.println( "User " + name
                + " has " + taskCount + " tasks for period "
                + dateTimePeriod.getStartDateTime() + "-" + dateTimePeriod.getEndDateTime()
        );

        for ( Task task : tasks ) {
            if ( taskIsInPeriod(task, dateTimePeriod)) {
                System.out.println("*  " + task.getName());
            }
        }
    }

    private int getTaskCountForPeriod(DateTimePeriod dateTimePeriod) {
        int count = 0;
        for ( Task task : tasks ) {
            if ( taskIsInPeriod(task, dateTimePeriod)) {
                count++;
            }
        }
        return count;
    }

    private boolean taskIsInPeriod(Task task, DateTimePeriod dateTimePeriod) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathToTasksFile() {
        return pathToTasksFile;
    }

    public void setPathToTasksFile(String pathToTasksFile) {
        this.pathToTasksFile = pathToTasksFile;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}