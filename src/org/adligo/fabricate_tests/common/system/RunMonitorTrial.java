package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.common.system.I_LocatableRunnable;
import org.adligo.fabricate.common.system.RunMonitor;
import org.adligo.tests4j.system.shared.trials.BeforeTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@SourceFileScope (sourceClass=RunMonitor.class, minCoverage=96.0)
public class RunMonitorTrial extends MockitoSourceFileTrial {
  private static ExecutorService THREAD_POOL_;
  @BeforeTrial
  public static void beforeTrial(Map<String,Object> params) {
    THREAD_POOL_ = Executors.newFixedThreadPool(1);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndRun() {
    I_FabSystem system = mock(I_FabSystem.class);
    Thread thread = mock(Thread.class);
    when(system.currentThread()).thenReturn(thread);
    
    when(system.newArrayBlockingQueue(Boolean.class, 1)).thenReturn(
        new ArrayBlockingQueue<Boolean>(1));
    
    AtomicBoolean ran = new AtomicBoolean(false);
    I_LocatableRunnable run = new I_LocatableRunnable() {

      @Override
      public String getCurrentLocation() {
        return "cl";
      }
      
      @Override
      public void run() {
        try {
          Thread.sleep(201);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        ran.set(true);
      }

      @Override
      public String getAdditionalDetail() {
        // TODO Auto-generated method stub
        return null;
      }
    };
    RunMonitor rm = new RunMonitor(system, run, 0);
    assertNull(rm.getThread());
    assertEquals(0, rm.getSequence());
    assertFalse(rm.isFinished());
    assertNull(rm.getCaught());
    
    THREAD_POOL_.submit(rm);
    long time = System.currentTimeMillis();
    try {
      rm.waitUntilFinished(200);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    long dur = System.currentTimeMillis() - time;
    assertGreaterThanOrEquals(200, dur);
    try {
      rm.waitUntilFinished(200);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    assertSame(thread, rm.getThread());
    assertTrue(ran.get());
    assertFalse(rm.hasFailure());
    assertTrue(rm.isFinished());
    assertNull(rm.getCaught());
    assertSame(run, rm.getDelegate());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndRunWithThrown() {
    I_FabSystem system = mock(I_FabSystem.class);
    I_FabLog log = mock(I_FabLog.class);
    when(system.getLog()).thenReturn(log);
    
    when(system.newArrayBlockingQueue(Boolean.class, 1)).thenReturn(
        new ArrayBlockingQueue<Boolean>(1));
    I_LocatableRunnable run = new I_LocatableRunnable() {

      @Override
      public String getCurrentLocation() {
        return "cl";
      }
      
      @Override
      public void run() {
        try {
          Thread.sleep(201);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        throw new RuntimeException("x");
      }

      @Override
      public String getAdditionalDetail() {
        // TODO Auto-generated method stub
        return null;
      }
    };
    RunMonitor rm = new RunMonitor(system, run, 1);
    assertEquals(1, rm.getSequence());
    assertFalse(rm.isFinished());
    assertNull(rm.getCaught());
    
    THREAD_POOL_.submit(rm);
    long time = System.currentTimeMillis();
    try {
      rm.waitUntilFinished(200);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    long dur = System.currentTimeMillis() - time;
    assertGreaterThanOrEquals(200, dur);
    try {
      rm.waitUntilFinished(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    assertTrue(rm.isFinished());
    assertTrue(rm.hasFailure());
    Throwable caught = rm.getCaught();
    assertEquals(RuntimeException.class.getName(), caught.getClass().getName());
    assertEquals("x", caught.getMessage());
  }
  
 
}
