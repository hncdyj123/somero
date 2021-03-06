package com.jzx.bda.somero.dubbomonitor.provider.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jzx.bda.somero.dubbomonitor.LeaderService;
import com.jzx.bda.somero.mysql.persistent.service.AppService;
import com.jzx.bda.somero.mysql.persistent.service.SeedService;
import com.jzx.bda.somero.mysql.persistent.service.ServiceService;

public class LeaderServiceImpl implements LeaderService {

	@Override
	public Map<String, String> registerClient(String name, List<String> services) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("seed", seedService.getSeed().toString());
		map.put(name, appService.getAppId(name).toString());
		for (String serviceName : services) {
			map.put("serviceName", serviceService.getServiceId(serviceName, name).toString());
		}
		return map;
	}

	@Override
	public String registerClient(String name, String service) {
		return serviceService.getServiceId(service, name).toString();
	}

	private ServiceService serviceService;
	private SeedService seedService;
	private AppService appService;

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public void setSeedService(SeedService seedService) {
		this.seedService = seedService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
}
