<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:actionURL var="sendMailURL" name="sendMail" />

<style>
form > div[class=contenedor] > div > label[class=control-label]{
	display: none;
}
form > div[class=contenedor] > div > input{
	width: 70% !important;
	border-radius: inherit;
	box-shadow: none !important;
	border: 0;
	outline: 0;
	background: transparent;
	border-bottom: 1px solid black;
}
form > div[class=contenedor] > div > textarea{
	width: 100%;
}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-6">
		<img class="img-responsive img-circle" style="height: 60rem;padding: 90px 0;margin: auto;" alt="" src='<%=request.getContextPath() +"/img/call-center-multiva2.jpg"%>'>
		</div>
		<div class="col-md-6">
		<h3 class="subtitulo" style="margin-bottom: 4rem;">Contacto</h3>
		<aui:form action="${sendMailURL}" method="POST">
			<div class="contenedor">
				<label class="labelm">Nombre:</label>
				<aui:input class="ancho" name="Nombre" type="text" />
			</div>
			<div class="contenedor">
				<label class="labelm">Telefono:</label>
				<aui:input class="ancho" name="Telefono" type="number"  />
			</div>
			<div class="contenedor">
				<label class="labelm">Correo:</label>
				<aui:input class="ancho" name="Correo" type="email"  />
			</div>
			<div class="contenedor">
				<label class="labelm">Comentario:</label>
				<aui:input class="ancho" name="Comentario" type="text"  />
			</div>
			<div class="contenedor">
				<label class="labelm">En que horario te podemos cantactar:</label>
				<aui:input class="ancho" type="datetime-local" name="bdaytime"  />
			</div>
			<div class="contenedor">
				<aui:input class="ancho" id="produc" name="producto" type="textarea"  style="visibility: hidden;"  />
			</div>
			<aui:button type="submit" onclick="myFunction()" value="Enviar" />
		</aui:form>
		</div>
	</div>
</div>

<aui:script>
var prod = document.getElementById("producto").innerText;
AUI().use('node', function(A){
   A.one('#<portlet:namespace/>produc').set('value',prod); 
 });
 function myFunction() {
  document.getElementById("myForm").reset();
}
</aui:script>
