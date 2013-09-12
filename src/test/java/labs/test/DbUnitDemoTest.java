package labs.test;

import com.github.springtestdbunit.DbUnitRule;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Administrator on 9/12/13.
 */
public class DbUnitDemoTest extends UnitTestBase {
    @Rule
    public DbUnitRule dbUnit = new DbUnitRule();

    @Test
    @DatabaseSetup(value = "")
    public void test1() {
    }
}
