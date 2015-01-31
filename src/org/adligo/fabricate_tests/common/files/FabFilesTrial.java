package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.PatternFileMatcher;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.log.I_FabLogSystem;
import org.adligo.tests4j.run.common.FileUtils;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.AfterTrial;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.I_ReturnFactory;
import org.adligo.tests4j_4mockito.MockMethod;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SourceFileScope (sourceClass=FabFileIO.class,
  minCoverage=72.0,allowedCircularDependencies=CircularDependencies.AllowInnerOuterClasses)
public class FabFilesTrial extends MockitoSourceFileTrial {

  @AfterTrial
  public static void afterTrials() throws IOException {
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial" + File.separator + 
        "bar.txt";
    try {
      Files.delete(new File(path).toPath());
    } catch (NoSuchFileException x) {
      //do nothing
    }
    String pathFubar = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial" + File.separator + 
        "fubar.txt";
    try {
      Files.delete(new File(pathFubar).toPath());
    } catch (NoSuchFileException x) {
      //do nothing
    }
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodClose() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    Closeable closeable = mock(Closeable.class);
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(closeable).close();
    fabFiles.close(closeable);
    assertEquals(1, closeMethod.count());
  }
  
  
  @Test
  public void testMethodCloseExceptions() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    Closeable closeable = mock(Closeable.class);
    doThrow(new IOException("ioe")).when(closeable).close();
    //shouldn't throw a exception.
    fabFiles.close(closeable);
  }
  @Test
  public void testMethodCreateExceptions() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    String badFile = "/somewhere/somefile";
    if (File.separatorChar == '/') {
      badFile = "C:\\somewhere\\somefile";
    }
    String badFileName = badFile;
    /*
    FabFileIO fabFiles = new FabFileIO(sysMock);
    assertThrown(new ExpectedThrowable(new IOException(
        "There was a problem creating the following file;" + System.lineSeparator() +
        badFile)),
        new I_Thrower() {
          @Override
          public void run() throws Throwable {
            fabFiles.create(badFileName);
          }
        });
        */
  }  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodNewFileOutputStreamExceptions() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    FabFileIO fabFiles = new FabFileIO(sysMock, httpClient);
    
    final String notAFile = FileUtils.getRunDir() + "test_data" + File.separator +
          "fab_files_trial" + File.separator + "notADir" + File.separator +
          "foo.txt";
    
    assertThrown(new ExpectedThrowable(new FileNotFoundException(notAFile + " (No such file or directory)")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.newFileOutputStream(notAFile);
          }
        });
  } 
    
  @SuppressWarnings("boxing")
  @Test
  public void testMethodDownloadExceptions() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    FabFileIO fabFiles = new FabFileIO(sysMock, httpClient);
    
    
    when(httpClient.execute(any())).thenThrow(new IOException("abc"));
    assertThrown(new ExpectedThrowable(new IOException("abc")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.downloadFile("http://example.com/foo", "/somewhere/newfile");
          }
        });
    
    reset(httpClient);
    when(httpClient.execute(any())).thenThrow(new ClientProtocolException("abc"));
    assertThrown(new ExpectedThrowable(IOException.class,
        new ExpectedThrowable(new ClientProtocolException("abc"))), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.downloadFile("http://example.com/foo", "/somewhere/newfile");
          }
        });
    
    reset(httpClient);
    CloseableHttpResponse resp = mock(CloseableHttpResponse.class);
    StatusLine status = mock(StatusLine.class);
    when(status.getStatusCode()).thenReturn(300);
    when(resp.getStatusLine()).thenReturn(status);
    MockMethod<CloseableHttpResponse> executeMethod = new MockMethod<CloseableHttpResponse>(resp, true);
    when(httpClient.execute(any())).then(executeMethod);
    assertThrown(new ExpectedThrowable(new IOException(
        "Submitting a Http GET to the following url returned a invalid status code 300;" + System.lineSeparator() +
        "http://example.com/foo")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.downloadFile("http://example.com/foo", "/somewhere/newfile");
          }
        });
    
    when(status.getStatusCode()).thenReturn(200);
    //This is a intentionally bad file name
    final String badFile = FileUtils.getRunDir() + "test_data" + 
        File.separator + "notADir" + File.separator + "out.txt";
    
    HttpEntity entity = mock(HttpEntity.class);
    when(resp.getEntity()).thenReturn(entity);
    InputStream is = mock(InputStream.class);
    when(entity.getContent()).thenReturn(is);
    
    //note I tried stubbing this with spy in mockito, which didn't seem
    // to work.
    String fileMessage = badFile + " (No such file or directory)";
    String message = "java.io.FileNotFoundException: " + fileMessage;
    assertThrown(new ExpectedThrowable(new IOException(message),
        new ExpectedThrowable(new FileNotFoundException(fileMessage))), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.downloadFile("http://example.com/foo", badFile);
          }
        });
    
    
    
  }  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodDownloadSuccess() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    FabFileIO fabFiles = new FabFileIO(sysMock, httpClient);
    MockMethod<CloseableHttpResponse> executeMethod = new MockMethod<CloseableHttpResponse>();
    
    //fabFiles.downloadFile("http://example.com/foo", "/somewhere/newfile");
  }  
  
  @Test
  public void testMethodsLocalIO() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String separator = File.separator;
    
    if (fabFiles.exists("test_data" + separator + "file_trials" + separator + "fab_files_trial")) {
      fabFiles.removeRecursive("test_data" + separator + "file_trials" + separator + "fab_files_trial");
    }
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial"));
    assertTrue(fabFiles.mkdirs("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial" + separator + "temp"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial" + separator + "temp"));
    File file = fabFiles.create("test_data" + separator + "file_trials" + 
          separator + "fab_files_trial" + separator + "temp" + separator + "createdFile.txt");
    assertNotNull(file);
    assertEquals("createdFile.txt", file.getName());
    assertTrue(fabFiles.exists(file.getAbsolutePath()));
    fabFiles.deleteOnExit(file.getAbsolutePath());
    assertEquals(file.getAbsolutePath(), fabFiles.getAbsolutePath("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp" + separator + "createdFile.txt"));
    assertEquals(separator, fabFiles.getNameSeparator());
    
    assertEquals("test_data/file_trials/" + 
        "fab_files_trial/temp/createdFile.txt" ,fabFiles.getSlashPath("test_data\\file_trials\\" + 
        "fab_files_trial\\temp\\createdFile.txt"));
    File instanceFile = fabFiles.instance(file.getAbsolutePath());
    assertEquals("createdFile.txt", instanceFile.getName());
    
    assertTrue(fabFiles.mkdirs("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    assertTrue(fabFiles.mkdirs("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
    assertTrue(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
  
    fabFiles.removeRecursive("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2");
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodList() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.getLog()).thenReturn(logMock);
    
    List<String> files = fabFiles.list("test_data/file_trials/xml",
        new PatternFileMatcher(fabFiles, sysMock, "*.xml", true));
    assertEquals(4, files.size());
    assertEquals("depot.xml", new File(files.get(0)).getName());
    assertEquals("dev.xml", new File(files.get(1)).getName());
    assertEquals("fabricate.xml", new File(files.get(2)).getName());
    assertEquals("fabricateGroups.xml", new File(files.get(3)).getName());
  }
  
  @Test
  public void testMethodWriteFile() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    InputStream in = new ByteArrayInputStream(new String("fileContentString" +
        System.lineSeparator()).getBytes());
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial" + File.separator + 
        "bar.txt";
    File file = new File(path);
    if (!file.createNewFile()) {
      System.out.println("Failure to create " + file.getAbsolutePath());
    }
    FileOutputStream fos = new FileOutputStream(file);
    
    fabFiles.writeFile( in, fos,3);
    
    String fileContent = fabFiles.readFile(path);
    assertEquals("fileContentString" + System.lineSeparator(), fileContent);
    
  }
  
  @SuppressWarnings("resource")
  @Test
  public void testMethodWriteFileExceptions() throws Exception {
    I_FabLogSystem sysMock = mock(I_FabLogSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    InputStream in = mock(InputStream.class);
    FileOutputStream fos = mock(FileOutputStream.class);
    
    doThrow(new IOException("readex.")).when(in).read(any(byte[].class), anyInt(), anyInt());
    assertThrown(new ExpectedThrowable(new IOException("readex.")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.writeFile( in, fos,1);
          }
        });
    
    reset(in);
    doAnswer(newReadMethod()).when(in).read(any(byte[].class), anyInt(), anyInt());
    
    String pathFubar = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial" + File.separator + 
        "fubar.txt";
    FileChannel fileChanel = new FileOutputStream(new File(pathFubar)).getChannel();
    when(fos.getChannel()).thenReturn(fileChanel);
    
    doThrow(new IOException()).when(in).close();
    doThrow(new IOException()).when(fos).close();
    fabFiles.writeFile( in, fos, 1);
  }

  public MockMethod<Integer> newReadMethod() {
    MockMethod<Integer> readMethod = new MockMethod<Integer>(new I_ReturnFactory<Integer>() {
      int counter = 0;
      @SuppressWarnings("boxing")
      @Override
      public Integer create(Object[] keys) {
        byte [] bytes = (byte []) keys[0];
        bytes[0] = (byte) counter++;
        if (counter >= 2) {
          return -1;
        }
        return 1;
      }
      
    }, false);
    return readMethod;
  }  
}
