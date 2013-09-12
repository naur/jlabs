package labs.test;

import labs.test.bllunit.BllUnitRule;
import labs.test.bllunit.annotation.BllBefore;
import labs.test.models.Person;
import labs.test.models.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 9/12/13.
 */
public class BllDemoTest extends UnitTestBase {
    @Rule
    public BllUnitRule rule = new BllUnitRule();

    @Test
    @BllBefore(value = {"labs.test.models.User", "labs.test.models.Person"})
    public void test1() {
        User user = rule.getBean(User.class);
        Assert.assertNotNull(user);
        System.out.println(user.toString());

        Person person = rule.getBean(Person.class);
        Assert.assertNotNull(person);
        System.out.println(person.toString());
    }

    @Test
    @BllBefore(value = {"labs.test.models.Person"})
    public void test2() {
        User user = rule.getBean(User.class);
        Assert.assertNull(user);

        Person person = rule.getBean(Person.class);
        Assert.assertNotNull(person);
        System.out.println(person.toString());
    }
}
