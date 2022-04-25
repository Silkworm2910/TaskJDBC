package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Олег", "Иванов", (byte) 63);
        userService.saveUser("Ольга", "Петрова", (byte) 13);
        userService.removeUserById(1);
        userService.saveUser("Илья", "Сидоров", (byte) 3);
        userService.saveUser("Макар", "Волков", (byte) 99);
        System.out.println(userService.getAllUsers().toString());
       // userService.cleanUsersTable();
       // userService.dropUsersTable();
        userService.closeConnection();
    }
}
