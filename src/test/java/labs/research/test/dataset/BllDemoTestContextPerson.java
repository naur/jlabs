package labs.research.test.dataset;

import labs.entities.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 10/13/13.
 */
@Configuration
public class BllDemoTestContextPerson {
    @Bean
    public Person person() {
        Person person = new Person();
        person.setId(2900);
        person.setName("BllDemoTestContext Configuration Bean: person");
        return person;
    }
}
