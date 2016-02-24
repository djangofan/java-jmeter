package qa.test;

import org.apache.jorphan.collections.HashTree;
import org.testng.annotations.Test;

public class JmeterTest
{
    JmeterManager jmeterManager;

    @Test
    public void testJmeterExample()
    {
        jmeterManager = new JmeterManager();

        HashTree testPlanTree = jmeterManager.generateTestPlan("TestPlanName", "WhateverThreadGroup");

        jmeterManager.runSuite(testPlanTree);
    }

}
