package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.routines.AbstractRoutine;
import org.adligo.fabricate.routines.I_GenericTypeAware;
import org.adligo.fabricate.routines.I_InputAware;

import java.util.List;

public class BadInputRoutineMock extends AbstractRoutine implements I_InputAware<String>, I_GenericTypeAware {

  @Override
  public List<Class<?>> getClassType(Class<?> interfaceClass) {
    return null;
  }

  @Override
  public void setInput(String input) {
    
  }

}
