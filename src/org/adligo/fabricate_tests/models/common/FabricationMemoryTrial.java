package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricationMemory;
import org.adligo.fabricate.models.common.FabricationMemoryMutant;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Map;

@SourceFileScope (sourceClass=FabricationMemory.class)
public class FabricationMemoryTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testCopyConstructor() {
    Object o = new Object();
    FabricationMemoryMutant fmm = new FabricationMemoryMutant();
    fmm.put("key", o);
    FabricationMemory fm = new FabricationMemory(fmm);
    
    assertSame(o, fm.get("key"));
    Map<String,Object> vals = fm.get();
    assertSame(o, vals.get("key"));
    assertEquals(1, vals.size());
    assertEquals("java.util.Collections$UnmodifiableMap", vals.getClass().getName());
  }
}
