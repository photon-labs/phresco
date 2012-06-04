package com.photon.phresco.service;

import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;

/**
 * Cofiguration Service hosted at the URI path "/sample"
 */
@Path("/sample")
public class SampleService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getConfig() {
		String jsonString = "";
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream("sample.json");
			jsonString = IOUtils.toString(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
