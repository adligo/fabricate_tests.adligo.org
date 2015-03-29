package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.FailureTransport;
import org.adligo.fabricate.xml.io_v1.result_v1_0.FailureType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=FailureTransport.class)
public class FailureTransportTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructor() {
    FailureType ft = new FailureType();
    FailureTransport trans = new FailureTransport(true, ft);
    assertTrue(trans.isLogged());
    assertSame(ft, trans.getFailure());
    
    trans = new FailureTransport(false, ft);
    assertFalse(trans.isLogged());
  }
  
  @Test
  public void testConstructorException() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException("failure")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new FailureTransport(true, null);
          }
        });
    
  }
}
