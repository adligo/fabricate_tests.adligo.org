package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.MemoryLock;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.Set;

@SourceFileScope (sourceClass=MemoryLock.class, minCoverage=90.0)
public class MemoryLockTrial extends MockitoSourceFileTrial {

  @SuppressWarnings({"boxing", "unused"})
  @Test
  public void testConstructorAndMethods() {
    MemoryLock lock = new MemoryLock("key", Collections.singleton("foo"));
    
    assertEquals("key", lock.getKey());
    Set<String> callers = lock.getAllowedCallers();
    assertContains(callers, "foo");
    assertEquals(1, callers.size());
    
    assertFalse(lock.isAllowed());
    
    final MemoryLock lock2 = new MemoryLock("key2", Collections.singleton(MemoryLockTrial.class.getName()));
    
    assertEquals("key2", lock2.getKey());
    callers = lock2.getAllowedCallers();
    assertContains(callers, MemoryLockTrial.class.getName());
    assertEquals(1, callers.size());
    
    assertFalse(lock2.isAllowed());
    Runnable run = new Runnable() {
      
      @Override
      public void run() {
        assertTrue(lock2.isAllowed());
      }
    };
  }
}
