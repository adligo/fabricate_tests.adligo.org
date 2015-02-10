package org.adligo.fabricate_tests.models.fabricate;

import org.adligo.fabricate.common.system.FabricateDefaults;
import org.adligo.fabricate.models.fabricate.I_JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettingsMutant;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=JavaSettingsMutant.class, minCoverage=92.0)
public class JavaSettingsMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDefaults() {
    JavaSettingsMutant js = new JavaSettingsMutant((JavaType) null);
    assertEquals(FabricateDefaults.JAVA_XMS_DEFAULT, js.getXms());
    assertEquals(FabricateDefaults.JAVA_XMX_DEFAULT, js.getXmx());
    assertEquals(FabricateDefaults.JAVA_THREADS, js.getThreads());
  }
  
  @SuppressWarnings({"unused"})
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(NullPointerException.class),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            new JavaSettingsMutant((I_JavaSettings) null);
          }
        }); 
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopies() {
    JavaType jt = new JavaType();
    jt.setThreads(3);
    jt.setXms("12m");
    jt.setXmx("13m");
    JavaSettingsMutant js = new JavaSettingsMutant(jt);
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    assertEquals(3, js.getThreads());
    
    js = new JavaSettingsMutant(new JavaSettings(jt));
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    assertEquals(3, js.getThreads());
    
    js = new JavaSettingsMutant(new JavaSettingsMutant());
    assertEquals(FabricateDefaults.JAVA_XMS_DEFAULT, js.getXms());
    assertEquals(FabricateDefaults.JAVA_XMX_DEFAULT, js.getXmx());
    assertEquals(FabricateDefaults.JAVA_THREADS, js.getThreads());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() {
    JavaSettingsMutant jt = new JavaSettingsMutant();
    jt.setThreads(3);
    jt.setXms("12m");
    jt.setXmx("13m");
    
    assertEquals(3, jt.getThreads());
    assertEquals("12m", jt.getXms());
    assertEquals("13m", jt.getXmx());
  }
}
