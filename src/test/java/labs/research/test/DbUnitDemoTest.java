package labs.research.test;

import com.github.springtestdbunit.DbUnitRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 9/12/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DbUnitDemoTest {
    @Rule
    public DbUnitRule dbUnit = new DbUnitRule();

    @Test
    public void test1() {
    }
}
