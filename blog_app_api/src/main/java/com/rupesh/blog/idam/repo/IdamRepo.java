package com.rupesh.blog.idam.repo;

import java.util.List;
import java.util.Map;

public interface IdamRepo {

	public List<Map<String, Object>> getAllDetails();

	public List<Map<String, Object>> getDetailsByLoginId(List<String> list);

	public List<Map<String, Object>> getDetailsByTimeStamp(String timestamp);

}
