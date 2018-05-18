package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedo stores and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500(TorpedoStore primaryTorpedoStore, TorpedoStore secondaryTorpedoStore) {
    this.primaryTorpedoStore = primaryTorpedoStore;//new TorpedoStore(10);
    this.secondaryTorpedoStore = secondaryTorpedoStore;//new TorpedoStore(10);
  }

  public boolean fireLaser(FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }

  /**
   * Tries to fire the torpedo stores of the ship.
   *
   * @param firingMode how many torpedo bays to fire
   *                   SINGLE: fires only one of the bays.
   *                   - For the first time the primary store is fired.
   *                   - To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
   *                   - But if the store next in line is empty, the ship tries to fire the other store.
   *                   - If the fired store reports a failure, the ship does not try to fire the other one.
   *                   ALL:	tries to fire both of the torpedo stores.
   * @return whether at least one torpedo was fired successfully
   */
  @Override
  public boolean fireTorpedo(FiringMode firingMode) {

    boolean firingSuccess = false;

    if (firingMode.equals(FiringMode.SINGLE)) {
      firingSuccess = fireSingleMode();
    } else if (firingMode.equals(FiringMode.ALL)) {
      // try to fire both of the torpedo stores
      firingSuccess = fireAllMode();
    }

    return firingSuccess;
  }

  private boolean fireSingleMode() {
    boolean firingSuccess = false;

    if (wasPrimaryFiredLast) {
      // try to fire the secondary first
      if (!secondaryTorpedoStore.isEmpty()) {
        firingSuccess = secondaryTorpedoStore.fire(1);
        wasPrimaryFiredLast = false;
      } else if (!primaryTorpedoStore.isEmpty()) {
        // although primary was fired last time, but the secondary is empty
        // thus try to fire primary again
          firingSuccess = primaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = true;
      }
      // if both of the stores are empty, nothing can be done, return failure
    } else {
      // try to fire the primary first
      if (!primaryTorpedoStore.isEmpty()) {
        firingSuccess = primaryTorpedoStore.fire(1);
        wasPrimaryFiredLast = true;
      } else if (!secondaryTorpedoStore.isEmpty()) {
        // although secondary was fired last time, but primary is empty
        // thus try to fire secondary again
          firingSuccess = secondaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = false;
        // if both of the stores are empty, nothing can be done, return failure
      }
    }
    return firingSuccess;
  }

  private boolean fireAllMode() {
    boolean primarySuccess = false;
    boolean secondarySuccess = false;

    primarySuccess = primaryTorpedoStore.fire(1);
    secondarySuccess = secondaryTorpedoStore.fire(1);
    wasPrimaryFiredLast = false;

    return primarySuccess || secondarySuccess;
  }
}
