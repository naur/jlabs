package labs.test;

import labs.test.bllunit.BllUnitRule;
import labs.test.bllunit.annotation.BllBefore;
import labs.test.models.Person;
import labs.test.models.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Administrator on 9/12/13.
 */
public class BllDemoTest extends UnitTestBase {
    @Rule
    public BllUnitRule rule = new BllUnitRule();

    @Test
    @BllBefore(value = "")
    public void test1() {
        User user = rule.getBean(User.class);
        Assert.assertNotNull(user);

        Person person = rule.getBean(Person.class);
        Assert.assertNotNull(person);
    }
}
