package test.java.com.ofs.testcases;
import org.testng.*;
import main.java.*;
import java.util.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import main.java.generics.Listeners;

public class TestNGRunner {
    public static void main(String[] args) {

        List<XmlSuite> Suites = new ArrayList<XmlSuite>();
        List<XmlClass> classes = new ArrayList<XmlClass>();
        List<Class> listenerClasses = new ArrayList<Class>();
        ArrayList<String> listeners = new ArrayList<>();
        listeners.add("main.java.generics.Listeners");

        XmlSuite suite = new XmlSuite();
        suite.setName("Regression");
        suite.setListeners(listeners);
        Map<String, String> testClassParameters = new HashMap<>();
        testClassParameters.put("targetDriver", "Chrome");

        suite.setParameters(testClassParameters);

        XmlTest test = new XmlTest(suite);
        test.setName("OFS Selenium");

        XmlClass class1 = new XmlClass("test.java.com.ofs.testcases.DemoSmokeTestCase");
        classes.add(class1);

        XmlClass class2 = new XmlClass("test.java.com.ofs.testcases.DemoSanityTest");
        classes.add(class2);

        test.setXmlClasses(classes);
        //suite.setParallel(XmlSuite.ParallelMode.getValidParallel("tests"));
        Suites.add(suite);
        //listenerClasses.add(Listeners.class);

        TestNG tng = new TestNG();
        tng.setXmlSuites(Suites);
        tng.run();

    }
}