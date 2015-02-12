package org.adligo.fabricate_tests.common.files;

import org.adligo.fabricate.common.en.FabricateEnConstants;
import org.adligo.fabricate.common.files.FabFileIO;
import org.adligo.fabricate.common.files.I_FabFilesSystem;
import org.adligo.fabricate.common.files.PatternFileMatcher;
import org.adligo.fabricate.common.log.I_FabLog;
import org.adligo.fabricate.common.system.FabSystem;
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
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SourceFileScope (sourceClass=FabFileIO.class,
  minCoverage=75.0,allowedCircularDependencies=CircularDependencies.AllowInnerOuterClasses)
public class FabFilesTrial extends MockitoSourceFileTrial {

  @AfterTrial
  public static void afterTrials() throws IOException {
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial" + File.separator + 
        "bar.txt";
    deleteFile(path);
    String pathFubar = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial" + File.separator + 
        "fubar.txt";
    deleteFile(pathFubar);
    String pathUnzip = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial_extract";
    try {
      File file = new File(pathUnzip);
      if (file.exists()) {
        FabFileIO fabfileIO = new FabFileIO(new FabSystem());
        fabfileIO.deleteRecursive(pathUnzip);
      }
    } catch (NoSuchFileException x) {
      //do nothing
    }
    String pathUnzipExes = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "fab_files_trial_unzip_exceptions_extract";
    try {
      File file = new File(pathUnzipExes);
      if (file.exists()) {
        FabFileIO fabfileIO = new FabFileIO(new FabSystem());
        fabfileIO.deleteRecursive(pathUnzipExes);
      }
    } catch (NoSuchFileException x) {
      //do nothing
    }
    String download = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "index.html";
    deleteFile(download);
  }

  public static void deleteFile(String pathFubar) throws IOException {
    try {
      File file = new File(pathFubar);
      if (file.exists()) {
        Files.delete(file.toPath());
      }
    } catch (NoSuchFileException x) {
      //do nothing
    }
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
  
  @Test
  public void testMethodCalculateMd5() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "foo.txt";
    String md5 = fabFiles.calculateMd5(path);
    
    assertEquals("681cb6d93ebdc5936641d213ff361179",md5);
  }

