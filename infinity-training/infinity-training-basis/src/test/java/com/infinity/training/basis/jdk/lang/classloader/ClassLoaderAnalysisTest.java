package com.infinity.training.basis.jdk.lang.classloader;

import com.infinity.training.basis.jdk.AnalysisTest;
import org.junit.Test;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/13 13:19
 */
public class ClassLoaderAnalysisTest extends ClassLoaderAnalysis implements AnalysisTest {

    @Test
    public void testMyClassLoader() throws Exception {
        super.myClassLoader();
    }
}
