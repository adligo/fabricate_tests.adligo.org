package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.routines.implicit.SshAgentHelper;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=SshAgentHelper.class, minCoverage=100.0)
public class SshAgentHelperTrial extends MockitoSourceFileTrial {
  
  @SuppressWarnings("unused")
  @Test
  public void testConstructorExceptions() {
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        SystemEnMessages.INSTANCE.getExceptionUnableToFindSSH_AUTH_SOCKWhenParsingOutputOfSshAgent())), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new SshAgentHelper(
                " export SSH_AUTH_SOCK;\r" +
                "SSH_AGENT_PID=906; export SSH_AGENT_PID;\r" +
                "echo Agent pid 906;", SystemEnMessages.INSTANCE);
          }
        });
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(
        SystemEnMessages.INSTANCE.getExceptionUnableToFindSSH_AGENT_PIDWhenParsingOutputOfSshAgent())), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            new SshAgentHelper(
                "SSH_AUTH_SOCK=/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905;" + 
                    " export SSH_AUTH_SOCK;\r\n" +
                "echo Agent pid 906;", SystemEnMessages.INSTANCE);
          }
        });
  }
  
  @Test
  public void testConstructorDosAndUnix() {
    SshAgentHelper sah = new SshAgentHelper(
        "SSH_AUTH_SOCK=/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905;" + 
        " export SSH_AUTH_SOCK;\r\n" +
        "SSH_AGENT_PID=906; export SSH_AGENT_PID;\r\n" +
        "echo Agent pid 906;", SystemEnMessages.INSTANCE);
    
    assertEquals("/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905",
        sah.getSock());
    assertEquals("906",sah.getPid());
    
    
    sah = new SshAgentHelper(
        "SSH_AUTH_SOCK=/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905;" + 
        " export SSH_AUTH_SOCK;\n\r" +
        "SSH_AGENT_PID=906; export SSH_AGENT_PID;\n\r" +
        "echo Agent pid 906;", SystemEnMessages.INSTANCE);
    
    assertEquals("/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905",
        sah.getSock());
    assertEquals("906",sah.getPid());
  }
  
  @Test
  public void testConstructorDos() {
    SshAgentHelper sah = new SshAgentHelper(
        "SSH_AUTH_SOCK=/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905;" + 
        " export SSH_AUTH_SOCK;\r" +
        "SSH_AGENT_PID=906; export SSH_AGENT_PID;\r" +
        "echo Agent pid 906;", SystemEnMessages.INSTANCE);
    
    assertEquals("/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905",
        sah.getSock());
    assertEquals("906",sah.getPid());
  }
  
  
  @Test
  public void testConstructorDosUnix() {
    SshAgentHelper sah = new SshAgentHelper(
        "SSH_AUTH_SOCK=/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905;" + 
        " export SSH_AUTH_SOCK;\r" +
        "SSH_AGENT_PID=906; export SSH_AGENT_PID;\r" +
        "echo Agent pid 906;", SystemEnMessages.INSTANCE);
    
    assertEquals("/var/folders/g8/8krz3q551s19dk6nx5yh5zlr0000gn/T//ssh-uPJzGzwa62el/agent.905",
        sah.getSock());
    assertEquals("906",sah.getPid());
  }


}
