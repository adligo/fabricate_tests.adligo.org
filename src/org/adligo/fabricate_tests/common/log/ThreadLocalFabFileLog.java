package org.adligo.fabricate_tests.common.log;

import org.adligo.fabricate.common.log.I_Print;

import java.util.ArrayList;
import java.util.List;

public class ThreadLocalFabFileLog implements I_Print {
  private ThreadLocal<List<String>> prints = new ThreadLocal<List<String>>();
  private ThreadLocal<List<Throwable>> throwables = new ThreadLocal<List<Throwable>>();
  
  @Override
  public void println(String p) {
    List<String> val =  prints.get();
    if (val == null) {
      val = new ArrayList<String>();
      prints.set(val);
    }
    val.add(p);
  }

  @Override
  public void printTrace(Throwable t) {
    List<Throwable> val =  throwables.get();
    if (val == null) {
      val = new ArrayList<Throwable>();
      throwables.set(val);
    }
    val.add(t);
  }

  public List<String> getLines() {
    return prints.get();
  }
  
  public List<Throwable> getThrowables() {
    return throwables.get();
  }
}
