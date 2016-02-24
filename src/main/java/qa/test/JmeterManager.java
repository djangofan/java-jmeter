package qa.test;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JmeterManager
{
    public StandardJMeterEngine jmeter;

    public JmeterManager()
    {
        jmeter = new StandardJMeterEngine();

        JMeterUtils.loadJMeterProperties("src/test/resources/jmeter.properties");

        //JMeterUtils.initLogging(); // you can comment this line out to see extra log messages of i.e. DEBUG level

        JMeterUtils.initLocale();
    }

    public void runSuite(HashTree testPlan)
    {
        jmeter.configure(testPlan);
        jmeter.run();
    }

    public ThreadGroup getThreadGroup(String name)
    {
        org.apache.jmeter.threads.ThreadGroup threadGroup = new org.apache.jmeter.threads.ThreadGroup();
        threadGroup.setName(name);
        threadGroup.setNumThreads(5);
        threadGroup.setRampUp(1);
        return threadGroup;
    }

    public HTTPSampler getHttpSampler()
    {
        HTTPSampler httpSampler = new HTTPSampler();
        httpSampler.setDomain("google.com");
        httpSampler.setPort(80);
        httpSampler.setPath("/");
        httpSampler.setMethod("GET");
        return httpSampler;
    }

    public LoopController getLoopController()
    {
        LoopController loopController = new LoopController();
        loopController.setLoops(1);
        loopController.addTestElement(getHttpSampler());
        loopController.setFirst(true);
        loopController.initialize();
        return loopController;
    }

    public HashTree generateTestPlan(String testPlanName, String threadGroupName)
    {
        HashTree testPlanTree = new HashTree();
        LoopController loopController = getLoopController();
        org.apache.jmeter.threads.ThreadGroup threadGroup = getThreadGroup(threadGroupName);
        threadGroup.setSamplerController(loopController);
        TestPlan testPlan = new TestPlan(testPlanName);
        testPlanTree.add("testPlan", testPlan);
        testPlanTree.add("loopController", loopController);
        testPlanTree.add("threadGroup", threadGroup);
        testPlanTree.add("httpSampler", getHttpSampler());
        return testPlanTree;
    }

}
