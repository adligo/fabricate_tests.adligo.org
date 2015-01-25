package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

@SourceFileScope (sourceClass=SystemEnMessages.class)
public class SystemEnMessagesTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    SystemEnMessages messages = SystemEnMessages.INSTANCE;
    asserter.assertConstant("--version", 
        messages.getClaVersion());
    asserter.assertConstant("-v", 
        messages.getClaVersionShort());
    asserter.assertConstant("Compiled on <X/>.", 
        messages.getCompiledOnX());
    asserter.assertConstant("Exception: Expected $JAVA_HOME to have version <X/> but is <Y/>.", 
        messages.getExceptionExpectedJavaHomVersionXbutIsY());
    asserter.assertConstant("Exception: Fabricate requires Java 1.7 or greater.", 
        messages.getExceptionFabricateRequiresJava1_7OrGreater());
    asserter.assertConstant("Exception: Java version parameter expected.", 
        messages.getExceptionJavaVersionParameterExpected());
    asserter.assertConstant("Exception: No $FABRICATE_HOME environment variable set.", 
        messages.getExceptionNoFabricateHomeSet());
    asserter.assertConstant("Exception: No fabricate_*.jar in $FABRICATE_HOME/lib for the following $FABRICATE_HOME;", 
        messages.getExceptionNoFabricateJarInFabricateHomeLib());
    asserter.assertConstant("Exception: No fabricate.xml or project.xml found.", 
        messages.getExceptionNoFabricateXmlOrProjectXmlFound());
    asserter.assertConstant("Exception: No $JAVA_HOME environment variable set.", 
        messages.getExceptionNoJavaHomeSet());
    asserter.assertConstant("Fabricate by Adligo.", 
        messages.getFabricateByAdligo());
    asserter.assertConstant("Fabrication failed!", 
        messages.getFabricationFailed());
    asserter.assertConstant("Version <X/>.", 
        messages.getVersionX());
    asserter.assertConstantsMatchMethods(SystemEnMessages.class);
  }
  
}
