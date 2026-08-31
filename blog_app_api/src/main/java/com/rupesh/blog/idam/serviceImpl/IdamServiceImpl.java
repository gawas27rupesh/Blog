package com.rupesh.blog.idam.serviceImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupesh.blog.idam.dto.Data;
import com.rupesh.blog.idam.dto.FullIdamReconRequest;
import com.rupesh.blog.idam.dto.IdamRequestLoginTimestamp;
import com.rupesh.blog.idam.dto.Role;
import com.rupesh.blog.idam.repo.IdamRepo;
import com.rupesh.blog.idam.services.IdamService;

@Service
public class IdamServiceImpl implements IdamService {

	@Autowired
	private IdamRepo idamRepo;

	@Override
	public FullIdamReconRequest getallDetails() {
		List<Map<String, Object>> rows = idamRepo.getAllDetails();
		FullIdamReconRequest sortingListOfUserAndRoles = sortingListOfUserAndRoles(rows);
		return sortingListOfUserAndRoles;

	}

	@Override
	public FullIdamReconRequest getDetailsByLoginIdOrTimeStamp(IdamRequestLoginTimestamp request) {
		List<Map<String, Object>> listOfDetails = new ArrayList<Map<String, Object>>();
		FullIdamReconRequest sortingListOfUserAndRoles = new FullIdamReconRequest();
		if (!request.getLoginId().isEmpty()) {
			listOfDetails = idamRepo.getDetailsByLoginId(request.getLoginId());
			sortingListOfUserAndRoles = sortingListOfUserAndRoles(listOfDetails);
		} else if (!StringUtils.isBlank(request.getTimestamp())) {
			listOfDetails = idamRepo.getDetailsByTimeStamp(request.getTimestamp());
			sortingListOfUserAndRoles = sortingListOfUserAndRoles(listOfDetails);
		}
		return sortingListOfUserAndRoles;
	}

	private FullIdamReconRequest sortingListOfUserAndRoles(List<Map<String, Object>> rows) {
		FullIdamReconRequest response = new FullIdamReconRequest();
		Map<String, Data> userMap = new LinkedHashMap<>();

		for (Map<String, Object> row : rows) {
			String loginId = (String) row.get("LOGINID");
			Data data = userMap.get(loginId);

			if (data == null) {
				data = new Data();
				data.setLoginId((String) row.get("LOGINID"));
				data.setName((String) row.get("NAME"));
				data.setMobile((String) row.get("MOBILE"));
				data.setEmail((String) row.get("EMAIL"));
				data.setUserType((String) row.get("USERTYPE"));
				data.setEmployeeId((String) row.get("EMPLOYEEID"));
				data.setRole(new ArrayList<Role>());
				userMap.put(loginId, data);
			}

			String roleCode = (String) row.get("ROLECODE");
			String roleDesc = (String) row.get("ROLEDESC");

			if (roleCode != null) {
				Role role = new Role();
				role.setRoleCode(roleCode);
				role.setRoleDesc(roleDesc);
				data.getRole().add(role);
			}
		}

		response.setData(new ArrayList<Data>(userMap.values()));

		if (!response.getData().isEmpty()) {
			response.setStatus("SUCCESS");
			response.setErrorCode("00");
			response.setDescription("Data fetched successfully");
		} else {
			response.setStatus("SUCCESS");
			response.setErrorCode("01");
			response.setDescription("No Users Found");

		}
		return response;
	}

}
