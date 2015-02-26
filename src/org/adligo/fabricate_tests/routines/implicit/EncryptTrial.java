package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.ImplicitTraitEnMessages;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=EncryptTrait.class, minCoverage=80.0)
public class EncryptTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private EncryptTrait encrypt_ = new EncryptTrait();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeperator()).thenReturn(System.lineSeparator());
    encrypt_.setSystem(sysMock_);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodRunSimple12361224Fibanachi() throws Exception {
    encrypt_.setInput("1");
    //ASCII 1 is 0011 0001
    //ASCII 1 shifted to the right by 1 is 1001 1000
    
    encrypt_.run();
    String result = encrypt_.getOutput();
    assertEquals("@@@AS@", result);
    // 0100 0000 = @
    // 0100 0001 = A
    
    // 0101 0011 = S
    
    encrypt_.setInput("12");
    //ASCII 1 is 0011 0001
    //ASCII 2 is 0011 0010
    //ASCII 12 shifted to the right by 2 is 
    // 1000 1100
    // 0100 1100
    
    encrypt_.run();
    result = encrypt_.getOutput();
    // 0100 0000 = @
    // 0100 0010 = B
    
    // 0101 0001 = Q
    // 0101 0001 = Q
    // 0100 0110 = F
    // 0100      = @
    assertEquals("@@@BQQF@", result);
    
    encrypt_.setInput("123");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@CLXSDL", result);
    
    encrypt_.setInput("1236");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@DLLIRFLX", result);
    
    encrypt_.setInput("12361");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@EQFDYCFMQ", result);    
    
    encrypt_.setInput("123612");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@FYCBLQSFXXP", result);    
    
    encrypt_.setInput("1236122");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@GLQQFHYSLLIR@", result);    
    
    encrypt_.setInput("12361224");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@HFPXSDLYVFDYCD", result);    
    
   
    
    encrypt_.setInput("0000000000000000000000000000000");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@@@_LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@L@", result);  
    
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1024; i++) {
      sb.append("0");
    }
    encrypt_.setInput(sb.toString());
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("@A@@F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@L@", result);  
  }
}
