package Email.portlet;

import Email.constants.Contants;
import mx.com.multiva.solicitudservicio.service.SolicitudLocalService;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bernardo Hernández Ramírez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.init-param.copy-request-parameters=false", 
		"com.liferay.portlet.display-category=Sample.Multiva",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Contacto Multiva",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Contants.EMAIL,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class EmailPortlet extends MVCPortlet {
	
	// Log permite un mayor detalle en la localizacion de los errores
	private static final Log log = LogFactoryUtil.getLog(EmailPortlet.class);
	// Objeto de Service builder
	private SolicitudLocalService _solicitudLocalService;
	
	/**
	 * @param request 
	 * @param response
	 */
	public void sendMail( ActionRequest request,  ActionResponse response){
		log.info("<--- sendMail -->");
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			Group siteGroup = themeDisplay.getSiteGroup();
			String correoDestino = (String) siteGroup.getExpandoBridge().getAttribute("correoDestino");
			
			String para = correoDestino;
			String de = (!ParamUtil.getString(request, Contants.CORREO).isEmpty())?ParamUtil.getString(request, Contants.CORREO):"test@liferay.com";
			String nombre = (!ParamUtil.getString(request, Contants.NOMBRE).isEmpty())?ParamUtil.getString(request, Contants.NOMBRE):"Sin nombre";
			String asunto = Contants.ASUNTO;
			String contenido = (!ParamUtil.getString(request, Contants.COMENTARIOS).isEmpty())?ParamUtil.getString(request, Contants.COMENTARIOS):"test";
			String producto = (!ParamUtil.getString(request, Contants.PRODUCTO).isEmpty())?ParamUtil.getString(request, Contants.PRODUCTO):"Sin producto";
			String hora = (!ParamUtil.getString(request, Contants.HORARIO).isEmpty())?ParamUtil.getString(request, Contants.HORARIO):"Sin hora";
			String telefono = (!ParamUtil.getString(request, Contants.TELEFONO).isEmpty())?ParamUtil.getString(request, Contants.TELEFONO):"Sin telefono";
			
			//Metodo que envia a correo
			mail(para, de, nombre, asunto, contenido, producto,hora,telefono);
			//Metodo de service builder
			_solicitudLocalService.addSolicitud(getUserId(request).getUserId(), nombre, telefono, de, asunto, producto, hora, contenido, getContext(getUserId(request)));
		
			
			
		}catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(Contants.CAUSE+ e.getCause());
			log.error(Contants.LOCATIONMESSAGE + e.getLocalizedMessage());
			log.error(Contants.MESSAGE+ e.getMessage());
			
		}
	}// Fin de metodo sendMail
	
	/**
	 * @param para Esta variable de tipo cadena define a quien se le va a enviar el correo
	 * @param de Obtiene el correo del usuario
	 * @param nombre Obtiene el nombre del usuario
	 * @param asunto Obtiene el asunto colocado por defecto
	 * @param contenido Obtiene la información de los comentarios
	 * @param producto Obtiene el nombre del producto
	 */
	public void mail(String para, String de, String nombre, String asunto, String contenido, String producto, String hora, String telefono){
		log.info("<-- mail -->");
		try{
			
			InternetAddress fromAddress = new InternetAddress(de);
			InternetAddress toAddress = new InternetAddress(para);
			
			MailMessage mailMessage = new  MailMessage();
			mailMessage.setFrom(fromAddress);
			mailMessage.setTo(toAddress);
			mailMessage.setSubject(asunto);
			mailMessage.setHTMLFormat(true);
			mailMessage.setBody(""
					+ "<h5>Nombre del cliente: "+nombre+"<h5>"
					+ "<h5>Correo electronico: "+de+"<h5>"
					+ "<h5>Producto de interes: "+producto+"<h5>"
					+ "<h5>Horario de contacto: "+hora+"<h5>"
					+ "<h5>Telefono de referencia: "+telefono+"<h5>"
					+ "<p>Comentarios: "+contenido+"<p>");
			
			MailServiceUtil.sendEmail(mailMessage);
			log.info("El mensaje se envio correctamente");
			
		}catch (AddressException e) {
			// TODO: handle exception
			log.error(Contants.TRACK + e.getStackTrace());
			log.error(Contants.CAUSE + e.getCause());
			log.error(Contants.LOCATIONMESSAGE + e.getLocalizedMessage());
			log.error(Contants.MESSAGE + e.getMessage());
		}
	}// fin de mail
	
	
	@Reference(unbind = "-")
	protected void setSolicitudService(SolicitudLocalService solicitudLocalService) {
	   _solicitudLocalService = solicitudLocalService;
	}// fin setSolicitudService
	
	/**
	 * Obtiene el ThemeDisplay
	 * @param request
	 */
	private ThemeDisplay getUserId(ActionRequest request){
		ThemeDisplay themeDisplayLocal = new ThemeDisplay();
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			themeDisplayLocal = themeDisplay;
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return themeDisplayLocal;
	}// fin de getUserId
	
	/**
	 * Obtiene el contexto
	 * @param themeDisplay
	 */
	private ServiceContext getContext(ThemeDisplay themeDisplay){
		ServiceContext serviceContextLocal = new ServiceContext();
		try {
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
			serviceContextLocal = serviceContext;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return serviceContextLocal;
	}
}