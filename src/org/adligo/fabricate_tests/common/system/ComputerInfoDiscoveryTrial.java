package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.system.ComputerInfoDiscovery;
import org.adligo.fabricate.common.system.I_ExecutionResult;
import org.adligo.fabricate.common.system.I_Executor;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ComputerInfoDiscovery.class,minCoverage=0.0)
public class ComputerInfoDiscoveryTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethodGetJavaVersion() {
    I_FabSystem sysMock = mock(I_FabSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.getProperty("java.version", "Unknown")).thenReturn("123");
    assertEquals("123", ComputerInfoDiscovery.getJavaVersion(sysMock));
  }
  
  @Test
  public void testMethodGetCpuInfo() throws Exception {
    I_FabSystem sysMock = mock(I_FabSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    //actual value from a real call on mac os
    when(sysMock.getProperty("os.name", "Unknown")).thenReturn("Mac OS X");
    
    I_Executor exec = mock(I_Executor.class);
    when(sysMock.getExecutor()).thenReturn(exec);
    
    I_ExecutionResult result = mock(I_ExecutionResult.class);
    when(result.getOutput()).thenReturn("machdep.cpu.brand_string: Intel(R) Core(TM) i7-4930K CPU @ 3.40GHz");
    when(exec.executeProcess(FabricationMemoryConstants.EMPTY_ENV, 
        ".", "sysctl", "-a")).thenReturn(result);
    String [] cpuInfo = ComputerInfoDiscovery.getCpuInfo(
        sysMock, ComputerInfoDiscovery.MAC);
    assertEquals("Intel(R) Core(TM) i7-4930K CPU", cpuInfo[0] );
    assertEquals("3.40GHz", cpuInfo[1] );
   
    when(result.getOutput()).thenReturn("model name\t: Intel(R) Core(TM) i7 CPU         860 @ 2.80GHz");
    when(exec.executeProcess(FabricationMemoryConstants.EMPTY_ENV, 
        ".", "cat", "/proc/cpuinfo")).thenReturn(result);
    cpuInfo = ComputerInfoDiscovery.getCpuInfo(
        sysMock, ComputerInfoDiscovery.LINUX);
    assertEquals("Intel(R) Core(TM) i7 CPU 860", cpuInfo[0] );
    assertEquals("2.80GHz", cpuInfo[1] );
    
    //actual value from a real call on a Windows 8.1 virtual box
    when(result.getOutput()).thenReturn("Name\r\n" +
        "Intel(R) Core(TM) i7-4930K CPU @ 3.40GHz");
    when(exec.executeProcess(FabricationMemoryConstants.EMPTY_ENV, 
        ".", "wmic", "cpu", "get","name")).thenReturn(result);
    cpuInfo = ComputerInfoDiscovery.getCpuInfo(
        sysMock, ComputerInfoDiscovery.WINDOWS);
    assertEquals("Intel(R) Core(TM) i7-4930K CPU", cpuInfo[0] );
    assertEquals("3.40GHz", cpuInfo[1] );
    
    
  }
  
  @Test
  public void testMethodGetOs() {
    I_FabSystem sysMock = mock(I_FabSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    //actual value from a real call on mac os
    when(sysMock.getProperty("os.name", "Unknown")).thenReturn("Mac OS X");
    assertEquals(ComputerInfoDiscovery.MAC, ComputerInfoDiscovery.getOperatingSystem(sysMock));
    
    //actual value from a real call on a Windows 8.1 virtual box
    when(sysMock.getProperty("os.name", "Unknown")).thenReturn("Windows 8.1");
    assertEquals(ComputerInfoDiscovery.WINDOWS, ComputerInfoDiscovery.getOperatingSystem(sysMock));
    
  //actual value from a real Scientific Linux machine
    when(sysMock.getProperty("os.name", "Unknown")).thenReturn("Linux");
    assertEquals(ComputerInfoDiscovery.LINUX, ComputerInfoDiscovery.getOperatingSystem(sysMock));
  }
  
  @Test
  public void testMethodGetOsVersion() throws Exception {
    I_FabSystem sysMock = mock(I_FabSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    //actual value from a real call on mac os
    when(sysMock.getProperty("os.name", "Unknown")).thenReturn("Mac OS X");
    
    I_Executor exec = mock(I_Executor.class);
    when(sysMock.getExecutor()).thenReturn(exec);
    
    I_ExecutionResult result = mock(I_ExecutionResult.class);
    when(result.getOutput()).thenReturn("10.0.0");
    when(exec.executeProcess(FabricationMemoryConstants.EMPTY_ENV, 
        ".", "sw_vers", "-productVersion")).thenReturn(result);
    assertEquals("10.0.0", ComputerInfoDiscovery.getOperatingSystemVersion(
        sysMock, ComputerInfoDiscovery.MAC));
    
   
    //actual value from a real call on a Windows 8.1 virtual box
    when(sysMock.getProperty("os.name", "Unknown")).thenReturn("Windows 8.1");
    assertEquals("8.1", ComputerInfoDiscovery.getOperatingSystemVersion(
        sysMock, ComputerInfoDiscovery.WINDOWS));
    
    
    when(sysMock.getProperty("os.version", "Unknown")).thenReturn("3.10.0123");
    assertEquals("3.10.0123", ComputerInfoDiscovery.getOperatingSystemVersion(
        sysMock, ComputerInfoDiscovery.LINUX));
  }
}
