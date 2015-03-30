package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.ImplicitTraitEnMessages;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=DecryptTrait.class, minCoverage=80.0)
public class DecryptTraitTrial extends MockitoSourceFileTrial {
  private I_FabSystem sysMock_;
  private DecryptTrait encrypt_ = new DecryptTrait();
  
  public void beforeTests() {
    sysMock_ = mock(I_FabSystem.class);
    when(sysMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock_.lineSeparator()).thenReturn(System.lineSeparator());
    encrypt_.setSystem(sysMock_);
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodRunSimple12361224Fibanachi() throws Exception {
    encrypt_.setInput("@@@AS@");
    //ASCII 1 is 0011 0001
    //ASCII 1 shifted to the right by 1 is 1001 1000
    
    encrypt_.run();
    String result = encrypt_.getOutput();
    // 0100 0000 = @
    // 0100 0001 = A
    
    // 0101 0011 = S
    assertEquals("1", result);
    
    //ASCII 1 is 0011 0001
    //ASCII 2 is 0011 0010
    //ASCII 12 shifted to the right by 2 is 
    // 1000 1100
    // 0100 1100
    
    
    encrypt_.setInput("@@@BQQF@");
    encrypt_.run();
    result = encrypt_.getOutput();
    // 0100 0000 = @
    // 0100 0010 = B
    
    // 0101 0001 = Q
    // 0101 0001 = Q
    // 0100 0110 = F
    // 0100      = @
    assertEquals("12", result);
    
    encrypt_.setInput("@@@CLXSDL");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("123", result);
    
    encrypt_.setInput("@@@DLLIRFLX");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("1236", result);
    
    encrypt_.setInput("@@@EQFDYCFMQ");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("12361", result);    
    
    encrypt_.setInput("@@@FYCBLQSFXXP");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("123612", result);    
    
    encrypt_.setInput("@@@GLQQFHYSLLIR@");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("1236122", result);    
    
    encrypt_.setInput("@@@HFPXSDLYVFDYCD");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("12361224", result);    
    
    encrypt_.setInput("@@@_LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@L@");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals("0000000000000000000000000000000", result);  
    
    encrypt_.setInput("@A@@F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAP"
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
        + "F@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@LAPF@XC@L@");
    encrypt_.run();
    result = encrypt_.getOutput();
    assertEquals(1024, result.length());

    char [] chars = result.toCharArray();
    for (int i = 0; i < 1024; i++) {
      assertEquals('0', chars[i]);
    }
  }
}
