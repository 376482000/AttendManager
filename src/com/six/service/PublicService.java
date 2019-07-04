package com.six.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PublicService {

	public void editPassword(HttpServletRequest request, HttpServletResponse response, String username );

}
