package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricationMemoryMutant;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Map;

@SourceFileScope (sourceClass=FabricationMemoryMutant.class)
public class FabricationMemoryMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsPutGet() {
    Object o = new Object();
    FabricationMemoryMutant fmm = new FabricationMemoryMutant();
    fmm.put("key", o);
    assertSame(o, fmm.get("key"));
    Map<String,Object> vals = fmm.get();
    assertSame(o, vals.get("key"));
    assertEquals(1, vals.size());
  }
}
