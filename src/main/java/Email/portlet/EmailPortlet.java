package Email.portlet;

import Email.constants.EmailPortletKeys;


import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bernardo Hernández Ramírez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.Multiva",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Multiva Correos",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + EmailPortletKeys.Email,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class EmailPortlet extends MVCPortlet {
	
	// Log permite un mayor detalle en la localizacion de los errores
	private static final Log log = LogFactoryUtil.getLog(EmailPortlet.class);
	
	/**
	 * @param request
	 * @param response
	 */
	public void sendMail( ActionRequest request,  ActionResponse response){
		try {
			log.info("Recuperacion de datos de formulario");
			String para = "bhernandez@consistent.com.mx";
			String de = (!ParamUtil.getString(request, "Correo").isEmpty())?ParamUtil.getString(request, "Correo"):"test@liferay.com";
			String nombre = (!ParamUtil.getString(request, "Nombre").isEmpty())?ParamUtil.getString(request, "Nombre"):"Sin nombre";
			String asunto = "Solicitud de producto";
			String contenido = (!ParamUtil.getString(request, "Comentario").isEmpty())?ParamUtil.getString(request, "Comentario"):"test";
			String producto = (!ParamUtil.getString(request, "producto").isEmpty())?ParamUtil.getString(request, "producto"):"Sin producto";
			mail(para, de, nombre, asunto, contenido, producto);
		}catch (NullPointerException e) {
			// TODO: handle exception
			
		}
	}// Fin de metodo sendMail
	
	/**
	 * @param para
	 * @param de
	 * @param nombre
	 * @param asunto
	 * @param contenido
	 * @param producto
	 */
	public void mail(String para, String de, String nombre, String asunto, String contenido, String producto){
		try{
			InternetAddress fromAddress = new InternetAddress(de);
			InternetAddress toAddress = new InternetAddress(para);
			
			MailMessage mailMessage = new  MailMessage();
			mailMessage.setFrom(fromAddress);
			mailMessage.setTo(toAddress);
			mailMessage.setSubject(asunto);
			mailMessage.setHTMLFormat(true);
			mailMessage.setBody(""
					+ "<h4>Mi nombre es: "+nombre+"<h4>"
					+ "<h5>Mi correo es: "+de+"<h5>"
					+ "<h5>Mi producto es: "+producto+"<h5>"
					+ "<p>Mis comentarios son: "+contenido+"<p>");
			
			MailServiceUtil.sendEmail(mailMessage);
			log.info("El mensaje se envio correctamente");
			
		}catch (AddressException e) {
			// TODO: handle exception
			log.error(e.getStackTrace());
			log.error(e.getCause());
			log.error(e.getLocalizedMessage());
			log.error(e.getMessage());
		}
	}
}