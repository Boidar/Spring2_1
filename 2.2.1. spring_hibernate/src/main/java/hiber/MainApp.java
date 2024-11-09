package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      User user1 = new User("alexey", "anatolevich", "pdfg@mail.ru");
      User user2 = new User("dima", "alexeevich", "tdfg@mail.ru");
      Car car1 = new Car("Mazda", 2022);
      Car car2 = new Car("Nissan", 2008);
      userService.add(car1);
      userService.add(car2);
      user1.setCar(car1);
      user2.setCar(car2);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));
      userService.add(user1);
      userService.add(user2);


      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         if (user.getCar() != null) {
            System.out.println("Car: " + user.getCar().getModel() + ", " + user.getCar().getSeries());
         }
         System.out.println();
      }
      System.out.println(userService.getUserByModAndSer("Mazda", 2022).toString());
      context.close();
   }
}
