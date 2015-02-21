package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.FabricateJaxbContexts;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import javax.xml.bind.JAXBContext;

@SourceFileScope (sourceClass=FabricateJaxbContexts.class, minCoverage=37.0)
public class FabricateJaxbContextsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    JAXBContext ctx =FabricateJaxbContexts.DEPOT_CONTEXT; 
    assertNotNull(ctx);
    String asString = ctx.toString();
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.depot_v1_0.DepotType"));
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.depot_v1_0.ArtifactType"));
    
    ctx =FabricateJaxbContexts.DEV_CONTEXT; 
    assertNotNull(ctx);
    asString = ctx.toString();
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType"));
    
    ctx =FabricateJaxbContexts.FAB_CONTEXT; 
    assertNotNull(ctx);
    asString = ctx.toString();
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType"));
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateDependencies"));
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType"));
    
    ctx =FabricateJaxbContexts.LIB_CONTEXT; 
    assertNotNull(ctx);
    asString = ctx.toString();
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.library_v1_0.DependencyType"));
    
    ctx =FabricateJaxbContexts.PROJECT_CONTEXT; 
    assertNotNull(ctx);
    asString = ctx.toString();
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.common_v1_0.ParamType"));
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.library_v1_0.DependenciesType"));
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.project_v1_0.FabricateProjectType"));
    
    ctx =FabricateJaxbContexts.RESULT_CONTEXT; 
    assertNotNull(ctx);
    asString = ctx.toString();
    assertTrue(asString, asString.contains("org.adligo.fabricate.xml.io_v1.result_v1_0.FailureType"));
    
  }
}
