package model;

import java.util.ArrayList;

/**
 * Interface for read-only central system data.
 * Provides methods to retrieve information about users without allowing modification.
 */
public interface ReadOnlyCentralSystem {

  /**
   * Retrieves the IDs of all users in the central system.
   *
   * @return The list of user IDs.
   */
  public ArrayList<String> getUserIds();

  /**
   * Retrieves information about a user with the specified ID.
   *
   * @param uid The ID of the user to retrieve.
   * @return Information about the user as a read-only object.
   */
  public ReadOnlyUsers getUser(String uid);

}
