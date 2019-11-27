<%@page import="Email.constants.Contants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<portlet:actionURL var="sendMailURL" name="sendMail" />
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<style>
.inp{
	width: 70% !important;
	border-radius: inherit;
	box-shadow: none !important;
	border: 0;
	outline: 0;
	background: transparent;
	border-bottom: 1px solid black;
	display: block;
}
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

form > div[class=correoMultiva] > button{
	padding: 1rem;width: 14rem;display: block;margin-left: auto;
}
form > div[class=correoMultiva]{
	width: 70%;
}
.labelm{
	color:#242424 !important;
	font-weight: 400 !important;
}
.btn-correo-multiva{
	padding: 1rem;
    width: 14rem;
    display: block;
    float: right;
    background-color: #337ab7;
    font-weight: 500;
    color: white;
    border: none;
    margin-right: 29%;
    margin-top: 2rem;
}


@media only screen and (max-width: 991px) {
  .inp{
	width: 100% !important;
	}
	form > div[class=correoMultiva]{
	width: 100%;
	}
	.btn-correo-multiva{
		margin-right: 0%;
	}
}
</style>

<div class="container">
	<div class="row">
		<div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
		<img class="img-responsive img-circle" style="height: 60rem;padding: 90px 0;margin: auto;" alt="" src='<%=request.getContextPath() +"/img/call-center-multiva2.jpg"%>'>
		</div>
		<div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6">
		<h3 class="subtitulo" style="margin-bottom: 4rem;">Contacto</h3>
		<%-- <aui:form action="${sendMailURL}" method="POST">
			<div class="contenedor">
				<label class="labelm">Nombre:</label>
				<aui:input class="ancho" name="<%=Contants.NOMBRE %>" type="text" />
			</div>
			<div class="contenedor">
				<label class="labelm">Teléfono:</label>
				<aui:input class="ancho" name="<%=Contants.TELEFONO %>" type="number"  />
			</div>
			<div class="contenedor">
				<label class="labelm">Correo:</label>
				<aui:input class="ancho" name="<%=Contants.CORREO %>" type="email"  />
			</div>
			<div class="contenedor">
				<label class="labelm">Comentario:</label>
				<aui:input class="ancho" name="<%=Contants.COMENTARIOS %>" type="text"  />
			</div>
			<div class="contenedor">
				<label class="labelm">En que horario te podemos contactar:</label>
				<aui:input class="ancho" type="datetime-local" name="<%=Contants.HORARIO %>"  />
			</div>
			<div class="contenedor">
				<aui:input class="ancho" id="produc" name="<%=Contants.PRODUCTO %>" type="textarea"  style="visibility: hidden;"  />
			</div>
			
			<div class="correoMultiva">
				<aui:button  type="submit" onsubmit="return isValidForm()" value="Enviar" />
			</div>
		</aui:form> --%>

		<form action="${sendMailURL}" method="post">
		  <div class="form-group">
		    <label class="labelm" for="nombre">Nombre:</label>
		    <input name="<portlet:namespace/><%=Contants.NOMBRE%>" type="text" class="form-control inp" id="name">
		  </div>
		  <div class="form-group">
		    <label class="labelm" for="telefono">Teléfono:</label>
		    <input name="<portlet:namespace/><%=Contants.TELEFONO%>" type="tel" class="form-control inp" id="phone">
		  </div>
		  <div class="form-group">
		    <label class="labelm" for="correo">Correo:</label>
		    <input name="<portlet:namespace/><%=Contants.CORREO%>" type="email" class="form-control inp" id="email">
		  </div>
		  <div class="form-group">
		    <label class="labelm" for="comentarios">Comentarios:</label>
		    <input name="<portlet:namespace/><%=Contants.COMENTARIOS%>" type="text" class="form-control inp" id="comentarios">
		  </div>
		  <div class="form-group">
		    <label class="labelm" for="comentarios">En que horario te podemos contactar::</label>
		    <input name="<portlet:namespace/><%=Contants.HORARIO%>" type="datetime-local" class="form-control inp" id="hora">
		  </div>
		  <input id="produc" name="<portlet:namespace/><%=Contants.PRODUCTO%>" style="visibility: hidden;">
		  	<div id="captcha-multiva">
				<div class="g-recaptcha" data-sitekey="6LfA98QUAAAAAI14dXQBPy1BMNYKZVn8NaCzvMOG"></div>
				<small style="color:red;" id="banner-error"></small>
			</div>
		  <button type="submit" class="btn btn-correo-multiva">Enviar</button>
		</form>
		</div>
	</div>
</div>

<script>
var valid = document.getElementById('banner-error');
$('form').on('submit', function(e) {
	  if(grecaptcha.getResponse() == "") {
		  e.preventDefault();
	    	valid.innerHTML = "No puedes enviar el formulario hasta que valides el captcha";
	  } else {
	    console.log("Si paso el captcha");
	  }
	});
</script>


<aui:script>
var prod = document.getElementById("producto").innerText;
document.getElementById('produc').value = prod;
AUI().use('node', function(A){
   A.one('#<portlet:namespace/>produc').set('value',prod); 
 });
</aui:script>
