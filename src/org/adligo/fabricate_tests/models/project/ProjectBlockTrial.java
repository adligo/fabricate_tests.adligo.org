package org.adligo.fabricate_tests.models.project;

import org.adligo.fabricate.models.project.ProjectBlock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@SourceFileScope (sourceClass=ProjectBlock.class,minCoverage=87.0)
public class ProjectBlockTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorRealUsage() throws InterruptedException {
    ProjectBlock pb = new ProjectBlock("project", "blockedProject");
    assertEquals("ProjectBlock [project=project, blockingProject=blockedProject, blocked=true]", pb.toString());
    
    pb.setProjectFinished();
    assertTrue(pb.waitUntilUnblocked(0));
    assertTrue(pb.waitUntilUnblocked(0));
  }
  

  @SuppressWarnings({"boxing", "unchecked"})
  @Test
  public void testConstructorMockArrayBlockingQueue() throws InterruptedException {
    ArrayBlockingQueue<Boolean> block = mock(ArrayBlockingQueue.class);
    
    final ProjectBlock pb = new ProjectBlock("project", "blockedProject", block);
    assertEquals("ProjectBlock [project=project, blockingProject=blockedProject, blocked=true]", pb.toString());
    
    doThrow(new InterruptedException("xyz")).when(block).put(any());
    assertThrown(new ExpectedThrowable(new InterruptedException("xyz")), new I_Thrower() {
      @Override
      public void run() throws Throwable {
        pb.setProjectFinished();
      }
    });     
    
   
    doThrow(new InterruptedException("xyz123")).when(block).poll(anyLong(), any());
    assertThrown(new ExpectedThrowable(new InterruptedException("xyz123")), new I_Thrower() {
      @Override
      public void run() throws Throwable {
        pb.waitUntilUnblocked(0);
      }
    }); 
    
    reset(block);
    MockMethod<Void> putMethod = new MockMethod<Void>();
    doAnswer(putMethod).when(block).put(any());
    
    MockMethod<Void> pollMethod = new MockMethod<Void>();
    doAnswer(pollMethod).when(block).poll(anyLong(), any());
    
   
    
    ProjectBlock pb2 = new ProjectBlock("project", "blockedProject", block);
   
    assertFalse(pb2.waitUntilUnblocked(1));
    assertEquals(1L, pollMethod.getArgs(0)[0]);
    assertEquals(TimeUnit.MILLISECONDS, pollMethod.getArgs(0)[1]);
    
    pollMethod.clear();
    assertFalse(pb2.waitUntilUnblocked(2));
    assertEquals(2L, pollMethod.getArg(0));
    assertEquals(TimeUnit.MILLISECONDS, pollMethod.getArgs(0)[1]);
    
    pb.setProjectFinished();
    assertEquals(Boolean.TRUE, putMethod.getArg(0));
  }
  
}
