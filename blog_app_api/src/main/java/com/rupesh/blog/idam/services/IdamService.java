package com.rupesh.blog.idam.services;

import com.rupesh.blog.idam.dto.FullIdamReconRequest;
import com.rupesh.blog.idam.dto.IdamRequestLoginTimestamp;

public interface IdamService {

	public FullIdamReconRequest getallDetails();

	public FullIdamReconRequest getDetailsByLoginIdOrTimeStamp(IdamRequestLoginTimestamp request);


}
