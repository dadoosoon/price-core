package im.dadoo.price.core;

import im.dadoo.price.core.configuration.PriceCoreContext;
import im.dadoo.price.core.service.BrandService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//      ApplicationContext application = new AnnotationConfigApplicationContext(PriceCoreContext.class);
//      BrandService bs = application.getBean(BrandService.class);
//      System.out.println(bs.list());
//      assertTrue( true );
    }
}
