package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.fabricate.models.common.I_ExecutionEnvironment;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.HashMap;
import java.util.Map;

@SourceFileScope (sourceClass=FabricationMemoryConstants.class, minCoverage=72.0)
public class FabricationMemoryConstantsTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstants() {
    assertEquals("dependencies", FabricationMemoryConstants.DEPENDENCIES);
    assertEquals("environment", FabricationMemoryConstants.ENV);
    assertEquals("gitClonedProjects", FabricationMemoryConstants.CLONED_PROJECTS);
    
    assertEquals("projectsLoaded", FabricationMemoryConstants.PROJECTS_LOADED);
    assertEquals("projectsLoadedMap", FabricationMemoryConstants.PROJECTS_LOADED_MAP);
    assertEquals("projectsModified", FabricationMemoryConstants.PROJECTS_MODIFIED);
    
    assertEquals("setupSshAgent", FabricationMemoryConstants.SETUP_SSH_AGENT);
    I_ExecutionEnvironment executionEnv = FabricationMemoryConstants.EMPTY_ENV;
    Map<String,String> map = new HashMap<String,String>();
    executionEnv.addAllTo(map);
    assertEquals(0, map.size());
    FabricationMemoryConstants inst = FabricationMemoryConstants.INSTANCE;
    assertNotNull(inst);
  }
}
