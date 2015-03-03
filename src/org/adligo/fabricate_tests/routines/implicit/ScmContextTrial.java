package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate.models.common.ParameterMutant;
import org.adligo.fabricate.models.common.RoutineBriefMutant;
import org.adligo.fabricate.routines.implicit.ScmContext;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ScmContext.class, minCoverage=100.0)
public class ScmContextTrial extends MockitoSourceFileTrial {
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorAutofixes() {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.addParameter(new ParameterMutant(ScmContext.HOSTNAME, "example.com"));
    rbm.addParameter(new ParameterMutant(ScmContext.PATH,"opt/git/org"));
    ScmContext ctx = new ScmContext(rbm);
    
    assertEquals("example.com", ctx.getHostname());
    assertEquals("/opt/git/org/", ctx.getPath());
    assertEquals("https", ctx.getProtocol());
    assertEquals(443, ctx.getPort());
    assertEquals("", ctx.getUsername());
    
    rbm.addParameter(new ParameterMutant(ScmContext.PROTOCOL,"ssh"));
    ctx = new ScmContext(rbm);
    
    assertEquals("example.com", ctx.getHostname());
    assertEquals("/opt/git/org/", ctx.getPath());
    assertEquals("ssh", ctx.getProtocol());
    assertEquals(22, ctx.getPort());
    assertEquals("", ctx.getUsername());
  }


  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(ScmContext.HOSTNAME)),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ScmContext(rbm);
          }
        });
    
    rbm.addParameter(new ParameterMutant(ScmContext.HOSTNAME,"example.com"));
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(ScmContext.PATH)),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ScmContext(rbm);
          }
        });
    
    rbm.addParameter(new ParameterMutant(ScmContext.PATH,"xyz"));
    rbm.addParameter(new ParameterMutant(ScmContext.PROTOCOL,"xyz"));
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(ScmContext.PROTOCOL)),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new ScmContext(rbm);
          }
        });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorSimple() {
    RoutineBriefMutant rbm = new RoutineBriefMutant();
    rbm.addParameter(new ParameterMutant(ScmContext.HOSTNAME, "example.com"));
    rbm.addParameter(new ParameterMutant(ScmContext.PATH,"/opt/git/org/"));
    rbm.addParameter(new ParameterMutant(ScmContext.PROTOCOL,"ssh"));
    rbm.addParameter(new ParameterMutant(ScmContext.PORT,"123"));
    rbm.addParameter(new ParameterMutant(ScmContext.USER,"johnDoe"));
    ScmContext ctx = new ScmContext(rbm);
    
    assertEquals("example.com", ctx.getHostname());
    assertEquals("/opt/git/org/", ctx.getPath());
    assertEquals("ssh", ctx.getProtocol());
    assertEquals(123, ctx.getPort());
    assertEquals("johnDoe", ctx.getUsername());
  }
}