  @Test
  public void testMethodCalculateMd5PassthroughExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "notADir" + File.separator + "foo.txt";
    File abs = new File(path);
    //test passthrough IOException
    assertThrown(new ExpectedThrowable(new NoSuchFileException(abs.getAbsolutePath())),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.calculateMd5(path);
          }
        });
  }
  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodCheck() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    doReturn(HttpClients.createDefault()).when(sysMock).newHttpClient();
    int status = fabFiles.check("http://adligo.org/index.html");
    assertEquals(200, status);
  } 
  
  @SuppressWarnings("boxing")
  @Test (timeout=3000)
  public void testMethodCheckExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    MockMethod<Void> closeClientMethod = new MockMethod<Void>();
    doReturn(httpClient).when(sysMock).newHttpClient();
    doAnswer(closeClientMethod).when(httpClient).close();
    FabFileIO fabFiles = new FabFileIO(sysMock);
    when(httpClient.execute(any())).thenThrow(new IOException("abc"));
    
    assertThrown(new ExpectedThrowable(new IOException("abc")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.check("http://example.com/foo");
          }
        });
    assertEquals(1, closeClientMethod.count());
    
    reset(httpClient);
    closeClientMethod = new MockMethod<Void>();
    doReturn(httpClient).when(sysMock).newHttpClient();
    doAnswer(closeClientMethod).when(httpClient).close();
    when(httpClient.execute(any())).thenThrow(new ClientProtocolException("abc"));
    assertThrown(new ExpectedThrowable(IOException.class,
        new ExpectedThrowable(new ClientProtocolException("abc"))), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.check("http://example.com/foo");
          }
        });
    assertEquals(1, closeClientMethod.count());
    
    reset(httpClient);
    closeClientMethod = new MockMethod<Void>();
    doReturn(httpClient).when(sysMock).newHttpClient();
    doAnswer(closeClientMethod).when(httpClient).close();
    
    CloseableHttpResponse resp = mock(CloseableHttpResponse.class);
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(resp).close();
    StatusLine status = mock(StatusLine.class);
    when(status.getStatusCode()).thenReturn(300);
    when(resp.getStatusLine()).thenReturn(status);
    MockMethod<CloseableHttpResponse> executeMethod = new MockMethod<CloseableHttpResponse>(resp, true);
    when(httpClient.execute(any())).then(executeMethod);
    assertEquals(300, fabFiles.check("http://example.com/foo"));
    assertEquals(1, closeMethod.count());
    assertEquals(1, closeClientMethod.count());
  }  
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodClose() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    Closeable closeable = mock(Closeable.class);
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(closeable).close();
    //check a null parameter
    assertNull(fabFiles.close(null));
    //check a regular close
    assertNull(fabFiles.close(closeable));
    assertEquals(1, closeMethod.count());
  }
  
  @SuppressWarnings({"boxing"})
  @Test
  public void testMethodCloseIOPair() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    ByteBuffer buffer = ByteBuffer.allocate(10);
    ReadableByteChannel in = mock(ReadableByteChannel.class);
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(in).close();
    WritableByteChannel out = mock(WritableByteChannel.class);
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(out).close();
    
    IOCloseTrackerStub tracker = new IOCloseTrackerStub();
    
    
    //test both null
    fabFiles.closeIOPair(null, null, tracker);
    
    fabFiles.closeIOPair(in, null, tracker);
    assertEquals(1, closeInMethod.count());
    assertEquals(0, closeOutMethod.count());
    
    fabFiles.closeIOPair(null, out, tracker);
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    
    fabFiles.closeIOPair(null, out, tracker);
    assertEquals(1, closeInMethod.count());
    assertEquals(2, closeOutMethod.count());
    
    when(in.read(buffer)).thenReturn(-1);
    doThrow(new IOException("close inex.")).when(in).close();
    
    fabFiles.closeIOPair(in, out, tracker);
    assertEquals(3, closeOutMethod.count());
    assertEquals(1, tracker.size());
    IOException ex = tracker.get(0);
    assertEquals("close inex.", ex.getMessage());
    
    reset(in);
    when(in.read(buffer)).thenReturn(-1);
    doAnswer(closeInMethod).when(in).close();
    doThrow(new IOException("close outex.")).when(out).close();
    
    tracker = new IOCloseTrackerStub();
    fabFiles.closeIOPair(in, out, tracker);
    assertEquals(2, closeInMethod.count());
    assertEquals(1, tracker.size());
    ex = tracker.get(0);
    assertEquals("close outex.", ex.getMessage());
  }
  
  @Test
  public void testMethodCloseExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    Closeable closeable = mock(Closeable.class);
    doThrow(new IOException("ioe")).when(closeable).close();
    //shouldn't throw a exception.
    IOException closeException = fabFiles.close(closeable);
    assertNotNull(closeException);
    assertEquals("ioe", closeException.getMessage());
  }
  
  @Test
  public void testMethodCreateExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    String badFile = "/somewhere/somefile";
    if (File.separatorChar == '/') {
      badFile = "C:\\somewhere\\somefile";
    }
    String badFileName = badFile;
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
  }  
  
  @Test
  public void testMethodNewFileOutputStreamExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
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

  @Test
  public void testMethodNewZipFile() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    final String notAFile = FileUtils.getRunDir() + "test_data" + File.separator +
          "fab_files_trial" + File.separator + "notADir" + File.separator +
          "foo.txt";
    
    assertThrown(new ExpectedThrowable(new FileNotFoundException(notAFile + " (No such file or directory)")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.newZipFile(notAFile);
          }
        });
    
  } 
  
  @Test
  public void testMethodNewZipFileExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    final String zfName = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "xml.zip";
    ZipFile zf = fabFiles.newZipFile(zfName);
    assertEquals(zfName, zf.getName());
  } 
  @Test
  public void testMethodReadBytesException() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "notADir" + File.separator + "foo.txt";
    File abs = new File(path);
    assertThrown(new ExpectedThrowable(new NoSuchFileException(abs.getAbsolutePath())),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.readAllBytes(path);
          }
        });
  }
  
  @Test
  public void testMethodReadFile() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "foo.txt";
    String fileContent = fabFiles.readFile(path);
    
    assertEquals("This is just a file for testing reading files;" + System.lineSeparator(),
        fileContent);
  }
  
  @Test
  public void testMethodDecodeExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String path = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "notADir" + File.separator + "foo.txt";
    File abs = new File(path);
    //test passthrough IOException
    assertThrown(new ExpectedThrowable(new NoSuchFileException(abs.getAbsolutePath())),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.decode(path,"MD5");
          }
        });
    
    
    String goodPath = FileUtils.getRunDir() + 
        "test_data" + File.separator + "file_trials" + File.separator +
        "foo.txt";
    //test passthrough IOException
    assertThrown(new ExpectedThrowable(IOException.class,
        new ExpectedThrowable(new NoSuchAlgorithmException("234lkj2lk4jMD5 MessageDigest not available"))),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.decode(goodPath,"234lkj2lk4jMD5");
          }
        });
  }
  
  
  @Test
  public void testMethodDelete() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String to = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "toDelete.txt";
    fabFiles.writeFile(new ByteArrayInputStream("abc".getBytes()), 
        new FileOutputStream(to));
    assertTrue(fabFiles.exists(to));
    fabFiles.delete(to);
    assertFalse(fabFiles.exists(to));
  }  
  
  
  @Test
  public void testMethodDeleteException() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String to = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "toDeleteNotAFile.txt";
    assertThrown(new ExpectedThrowable(new IOException("There was a problem deleting the following file;" + 
          System.lineSeparator() + new File(to).getAbsolutePath())),
        new I_Thrower() {

          @Override
          public void run() throws Throwable {
            fabFiles.delete(to);
          }
    });
    
  }  
  
  @Test
  public void testMethodDeleteRecursive() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String to = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "toDelete";
    fabFiles.mkdirs(to);
    assertTrue(fabFiles.exists(to));
    
    String toTo = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "toDelete" + File.separator + "child";
    fabFiles.mkdirs(toTo);
    assertTrue(fabFiles.exists(toTo));
    
    String toToBar = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "toDelete" + File.separator + "child" +
        File.separator + "bar.txt";
    fabFiles.writeFile(new ByteArrayInputStream("abc".getBytes()), 
        new FileOutputStream(toToBar));
    assertTrue(fabFiles.exists(toToBar));
    
    assertTrue(fabFiles.exists(toTo));
    fabFiles.deleteRecursive(to);
    assertFalse(fabFiles.exists(toTo));
    assertFalse(fabFiles.exists(to));
  }  
  
  @Test
  public void testMethodDeleteRecursiveException() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String to = FileUtils.getRunDir() + "test_data" + File.separator +
        "file_trials" + File.separator + "toDeleteNotAFile.txt";
    assertThrown(new ExpectedThrowable(new NoSuchFileException(
        new File(to).getAbsolutePath())),
        new I_Thrower() {

          @Override
          public void run() throws Throwable {
            fabFiles.deleteRecursive(to);
          }
    });
    
  }  
  
  @Test (timeout=3000)
  public void testMethodDownload() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    doReturn(HttpClients.createDefault()).when(sysMock).newHttpClient();
    
    String to = FileUtils.getRunDir() + "test_data" + File.separator +
          "file_trials" + File.separator + "index.html";
    fabFiles.downloadFile("http://adligo.com/index.html", to);
    
    String content = fabFiles.readFile(to);
    assertTrue(content.contains("Adligo"));
  }  
  

  @SuppressWarnings("boxing")
  @Test
  public void testMethodDownloadExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    MockMethod<Void> closeClientMethod = new MockMethod<Void>();
    doReturn(httpClient).when(sysMock).newHttpClient();
    doAnswer(closeClientMethod).when(httpClient).close();
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    
    when(httpClient.execute(any())).thenThrow(new IOException("abc"));
    assertThrown(new ExpectedThrowable(new IOException("abc")), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.downloadFile("http://example.com/foo", "/somewhere/newfile");
          }
        });
    assertEquals(1, closeClientMethod.count());
    
    reset(httpClient);
    closeClientMethod = new MockMethod<Void>();
    doReturn(httpClient).when(sysMock).newHttpClient();
    doAnswer(closeClientMethod).when(httpClient).close();
    when(httpClient.execute(any())).thenThrow(new ClientProtocolException("abc"));
    assertThrown(new ExpectedThrowable(IOException.class,
        new ExpectedThrowable(new ClientProtocolException("abc"))), 
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.downloadFile("http://example.com/foo", "/somewhere/newfile");
          }
        });
    assertEquals(1, closeClientMethod.count());
    
    reset(httpClient);
    closeClientMethod = new MockMethod<Void>();
    doReturn(httpClient).when(sysMock).newHttpClient();
    doAnswer(closeClientMethod).when(httpClient).close();
    
    HttpEntity entity = mock(HttpEntity.class);
    when(entity.getContentLength()).thenReturn(12L);
    InputStream is = mock(InputStream.class);
    when(entity.getContent()).thenReturn(is);
    
    CloseableHttpResponse resp = mock(CloseableHttpResponse.class);
    when(resp.getEntity()).thenReturn(entity);
    
    MockMethod<Void> closeMethod = new MockMethod<Void>();
    doAnswer(closeMethod).when(resp).close();
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
    assertEquals(1, closeClientMethod.count());
    assertEquals(1, closeMethod.count());
    
    when(status.getStatusCode()).thenReturn(200);
    //This is a intentionally bad file name
    final String badFile = FileUtils.getRunDir() + "test_data" + 
        File.separator + "notADir" + File.separator + "out.txt";
    

    
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
    assertEquals(2, closeClientMethod.count());
    assertEquals(2, closeMethod.count());
  }  
  
  @Test
  public void testMethodsLocalIO() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    FabFileIO fabFiles = new FabFileIO(sysMock);
    String separator = File.separator;
    
    if (fabFiles.exists("test_data" + separator + "file_trials" + separator + "fab_files_trial")) {
      fabFiles.deleteRecursive("test_data" + separator + "file_trials" + separator + "fab_files_trial");
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
  
    fabFiles.deleteRecursive("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2");
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2" + separator + "temp3"));
    assertFalse(fabFiles.exists("test_data" + separator + "file_trials" + 
        separator + "fab_files_trial" + separator + "temp2"));
    
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodList() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
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
  public void testMethodUnzip() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String in = FileUtils.getRunDir() + "test_data" + 
        File.separator + "file_trials" + File.separator + 
        "xml.zip";
    String out = FileUtils.getRunDir() + "test_data" + 
        File.separator + "file_trials" + File.separator +
        "fab_files_trial_extract";
    fabFiles.unzip(in,out);
    
    assertEquals("a3a6455469a62e90b72bfec43c3f6282",fabFiles.calculateMd5(out + File.separator + "xml" +
        File.separator + "depot.xml"));
    String devFile = out + File.separator + "xml" +
        File.separator + "dev.xml";
    assertEquals("8855bce1e158291df5d40892e6188d4b",fabFiles.calculateMd5(devFile));
    String devContent = fabFiles.readFile(devFile);
    assertUniform("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        "<dev xmlns=\"http://www.adligo.org/fabricate/xml/io_v1/dev_v1_0.xsd\" "
      + "project_group=\"some_project_group.example.com\"/>", devContent);
    assertEquals("d3985417a6b745a096ca441c909f1420",fabFiles.calculateMd5(out + File.separator + "xml" +
        File.separator + "fabricate.xml"));
    assertEquals("d8ba1d428b0f5b034438c738d12c0dff",fabFiles.calculateMd5(out + File.separator + "xml" +
        File.separator + "fabricateGroups.xml"));
  }
  
  @SuppressWarnings("boxing")
  @Test
  public void testMethodUnzipExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String out = FileUtils.getRunDir() + "test_data" + 
        File.separator + "file_trials" + File.separator + 
        "fab_files_trial_unzip_exceptions_extract";
    //pass through exception from write(no directory)
    ZipFile zf = mock(ZipFile.class);
    Vector<ZipEntry> vze = new Vector<ZipEntry>();
    vze.addElement(new ZipEntry("xml/"));
    ZipEntry ze = new ZipEntry("xml/dev.xml");
    vze.addElement(ze);
    when(zf.getInputStream(ze)).thenThrow(new IOException("ioe"));
    
    Enumeration<? extends ZipEntry> entries = vze.elements(); 
    MockMethod<Enumeration<? extends ZipEntry>> entriesMethod = new
          MockMethod<Enumeration<? extends ZipEntry>>(entries, true);
    
    when(zf.entries()).then(entriesMethod);
    IOCloseTrackerStub closeTracker = new IOCloseTrackerStub();
    assertThrown(new ExpectedThrowable(new IOException("ioe")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.extractZipFile(out, zf, closeTracker);
          }
        });
    
    //io close ZipFile close exception
    reset(zf);
    entries = vze.elements(); 
    entriesMethod = new
          MockMethod<Enumeration<? extends ZipEntry>>(entries, true);
    when(zf.entries()).then(entriesMethod);
    when(zf.getInputStream(ze)).thenReturn(new ByteArrayInputStream(
        new String("baos").getBytes()));
    doThrow(new IOException("cioe.")).when(zf).close();
    
    fabFiles.extractZipFile(out, zf, closeTracker);
    assertEquals(1, closeTracker.size());
    IOException coie = closeTracker.get(0);
    assertEquals("cioe.", coie.getMessage());
    
  }
  
  @Test
  public void testMethodWriteFile() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
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
    FileOutputStream fos = new FileOutputStream(file);
    
    fabFiles.writeFile( in, fos,3);
    
    String fileContent = fabFiles.readFile(path);
    assertEquals("fileContentString" + System.lineSeparator(), fileContent);
    
    Files.delete(new File(path).toPath());
    
    in = new ByteArrayInputStream(new String("fileContentString" +
        System.lineSeparator()).getBytes());
    fos = new FileOutputStream(file);
    fabFiles.writeFile(in, fos);
    
    fileContent = fabFiles.readFile(path);
    assertEquals("fileContentString" + System.lineSeparator(), fileContent);
    
    Files.delete(new File(path).toPath());
  }
  
  @Test
  public void testMethodVerifyZip() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    String in = FileUtils.getRunDir() + "test_data" + 
        File.separator + "file_trials" + File.separator + 
        "xml.zip";
    String out = FileUtils.getRunDir() + "test_data" + 
        File.separator + "file_trials" + File.separator +
        "fab_files_trial_extract";
    fabFiles.unzip(in,out);
    
    assertFalse(fabFiles.verifyZipFileExtract(out + File.separator + "notADir", 
        new ZipFile(in)));
    assertTrue(fabFiles.verifyZipFileExtract(out, new ZipFile(in)));
    
    String devFile = out + File.separator + "xml" +
        File.separator + "dev.xml";
    fabFiles.delete(devFile);
    assertFalse(fabFiles.verifyZipFileExtract(out, new ZipFile(in)));
  }
 

  @SuppressWarnings({"resource", "boxing"})
  @Test
  public void testMethodWriteFileExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    InputStream in = mock(InputStream.class);
    FileOutputStream fos = mock(FileOutputStream.class);
    
    //test propagation of exception out of method
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
    
    doThrow(new IOException("in")).when(in).close();
    doThrow(new IOException("out")).when(fos).close();
    IOCloseTrackerStub tracker = new IOCloseTrackerStub();
    //in this case the 'in' exception, is thrown twice
    // once from the close of the Channel, once from the close of
    // the input stream itself.  I really don't like the javadoc;
    // http://docs.oracle.com/javase/7/docs/api/java/nio/channels/Channel.html#close()
    // It should specify that implementations Must call close on delegate instances,
    // since it seems to do this in this case, but doesn't mention it
    // in the javadoc, so you can't rely on it always doing it.
    fabFiles.writeFileWithCloseTracker(in, fos, 1, tracker, -1 , "");
    assertEquals(3, tracker.size());
    IOException inE = tracker.get(0);
    assertEquals("in", inE.getMessage());
    inE = tracker.get(1);
    assertEquals("in", inE.getMessage());
    IOException outE = tracker.get(2);
    assertEquals("out", outE.getMessage());
  }
  
  @SuppressWarnings({ "boxing"})
  @Test
  public void testMethodWriteFileWithBufferExceptions() throws Exception {
    I_FabFilesSystem sysMock = mock(I_FabFilesSystem.class);
    when(sysMock.getConstants()).thenReturn(FabricateEnConstants.INSTANCE);
    when(sysMock.lineSeperator()).thenReturn(System.lineSeparator());
    
    I_FabLog logMock = mock(I_FabLog.class);
    when(sysMock.getLog()).thenReturn(logMock);
    
    FabFileIO fabFiles = new FabFileIO(sysMock);
    
    ByteBuffer buffer = ByteBuffer.allocate(10);
    ReadableByteChannel in = mock(ReadableByteChannel.class);
    MockMethod<Void> closeInMethod = new MockMethod<Void>();
    doAnswer(closeInMethod).when(in).close();
    WritableByteChannel out = mock(WritableByteChannel.class);
    MockMethod<Void> closeOutMethod = new MockMethod<Void>();
    doAnswer(closeOutMethod).when(out).close();
    
    IOCloseTrackerStub tracker = new IOCloseTrackerStub();
    doThrow(new IOException("readex.")).when(in).read(buffer);
    assertThrown(new ExpectedThrowable(new IOException("readex.")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.writeFileWithBuffers(buffer, in, out, tracker);
          }
        });
    assertEquals(1, closeInMethod.count());
    assertEquals(1, closeOutMethod.count());
    assertEquals(0, tracker.size());
    
    reset(in);
    doAnswer(closeInMethod).when(in).close();
    doThrow(new IOException("writeex.")).when(out).write(buffer);
    assertThrown(new ExpectedThrowable(new IOException("writeex.")),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fabFiles.writeFileWithBuffers(buffer, in, out, tracker);
          }
        });
    assertEquals(2, closeInMethod.count());
    assertEquals(2, closeOutMethod.count());
    assertEquals(0, tracker.size());
    
    reset(in);
    when(in.read(buffer)).thenReturn(-1);
    doThrow(new IOException("close inex.")).when(in).close();
    
    fabFiles.writeFileWithBuffers(buffer, in, out, tracker);
    assertEquals(3, closeOutMethod.count());
    assertEquals(1, tracker.size());
    IOException ex = tracker.get(0);
    assertEquals("close inex.", ex.getMessage());
    
    
    reset(in);
    when(in.read(buffer)).thenReturn(-1);
    doAnswer(closeInMethod).when(in).close();
    doThrow(new IOException("close outex.")).when(out).close();
    
    IOCloseTrackerStub tracker2 = new IOCloseTrackerStub();
    fabFiles.writeFileWithBuffers(buffer, in, out, tracker2);
    assertEquals(3, closeInMethod.count());
    assertEquals(1, tracker2.size());
    ex = tracker2.get(0);
    assertEquals("close outex.", ex.getMessage());
  }
}
