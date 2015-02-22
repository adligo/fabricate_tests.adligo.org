package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.files.I_FabFileIO;
import org.adligo.fabricate.common.log.FabFileLog;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.ByteArrayOutputStream;

@SourceFileScope (sourceClass=FabFileLog.class, minCoverage=68.0)
public class FabFileLogTrial extends MockitoSourceFileTrial {
  private ByteArrayOutputStream baos_;
  private I_FabFileIO files_;
  
  @Override
  public void beforeTests()  {
    baos_ = new ByteArrayOutputStream();
    files_ = mock(I_FabFileIO.class);
    try {
      doReturn(baos_).when(files_).newFileOutputStream("ahh");
    } catch (Exception x) {
      x.printStackTrace();
    }
  }
  
  @AfterTrial
  public static void afterTrial() {
    ThreadLocalPrintStreamMock.revert();
  }

  
  @Test
  public void testMethodPrintLn() throws Exception{
    FabFileLog log = new FabFileLog("ahh", files_);
    log.println("line");
    assertEquals("line\n", baos_.toString());
  }
  
  @Test
  public void testMethodPrintTrace() throws Exception {
    FabFileLog log = new FabFileLog("ahh", files_);
    Exception e = new Exception("exc");
    e.setStackTrace(new StackTraceElement[] {});
    
    log.printTrace(e);
    assertEquals("java.lang.Exception: exc\n", baos_.toString());
    baos_.reset();
    
    Exception x = new Exception("xtc");
    x.setStackTrace(new StackTraceElement[] {});
    x.initCause(e);
    
    log.printTrace(x);
    assertEquals("java.lang.Exception: xtc\n" +
        "Caused by: java.lang.Exception: exc\n" + 
        "java.lang.Exception: exc\n", baos_.toString());
    
  }
}
