package org.gradle;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/DeviceService")
public class DeviceService {

	DeviceInfoDao deviceInfoDao = new DeviceInfoDao();

	@GET
	@Path("/deviceinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getDevices() {
		return deviceInfoDao.getAllDevices();
	}

	@GET
	@Path("/onlinedeviceinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getOnlineDevices() {
		return deviceInfoDao.getAllOnlineDevices();
	}

	@GET
	@Path("/offlinedeviceinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getOfflineDevices() {
		return deviceInfoDao.getAllOfflineDevices();
	}

	@GET
	@Path("{x}/{y}/{radius}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getdevicesByRadius(@PathParam("x") String x, @PathParam("y") String y,
			@PathParam("radius") String radius) {
		return deviceInfoDao.getDevicesByRadius(x, y, radius);
	}
	
	@GET
	@Path("/lastseen")
	@Produces(MediaType.APPLICATION_JSON)
	public Device getLastSeen() {
		return deviceInfoDao.getLastSeenDevice();
	}
}