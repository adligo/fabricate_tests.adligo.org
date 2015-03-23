package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.FabricateController;
import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.log.FabLog;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.CommandLineArgs;
import org.adligo.fabricate.common.system.FabSystem;
import org.adligo.fabricate.common.system.FabSystemSetup;
import org.adligo.fabricate.common.system.I_FabSystem;
import org.adligo.fabricate.managers.CommandManager;
import org.adligo.fabricate.managers.FabricationManager;
import org.adligo.fabricate.managers.ProjectsManager;
import org.adligo.fabricate.routines.implicit.DecryptTrait;
import org.adligo.fabricate.routines.implicit.EncryptTrait;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.FabricateType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.LogSettingType;
import org.adligo.fabricate.xml.io_v1.fabricate_v1_0.LogSettingsType;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@SourceFileScope (sourceClass=FabSystemSetup.class,minCoverage=98.0)
public class FabSystemSetupTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstantDefaultLogsOn() {
    Set<String> lo = FabSystemSetup.DEFALUT_ON_LOGS;
    assertContains(lo, FabricateController.class.getName());
    assertContains(lo, CommandManager.class.getName());
    assertContains(lo, FabricationManager.class.getName());
    assertContains(lo, ProjectsManager.class.getName());
    assertEquals(4, lo.size());
  }

  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodCheckManditoryInjectedArgs() {
    I_FabSystem sysMock = mock(I_FabSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    
    I_FabLog logMock = mock(I_FabLog.class);
    MockMethod<Void> printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    when(sysMock.getLog()).thenReturn(logMock);
    
    when(sysMock.getArgValue("java")).thenReturn(null);
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        SystemEnMessages.INSTANCE.getExceptionJavaVersionParameterExpected())), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            FabSystemSetup.checkManditoryInjectedArgs(sysMock);
          }
        });
    assertEquals(CommandLineArgs.END, printlnMethod.getArg(0));
    assertEquals(1, printlnMethod.count());
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    when(sysMock.getLog()).thenReturn(logMock);
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    when(sysMock.getArgValue("java")).thenReturn("1.2.3");
    when(sysMock.getArgValue("start")).thenReturn(null);
    assertThrown(new ExpectedThrowable(new IllegalStateException(
        SystemEnMessages.INSTANCE.getExceptionNoStartTimeArg())), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            FabSystemSetup.checkManditoryInjectedArgs(sysMock);
          }
        });
    assertEquals(CommandLineArgs.END, printlnMethod.getArg(0));
    assertEquals(1, printlnMethod.count());
    
    printlnMethod = new MockMethod<Void>();
    doAnswer(printlnMethod).when(logMock).println(any());
    when(sysMock.getArgValue("java")).thenReturn("1.2.3");
    when(sysMock.getArgValue("start")).thenReturn("123");
    FabSystemSetup.checkManditoryInjectedArgs(sysMock);
    assertEquals(0, printlnMethod.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodGetSettings() {
    
    FabricateType fabricate = new FabricateType();
    Map<String,Boolean> logsOn = FabSystemSetup.getSettings(fabricate, Collections.singleton("Foo"));
    assertTrue(logsOn.get("Foo"));
    assertEquals(1, logsOn.size());
    
    LogSettingsType settings = new LogSettingsType();
    
    LogSettingType lst = new LogSettingType();
    lst.setClazz("Bar");
    lst.setSetting(true);
    settings.getLog().add(lst);
    
    LogSettingType lstOff = new LogSettingType();
    lstOff.setClazz("Car");
    lstOff.setSetting(false);
    settings.getLog().add(lstOff);
    fabricate.setLogs(settings);
    
    logsOn = FabSystemSetup.getSettings(fabricate, Collections.singleton("Foo"));
    assertTrue(logsOn.get("Foo"));
    assertTrue(logsOn.get("Bar"));
    assertFalse(logsOn.get("Car"));
    assertEquals(3, logsOn.size());
  }

  @SuppressWarnings({"boxing", "unchecked"})
  @Test
  public void testStaticMethodSetupFromArgs() {
    FabSystem sysMock = mock(FabSystem.class);
    when(sysMock.getDefaultCountry()).thenReturn("US");
    when(sysMock.getDefaultLanguage()).thenReturn("en");
    
    MockMethod<I_FabricateConstants> newFabConstantsMethod = new MockMethod<I_FabricateConstants>(
        FabricateEnConstants.INSTANCE, true);
    doAnswer(newFabConstantsMethod).when(sysMock).newFabConstantsDiscovery(any(), any());
    
    MockMethod<Void> setLogMethod = new MockMethod<Void>();
    MockMethod<Void> setConstantsMethod = new MockMethod<Void>();
    MockMethod<Void> setArgsMethod = new MockMethod<Void>();
    
    doAnswer(setLogMethod).when(sysMock).setLog(any());
    doAnswer(setConstantsMethod).when(sysMock).setConstants(any());
    doAnswer(setArgsMethod).when(sysMock).setArgs(any());
    
    //nullLocale
    FabSystemSetup.setup(sysMock, new String[] {"--purge", CommandLineArgs.PASSABLE_ARGS_ + 
        "=psArgs"
    });
    I_FabLog log = (I_FabLog) setLogMethod.getArg(0);
    assertEquals(FabLog.class.getName(), log.getClass().getName());
    assertFalse(log.hasAllLogsEnabled());
    assertEquals(1, setLogMethod.count());
    
    Object[] newFabConstantsArgs = newFabConstantsMethod.getArgs(0);
    assertEquals("en", newFabConstantsArgs[0]);
    assertEquals("US", newFabConstantsArgs[1]);
    assertEquals(1, newFabConstantsMethod.count());
    
    I_FabricateConstants constants = (I_FabricateConstants) setConstantsMethod.getArg(0);
    assertSame(FabricateEnConstants.INSTANCE, constants);
    assertEquals(1, setConstantsMethod.count());
    
    Map<String,String> args = (Map<String,String>) setArgsMethod.getArg(0);
    assertTrue(args.containsKey("-p"));
    assertEquals(1, setArgsMethod.count());
    
    setLogMethod.clear();
    newFabConstantsMethod.clear();
    setConstantsMethod.clear();
    setArgsMethod.clear();
    
    //notNullLocale
    FabSystemSetup.setup(sysMock, new String[] {"--=fr_CA","--purge", CommandLineArgs.PASSABLE_ARGS_ + 
        "=psArgs"
    });
    log = (I_FabLog) setLogMethod.getArg(0);
    assertEquals(FabLog.class.getName(), log.getClass().getName());
    assertFalse(log.hasAllLogsEnabled());
    assertEquals(1, setLogMethod.count());
    
    newFabConstantsArgs = newFabConstantsMethod.getArgs(0);
    assertEquals("fr", newFabConstantsArgs[0]);
    assertEquals("CA", newFabConstantsArgs[1]);
    assertEquals(1, newFabConstantsMethod.count());
    
    constants = (I_FabricateConstants) setConstantsMethod.getArg(0);
    assertSame(FabricateEnConstants.INSTANCE, constants);
    assertEquals(1, setConstantsMethod.count());
    
    args = (Map<String,String>) setArgsMethod.getArg(0);
    assertTrue(args.containsKey("-p"));
    assertEquals("psArgs", args.get(CommandLineArgs.PASSABLE_ARGS_));
    assertEquals("fr_CA", args.get(CommandLineArgs.LOCALE));
    assertEquals(1, setArgsMethod.count());
    
    setLogMethod.clear();
    newFabConstantsMethod.clear();
    setConstantsMethod.clear();
    setArgsMethod.clear();
    //noPassableArgs
    
    FabSystemSetup.setup(sysMock, new String[] {"--=fr_CA","--purge"});
    log = (I_FabLog) setLogMethod.getArg(0);
    assertEquals(FabLog.class.getName(), log.getClass().getName());
    assertFalse(log.hasAllLogsEnabled());
    assertEquals(1, setLogMethod.count());
    
    newFabConstantsArgs = newFabConstantsMethod.getArgs(0);
    assertEquals("fr", newFabConstantsArgs[0]);
    assertEquals("CA", newFabConstantsArgs[1]);
    assertEquals(1, newFabConstantsMethod.count());
    
    constants = (I_FabricateConstants) setConstantsMethod.getArg(0);
    assertSame(FabricateEnConstants.INSTANCE, constants);
    assertEquals(1, setConstantsMethod.count());
    
    args = (Map<String,String>) setArgsMethod.getArg(0);
    assertTrue(args.containsKey("-p"));
    assertEquals("\"[2,[8,--=fr_CA][7,--purge]]\"", args.get(CommandLineArgs.PASSABLE_ARGS_));
    assertEquals("fr_CA", args.get(CommandLineArgs.LOCALE));
    assertEquals(1, setArgsMethod.count());
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testStaticMethodSetupFromXml() {
    
    FabricateType fabricate = new FabricateType();
    
    LogSettingsType settings = new LogSettingsType();
    
    LogSettingType lst = new LogSettingType();
    lst.setClazz(EncryptTrait.class.getName());
    lst.setSetting(true);
    settings.getLog().add(lst);
    
    LogSettingType lstOff = new LogSettingType();
    lstOff.setClazz(DecryptTrait.class.getName());
    lstOff.setSetting(false);
    settings.getLog().add(lstOff);
    fabricate.setLogs(settings);
    
    FabSystem sysMock = mock(FabSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    
    MockMethod<Void> setLogMethod = new MockMethod<Void>();
    MockMethod<Void> setConstantsMethod = new MockMethod<Void>();
    MockMethod<Void> setArgsMethod = new MockMethod<Void>();
    
    doAnswer(setLogMethod).when(sysMock).setLog(any());
    doAnswer(setConstantsMethod).when(sysMock).setConstants(any());
    doAnswer(setArgsMethod).when(sysMock).setArgs(any());
    
    FabSystemSetup.setup(sysMock, fabricate);
    
    
    I_FabLog log = (I_FabLog) setLogMethod.getArg(0);
    assertEquals(FabLog.class.getName(), log.getClass().getName());
    assertTrue(log.isLogEnabled(FabricateController.class));
    assertTrue(log.isLogEnabled(EncryptTrait.class));
    assertFalse(log.isLogEnabled(DecryptTrait.class));
    assertFalse(log.hasAllLogsEnabled());
    assertEquals(1, setLogMethod.count());
    
    
    I_FabricateConstants constants = (I_FabricateConstants) setConstantsMethod.getArg(0);
    assertSame(FabricateEnConstants.INSTANCE, constants);
    assertEquals(1, setConstantsMethod.count());
  }
}
