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
 * @author liferay
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
	private static final Log log = LogFactoryUtil.getLog(EmailPortlet.class);
	public void sendMail( ActionRequest request,  ActionResponse response){
		try {
			log.info("Recuperacion de datos de formulario");
			String to = "bhernandez@consistent.com.mx";
			String from = (!ParamUtil.getString(request, "Correo").isEmpty())?ParamUtil.getString(request, "Correo"):"test@liferay.com";
			String nombre = (!ParamUtil.getString(request, "Nombre").isEmpty())?ParamUtil.getString(request, "Nombre"):"Sin nombre";
			String subject = "Solicitud de producto";
			String body = (!ParamUtil.getString(request, "Comentario").isEmpty())?ParamUtil.getString(request, "Comentario"):"test";
			String producto = (!ParamUtil.getString(request, "producto").isEmpty())?ParamUtil.getString(request, "producto"):"Sin producto";
			
			InternetAddress fromAddress = new InternetAddress(from);
			InternetAddress toAddress = new InternetAddress(to);
			
			MailMessage mailMessage = new  MailMessage();
			mailMessage.setFrom(fromAddress);
			mailMessage.setTo(toAddress);
			mailMessage.setSubject(subject);
			mailMessage.setHTMLFormat(true);
			mailMessage.setBody(""
					+ "<h4>Mi nombre es: "+nombre+"<h4>"
					+ "<h5>Mi correo es: "+from+"<h5>"
					+ "<h5>Mi producto es: "+producto+"<h5>"
					+ "<p>Mis comentarios son: "+body+"<p>");
			
			MailServiceUtil.sendEmail(mailMessage);
			System.out.println("Mensaje enviado");
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}