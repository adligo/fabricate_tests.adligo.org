package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.models.common.ExecutionEnvironment;
import org.adligo.fabricate.models.common.ExecutionEnvironmentMutant;
import org.adligo.fabricate.models.common.FabricationMemory;
import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.fabricate.models.common.FabricationMemoryMutant;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Map;

@SourceFileScope (sourceClass=FabricationMemory.class, minCoverage=100.0,
    allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class FabricationMemoryTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testCopyConstructor() {
    Object o = new Object();
    FabricationMemoryMutant<Object> fmm = new FabricationMemoryMutant<Object>(SystemEnMessages.INSTANCE);
    fmm.put("key", o);
    ExecutionEnvironmentMutant eem = new ExecutionEnvironmentMutant(SystemEnMessages.INSTANCE);
    eem.put("foo", "bar");
    fmm.put(FabricationMemoryConstants.ENV, eem);
    FabricationMemory<Object> fm = new FabricationMemory<Object>(fmm);
    
    assertSame(o, fm.get("key"));
    Map<String,Object> vals = fm.get();
    assertSame(o, vals.get("key"));
    Object em = fm.get(FabricationMemoryConstants.ENV);
    assertNotNull(em);
    assertEquals(ExecutionEnvironment.class.getName(), em.getClass().getName());
    assertEquals("bar", ((ExecutionEnvironment) em).get("foo"));
    assertEquals(2, vals.size());
    assertEquals("java.util.Collections$UnmodifiableMap", vals.getClass().getName());
  }
}
