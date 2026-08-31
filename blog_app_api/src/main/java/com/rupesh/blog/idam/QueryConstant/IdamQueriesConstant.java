package com.rupesh.blog.idam.QueryConstant;

public class IdamQueriesConstant {

	 public static final String GET_All_USER_DETAILS ="SELECT d.loginId AS loginId, d.name AS name, d.mobile AS mobile, \r\n"
	 		+ "d.email AS email, d.userType AS userType, d.employeeId AS employeeId, \r\n"
	 		+ "map.roleid AS roleCode, roles.rolename AS roleDesc FROM data d \r\n"
	 		+ "LEFT JOIN vtamusrrlemap map ON d.employeeId = map.employeeId LEFT JOIN vtamrole roles \r\n"
	 		+ "ON map.roleid = roles.roleid";
	 
	 public static final String GET_USER_DETAILS_BY_LOGINID =
	            "SELECT d.loginId AS loginId, d.name AS name, d.mobile AS mobile, \r\n"
	            + "d.email AS email, d.userType AS userType, d.employeeId AS employeeId, \r\n"
	            + "map.roleid AS roleCode, roles.rolename AS roleDesc FROM data d \r\n"
	            + "LEFT JOIN vtamusrrlemap map ON d.employeeId = map.employeeId LEFT JOIN vtamrole roles \r\n"
	            + "ON map.roleid = roles.roleid\r\n"
	            + "WHERE d.loginId IN (IN_PLACEHOLDER)";
	 
	 public static final String GET_USER_DETAILS_BY_TIMESTAMP =
	            "SELECT d.loginId AS loginId, d.name AS name, d.mobile AS mobile, \r\n"
	            + "d.email AS email, d.userType AS userType, d.employeeId AS employeeId, \r\n"
	            + "map.roleid AS roleCode, roles.rolename AS roleDesc FROM data d \r\n"
	            + "LEFT JOIN vtamusrrlemap map ON d.employeeId = map.employeeId LEFT JOIN vtamrole roles \r\n"
	            + "ON map.roleid = roles.roleid\r\n"
	            + "WHERE d.created_at >= ?";
}
