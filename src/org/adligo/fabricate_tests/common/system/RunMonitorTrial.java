package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.I_FabSystem;
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

@SourceFileScope (sourceClass=RunMonitor.class)
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
    when(system.newArrayBlockingQueue(Boolean.class, 1)).thenReturn(
        new ArrayBlockingQueue<Boolean>(1));
    
    AtomicBoolean ran = new AtomicBoolean(false);
    Runnable run = new Runnable() {
      
      @Override
      public void run() {
        try {
          Thread.sleep(201);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        ran.set(true);
      }
    };
    RunMonitor rm = new RunMonitor(system, run, 0);
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
    assertTrue(ran.get());
    assertTrue(rm.isFinished());
    assertNull(rm.getCaught());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAndRunWithThrown() {
    I_FabSystem system = mock(I_FabSystem.class);
    when(system.newArrayBlockingQueue(Boolean.class, 1)).thenReturn(
        new ArrayBlockingQueue<Boolean>(1));
    Runnable run = new Runnable() {
      
      @Override
      public void run() {
        try {
          Thread.sleep(201);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        throw new RuntimeException("x");
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
      rm.waitUntilFinished(200);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    assertTrue(rm.isFinished());
    Throwable caught = rm.getCaught();
    assertEquals(RuntimeException.class.getName(), caught.getClass().getName());
    assertEquals("x", caught.getMessage());
  }
  
 
}
