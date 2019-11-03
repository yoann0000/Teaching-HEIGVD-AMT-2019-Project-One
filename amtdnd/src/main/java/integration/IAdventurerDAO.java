package integration;

import Model.Adventurer;

import javax.ejb.Local;

@Local
public interface IAdventurerDAO extends IDAO<String, Adventurer> { }
