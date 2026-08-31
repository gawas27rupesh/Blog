package com.rupesh.blog.idam.repoImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rupesh.blog.idam.QueryConstant.IdamQueriesConstant;
import com.rupesh.blog.idam.dto.FullIdamReconRequest;
import com.rupesh.blog.idam.repo.IdamRepo;

@Repository
public class IdamRepoImpl implements IdamRepo {

	private final JdbcTemplate jdbcTemplate;

	public IdamRepoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getAllDetails() {

		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(IdamQueriesConstant.GET_All_USER_DETAILS);
		return queryForList;
	}

	@Override
	public List<Map<String, Object>> getDetailsByLoginId(List<String> list) {

		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}

		String placeholders = String.join(",", Collections.nCopies(list.size(), "?"));

		String sql = IdamQueriesConstant.GET_USER_DETAILS_BY_LOGINID.replace("IN_PLACEHOLDER", placeholders);

		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, list.toArray());
		return queryForList;
	}

	@Override
	public List<Map<String, Object>> getDetailsByTimeStamp(String timestamp) {
		List<Map<String, Object>> queryForList = jdbcTemplate
				.queryForList(IdamQueriesConstant.GET_USER_DETAILS_BY_TIMESTAMP, timestamp);

		return queryForList;
	}

}
