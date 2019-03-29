package Email.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import Email.constants.Contants;
import Email.portlet.EmailPortlet;

public class Contacto {
	/**
	 * @author Bernardo Hernández Ramírez
	 */
	// Log permite un mayor detalle en la localizacion de los errores
	private static final Log log = LogFactoryUtil.getLog(Contacto.class);
	//Atributos
	private String remitente;
	private String destinatario;
	private String nombre;
	private String asunto;
	private String contenido;
	private String producto;
	private String hora;
	private String telefono;
	
	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public static Log getLog() {
		return log;
	}
	
	

	public Contacto(String remitente, String destinatario, String nombre, String asunto, String contenido, String producto,
			String hora, String telefono) {
		super();
		this.remitente = remitente;
		this.destinatario = destinatario;
		this.nombre = nombre;
		this.asunto = asunto;
		this.contenido = contenido;
		this.producto = producto;
		this.hora = hora;
		this.telefono = telefono;
	}
	
	public Contacto() {
		super();
		this.remitente = "";
		this.destinatario = "";
		this.nombre = "";
		this.asunto = "";
		this.contenido = "";
		this.producto = "";
		this.hora = "";
		this.telefono = "";
	}

	/**
	 * @param para Esta variable de tipo cadena define a quien se le va a enviar el correo
	 * @param de Obtiene el correo del usuario
	 * @param nombre Obtiene el nombre del usuario
	 * @param asunto Obtiene el asunto colocado por defecto
	 * @param contenido Obtiene la información de los comentarios
	 * @param producto Obtiene el nombre del producto
	 */
	public void enviarCorreo(){
		log.info("<-- mail -->");
		try{
			
			InternetAddress fromAddress = new InternetAddress(remitente);
			InternetAddress toAddress = new InternetAddress(destinatario);
			
			MailMessage mailMessage = new  MailMessage();
			mailMessage.setFrom(fromAddress);
			mailMessage.setTo(toAddress);
			mailMessage.setSubject(asunto);
			mailMessage.setHTMLFormat(true);
			mailMessage.setBody(""
					+ "<h5>Nombre del cliente: "+nombre+"<h5>"
					+ "<h5>Correo electronico: "+remitente+"<h5>"
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

}
