package integration;

import Model.Guild;

import javax.ejb.Local;

@Local
public interface IGuildDAO extends IDAO<String, Guild>  {
}
