package pe.com.claro.eai.ws.ventas.plantillaws.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesExterno {

	@Value("${ws.datosCliente.endpointAddress}")
	public String wsDatosClienteEndpointAddress;

	@Value("${ws.datosCliente.max.request.timeout}")
	public String wsDatosClienteMaxRequestTimeout;

	@Value("${ws.datosCliente.max.connection.timeout}")
	public String wsDatosClienteMaxConnectionTimeout;

	@Value("${ws.datosCliente.autenticacion.usuario}")
	public String wsDatosClienteAutenticacionUsuario;

	@Value("${ws.datosCliente.autenticacion.clave}")
	public String wsDatosClienteAutenticacionClave;

	@Value("${ws.datosCliente.autenticacion.codigoServicioEAI}")
	public String wsDatosClienteAutenticacionCodigoServicioEai;

	@Value("${ws.datosCliente.autenticacion.capacidadServicioEAI}")
	public String wsDatosClienteAutenticacionCapacidadServicioEai;

	@Value("${ws.datosCliente.aplicacion.consumidora}")
	public String wsDatosClienteAplicacionConsumidora;

	@Value("${ws.datosCliente.hlrflag}")
	public String wsDatosClienteHlrflag;

	@Value("${ws.datosCliente.codigo.external.origen}")
	public String wsDatosClienteCodigoExternalOrigen;

	@Value("${ws.datosCliente.external.origen}")
	public String wsDatosClienteExternalOrigen;

	@Value("${ws.error.time.out}")
	public String wsErrorTimeOut;

	@Value("${db.bscs}")
	public String dbBscs;

	@Value("${db.bscs.owner}")
	public String dbBscsOwner;

	@Value("${db.bscs.jndi}")
	public String dbBscsJndi;

	@Value("${db.bscs.timeout.connection.max.time}")
	public String dbBscsTimeoutConnectionMaxTime;

	@Value("${db.bscs.timeout.execution.max.time}")
	public String dbBscsTimeoutExecutionMaxTime;

	@Value("${db.time.out}")
	public String dbTimeOut;

	@Value("${sp.consulta.nro.contrato}")
	public String spConsultaNroContrato;

	@Value("${consultar.codigo.respuesta.idf0}")
	public String consultarCodigoRespuestaIdf0;

	@Value("${consultar.mensaje.respuesta.idf0}")
	public String consultarMensajeRespuestaIdf0;

	@Value("${consultar.codigo.respuesta.idt1}")
	public String consultarCodigoRespuestaIdt1;

	@Value("${consultar.mensaje.respuesta.idt1}")
	public String consultarMensajeRespuestaIdt1;

	@Value("${consultar.codigo.respuesta.idt2}")
	public String consultarCodigoRespuestaIdt2;

	@Value("${consultar.mensaje.respuesta.idt2}")
	public String consultarMensajeRespuestaIdt2;
	
}

