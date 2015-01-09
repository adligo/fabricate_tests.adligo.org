package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.ProjectEnMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

import java.util.Collections;

@SourceFileScope (sourceClass=ProjectEnMessages.class)
public class ProjectEnMessagesTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    ProjectEnMessages messages = ProjectEnMessages.INSTANCE;
    asserter.assertConstant("can NOT depend on itself.", 
        messages.getCanNotDependOnIteself());
    asserter.assertConstant("does not contain a project.xml file.", 
        messages.getDoesNotContainAProjectXml());
    asserter.assertConstant("must depend on a project contained in the fabricate.xml file.", 
        messages.getMustDependOnAFabricateXmlProject());
    asserter.assertConstant("The following project;", 
        messages.getTheFollowingProject());
    
    asserter.assertConstantsMatchMethods(ProjectEnMessages.class);
  }
}
