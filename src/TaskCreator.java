import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskCreator {

    public static final String FILE_SEPARATOR = ";";

    public List<Task> createTaskListForUserFromFile(User user) {
        List<Task> tasks = new ArrayList<>();

        String pathToFile = user.getPathToTasksFile();
        if ( pathToFile == null ) {
            return tasks;
        }


        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line = null;
            while ( (line = br.readLine()) != null ) {
                String[] parts = line.trim().split(FILE_SEPARATOR);
                if ( parts.length == 5 ) {
                    Task task = createNewTask(parts);
                    tasks.add(task);
                }
            }
        } catch ( IOException e){
            e.printStackTrace();
        }

        return tasks;
    }

    private Task createNewTask(String[] parts) {
        Task task = new Task();
        task.setName( parts[0].trim() );
        task.setStartDateTime( parseStringToLocalDateTime( parts[1].trim() ) );
        task.setEndDateTime( parseStringToLocalDateTime( parts[2].trim() ) );
        task.setNotificationDateTime( parseStringToLocalDateTime( parts[3].trim() ) );
        task.setDescription( parts[4].trim() );
        return task;
    }

    private LocalDateTime parseStringToLocalDateTime(String stringLocalDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(stringLocalDateTime, formatter);
            return dateTime;
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }
}