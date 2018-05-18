package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMockTS;
  private TorpedoStore secondaryMockTS;

  @Before
  public void init(){
    primaryMockTS = mock(TorpedoStore.class);
    secondaryMockTS = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryMockTS, secondaryMockTS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);
    when(secondaryMockTS.fire(1)).thenReturn(true);


    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryMockTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMockTS.fire(1)).thenReturn(true);
    when(secondaryMockTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryMockTS, times(1)).fire(1);
    verify(secondaryMockTS, times(1)).fire(1);
  }

}
