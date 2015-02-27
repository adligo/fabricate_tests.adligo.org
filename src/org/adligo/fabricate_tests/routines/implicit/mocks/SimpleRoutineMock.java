package org.adligo.fabricate_tests.routines.implicit.mocks;

import org.adligo.fabricate.models.common.FabricationRoutineCreationException;
import org.adligo.fabricate.models.common.I_FabricationMemory;
import org.adligo.fabricate.models.common.I_FabricationMemoryMutant;
import org.adligo.fabricate.models.common.I_RoutineMemory;
import org.adligo.fabricate.models.common.I_RoutineMemoryMutant;
import org.adligo.fabricate.routines.AbstractRoutine;

public class SimpleRoutineMock extends AbstractRoutine {
  private boolean ran_ = false;
  private I_FabricationMemoryMutant lastMemoryMutant_;
  private I_FabricationMemory lastMemory_;
  
  @Override
  public void run() {
    ran_ = true;
    super.run();
  }

  @Override
  public boolean setup(I_FabricationMemoryMutant memory, I_RoutineMemoryMutant routineMemory) throws FabricationRoutineCreationException {
    lastMemoryMutant_ = memory;
    return super.setup(memory, routineMemory);
  }

  @Override
  public void setup(I_FabricationMemory memory, I_RoutineMemory routineMemory) throws FabricationRoutineCreationException {
    lastMemory_ = memory;
    super.setup(memory, routineMemory);
  }

  public boolean isRan() {
    return ran_;
  }

  public I_FabricationMemoryMutant getLastMemoryMutant() {
    return lastMemoryMutant_;
  }

  public I_FabricationMemory getLastMemory() {
    return lastMemory_;
  }

}
