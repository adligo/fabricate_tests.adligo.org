package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.models.common.FabricationMemoryMutant;
import org.adligo.fabricate.models.common.I_MemoryLock;
import org.adligo.fabricate.models.common.MemoryLock;
import org.adligo.fabricate_tests.models.common.mock.ClassToLock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@SourceFileScope (sourceClass=FabricationMemoryMutant.class, minCoverage=98.0)
public class FabricationMemoryMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsPutGet() {
    Object o = new Object();
    FabricationMemoryMutant<Object> fmm = new FabricationMemoryMutant<Object>(SystemEnMessages.INSTANCE);
    fmm.put("key", o);
    assertSame(o, fmm.get("key"));
    Map<String,Object> vals = fmm.get();
    assertSame(o, vals.get("key"));
    
    assertEquals(1, vals.size());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodsPutLockedException() {
    Object o = new Object();
    FabricationMemoryMutant<Object> fmm = new FabricationMemoryMutant<Object>(SystemEnMessages.INSTANCE);
    fmm.put("key", o);
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(SystemEnMessages.INSTANCE.getLocksMustContainAtLeastOneAllowedCaller())),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fmm.addLock(new MemoryLock("key", Collections.singleton(null)));
          }
        });
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(SystemEnMessages.INSTANCE.getLocksMayOnlyBeSetOnOrUnderTheCallersPackage())),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fmm.addLock(new MemoryLock("key", Collections.singleton("notAClass")));
          }
        });
    
    fmm.addLock(new MemoryLock("key", Collections.singleton(ClassToLock.class.getName())));
   
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        "The memory key 'key' has been locked by the following classes;" + System.lineSeparator() +
        "[" + ClassToLock.class.getName() + "]")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fmm.put("key", "key");
          }
        });
    
    Map<String, I_MemoryLock> locks = fmm.getLocks();
    I_MemoryLock ml =  locks.get("key");
    assertEquals("key", ml.getKey());
    Set<String> allowedCallers = ml.getAllowedCallers();
    assertContains(allowedCallers,   ClassToLock.class.getName());
    assertEquals(1, allowedCallers.size());
  }
}
