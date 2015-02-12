package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.log.DeferredLog;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.BufferedInputStream;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.ProcessBuilderWrapper;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@SourceFileScope (sourceClass=FabSystem.class, minCoverage=80.0)
public class FabSystemTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() throws Exception {
    FabSystem fabSystem = new FabSystem();
    
    I_FabLog log = mock(I_FabLog.class);
    fabSystem.setLog(log);
    assertNotSame(log, fabSystem.getLog());
    assertSame(log, ((DeferredLog) fabSystem.getLog()).getDelegate());
    
    fabSystem.setArgs(Collections.singletonMap("k",null));
    assertEquals(" k", fabSystem.toScriptArgs());
    assertNull(fabSystem.getArgValues("k"));
    
    fabSystem.setArgs(Collections.singletonMap("k", "v"));
    assertEquals("v", fabSystem.getArgValue("k"));
    assertNull(fabSystem.getArgValue("v"));
    assertTrue(fabSystem.hasArg("k"));
    assertFalse(fabSystem.hasArg("v"));
    List<String> vals = fabSystem.getArgValues("k");
    assertContains(vals, "v");
    assertEquals(1, vals.size());
    assertEquals(" k=v", fabSystem.toScriptArgs());
    
    fabSystem.setArgs(Collections.singletonMap("k2", "v1,v2,v3"));
    vals = fabSystem.getArgValues("k2");
    assertContains(vals, "v1");
    assertContains(vals, "v2");
    assertContains(vals, "v3");
    assertEquals(3, vals.size());
    assertEquals(" k2=v1,v2,v3", fabSystem.toScriptArgs());
  }
  
  @Test
  public void testMethodsFactories() throws Exception {
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
    
    assertSame(Thread.currentThread(), fabSystem.currentThread());
    ByteArrayOutputStream baos = fabSystem.newByteArrayOutputStream();
    assertNotNull(baos);
    assertNotSame(baos, fabSystem.newByteArrayOutputStream());
    
    ProcessBuilderWrapper pbw = fabSystem.newProcessBuilder(new String[] {""});
    assertNotNull(pbw);
    ProcessBuilder pb = pbw.getDelegate();
    assertNotNull(pb);
    
    ProcessBuilderWrapper pbw2 = fabSystem.newProcessBuilder(new String [] {""});
    assertNotNull(pbw2);
    ProcessBuilder pb2 = pbw2.getDelegate();
    assertNotNull(pb2);
    assertNotSame(pb, pb2);
    
    InputStream in = mock(InputStream.class);
    BufferedInputStream bis = fabSystem.newBufferedInputStream(in);
    assertNotNull(bis);
    assertSame(in,  bis.getDelegate());
  }
}
