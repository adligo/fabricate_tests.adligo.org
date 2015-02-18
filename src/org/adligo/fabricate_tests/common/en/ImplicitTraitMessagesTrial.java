package org.adligo.fabricate_tests.common.en;

import org.adligo.fabricate.common.en.ImplicitTraitEnMessages;
import org.adligo.fabricate.common.i18n.I_ImplicitTraitMessages;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.adligo.tests4j_tests.shared.i18n.I18N_Asserter;

@SourceFileScope (sourceClass=ImplicitTraitEnMessages.class)
public class ImplicitTraitMessagesTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() {
    I18N_Asserter asserter = new I18N_Asserter(this);
    
    I_ImplicitTraitMessages messages = ImplicitTraitEnMessages.INSTANCE;
    asserter.assertConstant("Please enter the data to decrypt.", 
        messages.getPleaseEnterTheDataToDecrypt());
    asserter.assertConstant("Please enter the data to encrypt.", 
        messages.getPleaseEnterTheDataToEncrypt());
    
    
    asserter.assertConstant("The following line contains the decrypted result;", 
        messages.getTheFollowingLineContainsTheDecryptedResult());
    asserter.assertConstant("The following line contains the encrypted result;", 
        messages.getTheFollowingLineContainsTheEncryptedResult());
    
    asserter.assertConstantsMatchMethods(ImplicitTraitEnMessages.class);
  }
}
