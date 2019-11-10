package integration;

import Model.Adventurer;
import datastore.exception.KeyNotFoundException;

import javax.ejb.Local;

@Local
public interface IAdventurerDAO extends IDAO<String, Adventurer> {
    Adventurer findById(String id) throws KeyNotFoundException;
}
