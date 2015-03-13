package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.models.common.DuplicateRoutineException;
import org.adligo.fabricate.models.common.RoutineBriefOrigin;
import org.adligo.fabricate.models.fabricate.FabricateMutant;
import org.adligo.fabricate.models.fabricate.I_FabricateXmlDiscovery;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineParentType;
import org.adligo.fabricate.xml.io_v1.common_v1_0.RoutineType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoUseCaseTrial;

import java.util.List;

public class FabricateXmlUsesTrial extends MockitoUseCaseTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testDuplicateTraitName() throws Exception {
    FabricateType fab = new FabXmlFileIO().parseFabricate_v1_0("test_data/xml_io_trials/fabricate/fabricateDuplicateTraitName.xml");
    List<RoutineParentType> traits = fab.getTrait();
    assertEquals(2, traits.size());
    
    I_FabricateXmlDiscovery xmlDisc = mock(I_FabricateXmlDiscovery.class);
    FabricateMutant fm = new FabricateMutant(fab, xmlDisc);
    
    DuplicateRoutineException caught = null;
    try {
      fm.addTraits(fab.getTrait());
    } catch (DuplicateRoutineException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("trex", caught.getName());
    assertSame(RoutineBriefOrigin.FABRICATE_TRAIT, caught.getOrigin());
    assertNull(caught.getParentName());
    assertNull(caught.getParentOrigin());
    
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testDuplicateTraitTaskName() throws Exception {
    FabricateType fab = new FabXmlFileIO().parseFabricate_v1_0("test_data/xml_io_trials/fabricate/fabricateDuplicateTraitTaskName.xml");
    List<RoutineParentType> traits = fab.getTrait();
    assertEquals(1, traits.size());
    RoutineParentType tts = traits.get(0);
    List<RoutineType> ttTasks = tts.getTask();
    assertEquals(2, ttTasks.size());
    
    I_FabricateXmlDiscovery xmlDisc = mock(I_FabricateXmlDiscovery.class);
    FabricateMutant fm = new FabricateMutant(fab, xmlDisc);
    
    DuplicateRoutineException caught = null;
    try {
      fm.addTraits(fab.getTrait());
    } catch (DuplicateRoutineException x) {
      caught = x;
    }
    assertNotNull(caught);
    assertEquals("trexIn", caught.getName());
    assertSame(RoutineBriefOrigin.FABRICATE_TRAIT_TASK, caught.getOrigin());
    assertEquals("trex", caught.getParentName());
    assertSame(RoutineBriefOrigin.FABRICATE_TRAIT, caught.getParentOrigin());
  }
}
