package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate.common.files.xml_io.SchemaLoader;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

@SourceFileScope (sourceClass=SchemaLoader.class, minCoverage=84.0, allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class SchemaLoaderTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstants() throws Exception {
    
    assertEquals("common_v1_0.xsd",SchemaLoader.COMMON_V1_0);
    assertEquals("depot_v1_0.xsd",SchemaLoader.DEPOT_V1_0);
    assertEquals("dev_v1_0.xsd",SchemaLoader.DEV_V1_0);
    assertEquals("fabricate_v1_0.xsd",SchemaLoader.FABRICATE_V1_0);
    assertEquals("library_v1_0.xsd",SchemaLoader.LIBRARY_V1_0);
    assertEquals("project_v1_0.xsd",SchemaLoader.PROJECT_V1_0);
    assertEquals("result_v1_0.xsd",SchemaLoader.RESULT_V1_0);
    
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/",SchemaLoader.BASE_NS_V1);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/common_v1_0.xsd", SchemaLoader.COMMON_NS_V1_0);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/depot_v1_0.xsd",SchemaLoader.DEPOT_NS_V1_0);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/dev_v1_0.xsd",SchemaLoader.DEV_NS_V1_0);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/fabricate_v1_0.xsd",SchemaLoader.FAB_NS_V1_0);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/library_v1_0.xsd",SchemaLoader.LIB_NS_V1_0);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/project_v1_0.xsd",SchemaLoader.PROJECT_NS_V1_0);
    assertEquals("http://www.adligo.org/fabricate/xml/io_v1/result_v1_0.xsd",SchemaLoader.RESULT_NS_V1_0);
    
    assertEquals("/org/adligo/fabricate/xml/",SchemaLoader.XML_PACKAGE);
    assertEquals("/org/adligo/fabricate/xml/common_v1_0.xsd",SchemaLoader.COMMON_SCHEMA_V1_0);
    assertEquals("/org/adligo/fabricate/xml/depot_v1_0.xsd",SchemaLoader.DEPOT_SCHEMA_V1_0);
    assertEquals("/org/adligo/fabricate/xml/dev_v1_0.xsd",SchemaLoader.DEV_SCHEMA_V1_0);
    assertEquals("/org/adligo/fabricate/xml/fabricate_v1_0.xsd",SchemaLoader.FAB_SCHEMA_V1_0);
    assertEquals("/org/adligo/fabricate/xml/library_v1_0.xsd",SchemaLoader.LIB_SCHEMA_V1_0);
    assertEquals("/org/adligo/fabricate/xml/project_v1_0.xsd",SchemaLoader.PROJECT_SCHEMA_V1_0);
    assertEquals("/org/adligo/fabricate/xml/result_v1_0.xsd",SchemaLoader.RESULT_SCHEMA_V1_0);
   
    assertEquals("org.adligo.fabricate.xml.io_v1",SchemaLoader.BASE_JAVA_NS_V1);
    assertEquals("org.adligo.fabricate.xml.io_v1.common_v1_0",SchemaLoader.JAVA_COMMON_NS_V1_0);
    assertEquals("org.adligo.fabricate.xml.io_v1.depot_v1_0",SchemaLoader.JAVA_DEPOT_NS_V1_0);
    assertEquals("org.adligo.fabricate.xml.io_v1.dev_v1_0",SchemaLoader.JAVA_DEV_NS_V1_0);
    assertEquals("org.adligo.fabricate.xml.io_v1.fabricate_v1_0",SchemaLoader.JAVA_FAB_NS_V1_0);
    assertEquals("org.adligo.fabricate.xml.io_v1.library_v1_0",SchemaLoader.JAVA_LIB_NS_V1_0);
    assertEquals("org.adligo.fabricate.xml.io_v1.project_v1_0",SchemaLoader.JAVA_PROJECT_NS_V1_0);
    assertEquals("org.adligo.fabricate.xml.io_v1.result_v1_0",SchemaLoader.JAVA_RESULT_NS_V1_0);
    
    final SchemaLoader schemaLoader = SchemaLoader.INSTANCE;
    
    Schema schema = schemaLoader.get();
    System.out.println("Schema is " + schema);
    assertNotNull(schema);
    //schema.
    
    Validator validator = schema.newValidator();
    System.out.println("Validator is " + validator);
    //validator.
    
    //FabFiles ff = FabFiles.INSTANCE;
    //ff.parseProject_v1_0("project.xml");
    LSResourceResolver resourceResolver =  validator.getResourceResolver();
    System.out.println("LSResourceResolver is " + resourceResolver);
    //validator.
    //validator.getProperty(SchemaLoader.COMMON_NS_V1_0);
   
    LSInput in = schemaLoader.resolveResource("http://www.w3.org/2001/XMLSchema", SchemaLoader.COMMON_NS_V1_0, "", "", "");
    assertNotNull(in);
    assertEquals(SchemaLoader.COMMON_NS_V1_0, in.getPublicId());
    in = schemaLoader.resolveResource("http://www.w3.org/2001/XMLSchema", SchemaLoader.COMMON_NS_V1_0, "", "", "");
    assertEquals(SchemaLoader.COMMON_NS_V1_0, in.getPublicId());
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(SchemaLoader.UNKNOWN_NAMESPACE_URI + "foo.bar")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            schemaLoader.resolveResource("http://www.w3.org/2001/XMLSchema", "foo.bar", "", "", "");
          }
        });
  }
}
