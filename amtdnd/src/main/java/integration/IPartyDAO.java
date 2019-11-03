package integration;

import Model.Party;

import javax.ejb.Local;

@Local
public interface IPartyDAO extends IDAO<String, Party>{
}
