package labs.test;

import labs.entities.Person;
import labs.entities.User;
import labs.test.bllunit.BllUnitRule;
import labs.test.bllunit.annotation.BllBefore;
import labs.test.dataset.BllDemoTestContext;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Administrator on 9/12/13.
 */
public class BllDemoTest extends UnitTestBase {
    @Rule
    public BllUnitRule rule = new BllUnitRule();

    @Test
    @BllBefore(config = {labs.test.dataset.BllDemoTestContext.class})
    public void testConfig() {
        User user = rule.getBean(User.class);
        Assert.assertNotNull(user);
        System.out.println(user.toString());

        Person person = rule.getBean(Person.class);
        Assert.assertNotNull(person);
        System.out.println(person.toString());
    }

    @Test
    @BllBefore(xml = {"classpath:dataset/demo.xml"})
    public void testXml() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BllDemoTestContext.class);
        User user1 = ctx.getBean(User.class);
        Assert.assertNotNull(user1);
        System.out.println(user1.toString());
    }

    @Test
    @BllBefore(
            config = {labs.test.dataset.BllDemoTestContext.class},
            xml = {"classpath:dataset/demo.xml"}
    )
    public void testConfigAndXml() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BllDemoTestContext.class);
        User user1 = ctx.getBean(User.class);
        Assert.assertNotNull(user1);
        System.out.println(user1.toString());
    }

}
