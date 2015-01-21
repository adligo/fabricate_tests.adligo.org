package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.FabXmlFileIO;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

/**
 * This class is just a redirect,
 * it is actually tested through the 
 * other trials in this package, which call 
 * FabXmlFiles to get to other files.
 * @author scott
 *
 */
@SourceFileScope (sourceClass=FabXmlFileIO.class,minCoverage=0.0)
public class FabXmlFilesTrial extends MockitoSourceFileTrial {

}
