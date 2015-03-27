package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.AttributeEnConstants;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

@SourceFileScope (sourceClass=AttributeEnConstants.class)
public class AttributeEnConstantsTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    AttributeEnConstants messages = AttributeEnConstants.INSTANCE;
    
    asserter.assertConstant("exclude", 
        messages.getExclude());
    asserter.assertConstant("files", 
        messages.getFiles());
    asserter.assertConstant("Git: default branch", 
        messages.getGitDefaultBranch());
    asserter.assertConstant("include", 
        messages.getInclude());
    asserter.assertConstant("ide", 
        messages.getIde());
    asserter.assertConstant("platforms", 
        messages.getPlatforms());
    asserter.assertConstant("srcDirs", 
        messages.getSrcDirs());
    asserter.assertConstant("jdkSrcDir", 
        messages.getJdkSrcDir());
    
    asserter.assertConstantsMatchMethods(AttributeEnConstants.class);
  }
}
