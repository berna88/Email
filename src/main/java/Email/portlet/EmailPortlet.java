package Email.portlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import Email.configuration.Portal;
import Email.constants.Contants;
import Email.mail.Contacto;
import Email.notification.Notification;
import mx.com.multiva.solicitudservicio.service.SolicitudLocalService;

/**
 * @author Bernardo Hernández Ramírez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.init-param.copy-request-parameters=false", 
		"com.liferay.portlet.display-category=Multiva",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Contacto Multiva",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Contants.EMAIL,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user,guest"
	},
	service = Portlet.class
)
public class EmailPortlet extends MVCPortlet {
	
	// Log permite un mayor detalle en la localizacion de los errores
	private static final Log log = LogFactoryUtil.getLog(EmailPortlet.class);
	// Objeto de Service builder
	private SolicitudLocalService _solicitudLocalService;
	private UserNotificationEventLocalService _userNotificationEventLocalService;
	
	/**
	 * @param request 
	 * @param response
	 */
	public void sendMail( ActionRequest request,  ActionResponse response){
		log.info("<--- sendMail -->");
		Portal portal = new Portal(request);
		try {
	
			String destino = "test@liferay.com";
			if(!portal.getThemeDisplay().getSiteGroup().getExpandoBridge().getAttribute("correoDestino").toString().equals(null) && !portal.getThemeDisplay().getSiteGroup().getExpandoBridge().getAttribute("correoDestino").toString().equals("")){
				destino = portal.getThemeDisplay().getSiteGroup().getExpandoBridge().getAttribute("correoDestino").toString();
			}
			
			String correoDestino = destino;
			String destinatario = correoDestino;
			String remitente = (!ParamUtil.getString(request, Contants.CORREO).isEmpty())?ParamUtil.getString(request, Contants.CORREO):"test@liferay.com";
			String nombre = (!ParamUtil.getString(request, Contants.NOMBRE).isEmpty())?ParamUtil.getString(request, Contants.NOMBRE):"Sin nombre";
			String asunto = Contants.ASUNTO;
			String contenido = (!ParamUtil.getString(request, Contants.COMENTARIOS).isEmpty())?ParamUtil.getString(request, Contants.COMENTARIOS):"test";
			String producto = (!ParamUtil.getString(request, Contants.PRODUCTO).isEmpty())?ParamUtil.getString(request, Contants.PRODUCTO):"Sin producto";
			String hora = (!ParamUtil.getString(request, Contants.HORARIO).isEmpty())?ParamUtil.getString(request, Contants.HORARIO):"Sin hora";
			String telefono = (!ParamUtil.getString(request, Contants.TELEFONO).isEmpty())?ParamUtil.getString(request, Contants.TELEFONO):"Sin telefono";
			
			Contacto contacto = new Contacto(remitente, destinatario, nombre, asunto, contenido, producto, hora, telefono);
			//Metodo que envia a correo
			contacto.enviarCorreo();
			
			//Metodo de service builder
			_solicitudLocalService.addSolicitud(portal.getUserId(), nombre, telefono, remitente, asunto, producto, hora, contenido, portal.getServiceContext());
		
			JSONObject payloadJSON =JSONFactoryUtil.createJSONObject();
			payloadJSON.put("userId", portal.getUserId());
			payloadJSON.put("title", asunto);
			payloadJSON.put("senderName",remitente);
			payloadJSON.put("notificationText",contenido);
			_userNotificationEventLocalService.addUserNotificationEvent(portal.getUserId(),
		    		                        Notification.PORTLET_ID,
		    		                        (new Date()).getTime(),
		    		                        UserNotificationDeliveryConstants.TYPE_WEBSITE, 
		    		                        portal.getUserId(),
		    		                        payloadJSON.toString(),
		    		                        false,
		    		                        portal.getServiceContext());
			
			/*Role role = RoleLocalServiceUtil.getRole(portal.getThemeDisplay().getCompanyId(), "CallCenter");
			
			List<Long> roleIds = new ArrayList<>();
			roleIds.add(role.getRoleId());
			
			for (Long long1 : roleIds) {
				List<User> us = UserLocalServiceUtil.getRoleUsers(long1);
			}*/
			
			
			
			 List<Company> companies=CompanyLocalServiceUtil.getCompanies();
	            for(Company company:companies)
	            {
	                List<Role> roles=RoleLocalServiceUtil.getRoles(company.getCompanyId());
	                for(Role role:roles)
	                {
	                	if(role.getName().equals("CallCenter")){
	                		System.out.println("Usuarios: "+UserLocalServiceUtil.getUser(role.getUserId()));
		                    System.out.println(role.getRoleId()+" "+role.getName());
	                	}
	                }
	            }
		}catch (NullPointerException e) {
			// TODO: handle exception
			
			e.printStackTrace();
			log.error(Contants.CAUSE+ e.getCause());
			log.error(Contants.LOCATIONMESSAGE + e.getLocalizedMessage());
			log.error(Contants.MESSAGE+ e.getMessage());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(Contants.CAUSE+ e.getCause());
			log.error(Contants.LOCATIONMESSAGE + e.getLocalizedMessage());
			log.error(Contants.MESSAGE+ e.getMessage());
			
		}
	}// Fin de metodo sendMail
	
	@Reference(unbind = "-")
	protected void setSolicitudService(SolicitudLocalService solicitudLocalService) {
	   _solicitudLocalService = solicitudLocalService;
	}// fin setSolicitudService

	@Reference(unbind = "-")
	protected void setUserNotificationEventLocalService(UserNotificationEventLocalService userNotificationEventLocalService) {
		_userNotificationEventLocalService = userNotificationEventLocalService;
	}// fin setSolicitudService

	
	
	
	
}