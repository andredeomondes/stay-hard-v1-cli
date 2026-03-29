import controller.HabitController;
import controller.UserController;
import repository.HabitRepository;
import repository.UserRepository;
import repository.csv.CsvHabitRepository;
import repository.csv.CsvUserRepository;
import service.HabitService;
import service.LevelService;
import service.UserService;
import ui.UserMenus;

public class StayHardApp {
    public static void main(String[] args) {

        HabitRepository habitRepository = new CsvHabitRepository("data/habits.csv");
        UserRepository userRepository = new CsvUserRepository("data/user.csv", "Player");

        HabitService habitService = new HabitService(habitRepository);
        UserService userService = new UserService(userRepository);
        LevelService levelService = new LevelService();

        HabitController habitController = new HabitController(habitService);
        UserController userController = new UserController(userService, levelService);

        UserMenus menu = new UserMenus(habitController, userController);
        menu.start();
    }
}