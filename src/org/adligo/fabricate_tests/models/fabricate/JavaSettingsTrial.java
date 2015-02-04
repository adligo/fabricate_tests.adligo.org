package org.adligo.fabricate_tests.models.fabricate;

import org.adligo.fabricate.models.common.FabricateDefaults;
import org.adligo.fabricate.models.fabricate.I_JavaSettings;
import org.adligo.fabricate.models.fabricate.JavaSettings;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.JavaType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=I_JavaSettings.class)
public class JavaSettingsTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDefaults() {
    JavaSettings js = new JavaSettings(null);
    assertEquals(FabricateDefaults.JAVA_XMS_DEFAULT, js.getXms());
    assertEquals(FabricateDefaults.JAVA_XMX_DEFAULT, js.getXmx());
    assertEquals(FabricateDefaults.JAVA_THREADS, js.getThreads());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorCopies() {
    JavaType jt = new JavaType();
    jt.setThreads(3);
    jt.setXms("12m");
    jt.setXmx("13m");
    JavaSettings js = new JavaSettings(jt);
    assertEquals("12m", js.getXms());
    assertEquals("13m", js.getXmx());
    assertEquals(3, js.getThreads());
  }
}
