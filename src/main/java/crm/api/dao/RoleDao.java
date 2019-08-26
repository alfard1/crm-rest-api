package crm.api.dao;

import crm.api.entity.Role;

public interface RoleDao {
	public Role findRoleByName(String theRoleName);
}
