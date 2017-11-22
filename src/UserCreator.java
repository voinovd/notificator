import java.util.List;

public class UserCreator {

    public User create(){
        TaskCreator taskCreator = new TaskCreator();

        User user = new User();
        user.setName("Mike");
        user.setLogin("mikki");
        user.setPassword("mikki-pass");
        user.setPathToTasksFile("mikkiTaskList.txt");
        List<Task> userTasks = taskCreator.createTaskListForUserFromFile(user);
        user.setTasks(userTasks);
        return user;
    }
}