package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.log.DeferredLog;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.BufferedInputStream;
import org.adligo.fabricate.common.system.ComputerInfoDiscovery;
import org.adligo.fabricate.common.system.ExecutingProcess;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.I_LocatableRunnable;
import org.adligo.fabricate.common.system.I_ProcessBuilderWrapper;
import org.adligo.fabricate.common.system.I_ProcessRunnable;
import org.adligo.fabricate.common.system.I_RunMonitor;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@SourceFileScope (sourceClass=FabSystem.class, minCoverage=70.0,
    allowedCircularDependencies=CircularDependencies.AllowInnerOuterClasses)
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
    assertEquals(0, fabSystem.getArgValues("k").size());
    
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
    
    assertEquals(Runtime.getRuntime().availableProcessors(), fabSystem.getAvailableProcessors());
    assertEquals(System.getProperty("java.version", "Unknown"), 
        fabSystem.getProperty("java.version", "Unknown"));
    
    assertEquals(File.pathSeparator, fabSystem.pathSeparator());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsFactories() throws Exception {
    FabSystem fabSystem = new FabSystem();
    
    //array blocking queue
    ArrayBlockingQueue<Boolean> queue = fabSystem.newArrayBlockingQueue(Boolean.class, 1);
    assertNotNull(queue);
    queue.put(true);
    assertTrue(queue.take());
    
    long ft = fabSystem.getCurrentTime();
    long now = System.currentTimeMillis();
    assertGreaterThanOrEquals(ft, now);
    assertTrue(ft -1000 < now);
    
    assertNull(fabSystem.getenv("nullvoid"));
    assertEquals(System.lineSeparator(), fabSystem.lineSeparator());
    
    assertEquals(Locale.getDefault().getLanguage(), fabSystem.getDefaultLanguage());
    assertEquals(Locale.getDefault().getCountry(), fabSystem.getDefaultCountry());
    
    assertEquals(FabFileIO.class.getName(), fabSystem.getFileIO().getClass().getName());
    assertEquals(FabXmlFileIO.class.getName(), fabSystem.getXmlFileIO().getClass().getName());
    
    assertSame(Thread.currentThread(), fabSystem.currentThread());
    ByteArrayOutputStream baos = fabSystem.newByteArrayOutputStream();
    assertNotNull(baos);
    assertNotSame(baos, fabSystem.newByteArrayOutputStream());
    
    I_ProcessBuilderWrapper pbw = fabSystem.newProcessBuilder(new String[] {""});
    assertNotNull(pbw);
    ProcessBuilder pb = pbw.getDelegate();
    assertNotNull(pb);
    
    I_ProcessBuilderWrapper pbw2 = fabSystem.newProcessBuilder(new String [] {""});
    assertNotNull(pbw2);
    ProcessBuilder pb2 = pbw2.getDelegate();
    assertNotNull(pb2);
    assertNotSame(pb, pb2);
    
    InputStream in = mock(InputStream.class);
    BufferedInputStream bis = fabSystem.newBufferedInputStream(in);
    assertNotNull(bis);
    assertSame(in,  bis.getDelegate());
    
    //run monitor
    final AtomicBoolean ran = new AtomicBoolean(false);
    I_LocatableRunnable r = new I_LocatableRunnable() {

      @Override
      public void run() {
        ran.set(true);
      }

      @Override
      public String getCurrentLocation() {
        return "cl";
      }

      @Override
      public String getAdditionalDetail() {
        // TODO Auto-generated method stub
        return null;
      }
    };
    I_RunMonitor rm = fabSystem.newRunMonitor(r, 0);
    assertEquals(0, rm.getSequence());
    rm.run();
    assertTrue(ran.get());
    
    ran.set(false);
    rm = fabSystem.newRunMonitor(r, 1);
    assertEquals(1, rm.getSequence());
    rm.run();
    assertTrue(ran.get());
    
    ExecutorService es = fabSystem.newFixedThreadPool(1);
    assertEquals("java.util.concurrent.Executors$FinalizableDelegatedExecutorService", es.getClass().getName());
     
    ExecutorService es2 = fabSystem.newFixedThreadPool(2);
    assertEquals("java.util.concurrent.ThreadPoolExecutor", es2.getClass().getName());
    
    Process proc = mock(Process.class);
    I_ProcessRunnable pr =  fabSystem.newProcessRunnable(proc);
    assertNotNull(pr);
    
    ExecutingProcess ep =  fabSystem.newExecutingProcess(proc);
    assertNotNull(ep);
    
    I_FabricateConstants constants = fabSystem.newFabConstantsDiscovery("languageCode", "countryCode");
    assertEquals(FabricateEnConstants.class.getName(), constants.getClass().getName());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetComputerInfoDiscoveryDelegates() {
    FabSystem sys = new FabSystem();
    //yep mirror the asserts just to do the passthrough,
    // the implementation is tested in ComputerInfoDiscoverTrial
    assertEquals(ComputerInfoDiscovery.getJavaVersion(sys), sys.getJavaVersion());
    String os = ComputerInfoDiscovery.getOperatingSystem(sys);
    assertEquals(os, sys.getOperatingSystem());
    assertEquals(ComputerInfoDiscovery.getOperatingSystemVersion(sys, os), 
        sys.getOperatingSystemVersion(os));
    
    String [] cpuInfo = ComputerInfoDiscovery.getCpuInfo(sys, os);
    String [] sysCpuInfo = sys.getCpuInfo(os);
    assertEquals(cpuInfo[0], sysCpuInfo[0]);
    assertEquals(cpuInfo[1], sysCpuInfo[1]);
    assertEquals(cpuInfo.length, sysCpuInfo.length);
    assertEquals(2, sysCpuInfo.length);
    
    
  }
}
