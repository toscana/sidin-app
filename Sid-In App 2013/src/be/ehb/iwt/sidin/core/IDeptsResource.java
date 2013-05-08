package be.ehb.iwt.sidin.core;

import org.restlet.resource.Get;

public interface IDeptsResource {
	
	@Get
	public DeptList retrieve();

}
