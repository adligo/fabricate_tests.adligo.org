package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.log.DeferredLog;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Locale;

@SourceFileScope (sourceClass=FabSystem.class, minCoverage=71.0)
public class FabSystemTrial extends MockitoSourceFileTrial {

  @Test
  public void testIO() throws Exception {
    FabSystem fabSystem = new FabSystem();
    
    long ft = fabSystem.getCurrentTime();
    long now = System.currentTimeMillis();
    assertGreaterThanOrEquals(ft, now);
    assertTrue(ft -1000 < now);
    
    assertNull(fabSystem.getenv("nullvoid"));
    assertEquals(System.lineSeparator(), fabSystem.lineSeperator());
    
    assertEquals(Locale.getDefault().getLanguage(), fabSystem.getDefaultLanguage());
    assertEquals(Locale.getDefault().getCountry(), fabSystem.getDefaultCountry());
    
    assertEquals(FabFileIO.class.getName(), fabSystem.getFileIO().getClass().getName());
    assertEquals(FabXmlFileIO.class.getName(), fabSystem.getXmlFileIO().getClass().getName());
    
    I_FabLog log = mock(I_FabLog.class);
    fabSystem.setLog(log);
    assertNotSame(log, fabSystem.getLog());
    assertSame(log, ((DeferredLog) fabSystem.getLog()).getDelegate());
  }
}
