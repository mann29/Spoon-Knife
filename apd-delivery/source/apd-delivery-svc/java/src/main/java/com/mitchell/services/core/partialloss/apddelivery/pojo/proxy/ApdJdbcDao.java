package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;

public interface ApdJdbcDao {

	String getNetworkFlag(String orgId) throws MitchellException;
}
