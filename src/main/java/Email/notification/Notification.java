package Email.notification;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import Email.constants.Contants;


@Component(service = UserNotificationHandler.class)
public class Notification extends BaseUserNotificationHandler{
	
	
	public static final String PORTLET_ID = "portlet_ContactoMultiva_INSTANCE_EqpJJKozgAKs";
 
	public Notification(){
		setPortletId(Notification.PORTLET_ID);
		
	}
	
	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());
		String notificationText = jsonObject.getString("notificationText");
		String title = jsonObject.getString("titulo");
		String senderName = jsonObject.getString("senderName");
		String body = StringUtil.replace(getBodyTemplate(), 
				new String[] { "{$TITLE$}", "[$SENDERNAME$]", "[$BODY_TEXT$]" }, 
				new String[] { title, senderName, notificationText});
		return body;
	}
	
	
	
	
	@Override
	protected String getBodyTemplate() throws Exception {
		// TODO Auto-generated method stub
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\"> Title::[$TITLE$]</div><div ");
		sb.append("class=\"body\"Sender::[$SENDERNAME$] <br>Notification::[$BODY_TEXT$]</div>");
		return sb.toString();
	}

}
