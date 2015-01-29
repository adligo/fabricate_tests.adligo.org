package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.BufferedInputStream;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayInputStream;

@SourceFileScope (sourceClass=BufferedInputStream.class,minCoverage=88.0)
public class BufferedInputStreamTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethodsGetsAndSets() throws Exception {
    ByteArrayInputStream baos = new ByteArrayInputStream(
        new String("hey" + System.lineSeparator() +
        "you " + System.lineSeparator() + 
        "guys").getBytes());
    BufferedInputStream bir = new BufferedInputStream(baos);
    assertEquals("hey", bir.readLine());
    assertEquals("you ", bir.readLine());
    assertEquals("guys", bir.readLine());
    assertNull(bir.readLine());
    
    bir.close();
    assertTrue(bir.isClosedWrapper());
    assertTrue(bir.isClosedReader());
    assertTrue(bir.isClosedWrapper());
    assertSame(baos, bir.getDelegate());
  }
}
