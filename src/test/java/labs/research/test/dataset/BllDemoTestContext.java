package labs.research.test.dataset;

import labs.entities.Person;
import labs.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 10/13/13.
 */
@Configuration
public class BllDemoTestContext {
    @Bean
    public Person person() {
        Person person = new Person();
        person.setId(2900);
        person.setName("BllDemoTestContext Configuration Bean: person");
        return person;
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId(2800);
        user.setName("BllDemoTestContext Configuration Bean: user");
        return user;
    }
}
