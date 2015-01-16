package org.adligo.fabricate_tests.common;

import org.adligo.fabricate.common.FabricateXmlDiscovery;
import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.en.FileEnMessages;
import org.adligo.fabricate.common.i18n.I_FabricateConstants;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.files.FabFileIO;
import org.adligo.fabricate.files.I_FabFileIO;
import org.adligo.fabricate.files.xml_io.FabXmlFileIO;
import org.adligo.fabricate.files.xml_io.I_FabXmlFileIO;
import org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.IOException;

@SourceFileScope (sourceClass=FabricateXmlDiscovery.class)
public class FabricateXmlDiscoveryTrial extends MockitoSourceFileTrial {

  private I_FabLog logMock_;
  private I_FabFileIO filesMock_;
  private I_FabXmlFileIO xmlFilesMock_;
  
  private MockMethod<Void> printlnMock_;
  private MockMethod<File> instanceMethod_;
  private MockMethod<FabricateDevType> parseDev_v1_0Method_;
  
  private I_FabricateConstants constantsMock_;
  
  @Override
  public void beforeTests()  {
    printlnMock_ = new MockMethod<Void>();
    logMock_ = mock(I_FabLog.class);
    doAnswer(printlnMock_).when(logMock_).println(any());
    filesMock_ = mock(I_FabFileIO.class);
    setupPaths("/");
    
    when(filesMock_.instance(any())).then(instanceMethod_);
    
    constantsMock_ = mock(I_FabricateConstants.class);
    when(constantsMock_.getFileMessages()).thenReturn(FileEnMessages.INSTANCE);
    
    when(constantsMock_.getLineSeperator()).thenReturn("\n");
    when(logMock_.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    
    xmlFilesMock_ = mock(I_FabXmlFileIO.class);
    parseDev_v1_0Method_ = new MockMethod<FabricateDevType>(new I_ReturnFactory<FabricateDevType>() {

      @Override
      public FabricateDevType create(Object[] keys) {
        FabricateDevType toRet = mock(FabricateDevType.class);
        return toRet;
      }
    });
    try {
      when(xmlFilesMock_.parseDev_v1_0(any())).then(parseDev_v1_0Method_);
    } catch (IOException x) {
      throw new RuntimeException(x);
    }
  }

  public void setupPaths(String nameSeparator) {
    when(filesMock_.getNameSeparator()).thenReturn(nameSeparator);
    instanceMethod_ = new MockMethod<File>(new I_ReturnFactory<File>() {

      @Override
      public File create(Object[] keys) {
        //making nameSeparator final didn't help
        String nameSep = filesMock_.getNameSeparator();
        File fileMock = mock(File.class);
        String absolutePath = nameSep + "somewhere" + nameSep + keys[0];
        int lastSlash = absolutePath.lastIndexOf(nameSep);
        when(fileMock.getAbsolutePath()).thenReturn(absolutePath);
        String fileName = absolutePath.substring(lastSlash + 1, absolutePath.length());
        when(fileMock.getName()).thenReturn(fileName);
        return fileMock;
      }
    });
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testConstructorDirWithFabricateXml() {
    FabricateXmlDiscovery disc = new FabricateXmlDiscovery(null, null, false);
    assertSame(FabFileIO.INSTANCE, disc.getFiles());
    assertSame(FabXmlFileIO.INSTANCE, disc.getXmlFiles());
    
    when(filesMock_.exists("fabricate.xml")).thenReturn(true);
    disc = new FabricateXmlDiscovery(filesMock_, xmlFilesMock_, false);
    assertEquals("/somewhere/fabricate.xml", disc.getFabricateXmlPath());
  }
  
  @Test
  public void testConstructorDirWithProjectXml() {
    
  }
  
  @Test
  public void testConstructorDirWithProjectAndDevXml() {
    
  }
  
  @Test
  public void testConstructorDirWithProjectAndDevXml_DevParseIOException() {
    
  }
}
