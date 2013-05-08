package be.ehb.iwt.sidinapp.restlet;

import org.restlet.engine.Engine;
import org.restlet.ext.httpclient.HttpClientHelper;
import org.restlet.ext.jackson.JacksonConverter;

public class EngineConfiguration {
	private static EngineConfiguration uniqueInstance = new EngineConfiguration();

	public static EngineConfiguration getInstance() {
		return uniqueInstance;
	};

	public String mGaeDBPath;
	public String mGaeDeptPath;

	public EngineConfiguration() {
		Engine.getInstance().getRegisteredConverters().clear();
		Engine.getInstance().getRegisteredConverters()
				.add(new JacksonConverter());
		
		Engine.getInstance().getRegisteredClients().clear();
		Engine.getInstance().getRegisteredClients()
				.add(new HttpClientHelper(null));
	}

	public String getMgaeDBPath() {
		return mGaeDBPath;
	}

	public String getMgaeDeptPath() {
		return mGaeDeptPath;
	}

	public void setMgaeDBPath(String mgaeDBPath) {
		this.mGaeDBPath = mgaeDBPath;
	}

	public void setMgaeDeptPath(String mgaeDeptPath) {
		this.mGaeDeptPath = mgaeDeptPath;
	}
}