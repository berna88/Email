package Email.configuration;

import javax.portlet.ActionRequest;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

public class Portal {
	
	private ThemeDisplay themeDisplay;
	private ServiceContext serviceContext;
	private ActionRequest actionRequest;
	
	
	public ActionRequest getActionRequest() {
		return actionRequest;
	}
	public void setActionRequest(ActionRequest actionRequest) {
		this.actionRequest = actionRequest;
	}
	public ThemeDisplay getThemeDisplay() {
		themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		return themeDisplay;
	}
	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		this.themeDisplay = themeDisplay;
	}
	public ServiceContext getServiceContext() {
		serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
		return serviceContext;
	}
	public void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}
	public Portal(ActionRequest actionRequest) {
		super();
		this.actionRequest = actionRequest;
	}
	
	public Long getUserId(){
		themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Long userId = themeDisplay.getUserId();
		return userId;
	}
	
	
	

}
