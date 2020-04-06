package pe.com.claro.eai.ws.ventas.plantillaws.proxy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.claro.eai.ws.ventas.plantillaws.exception.WSException;
import pe.com.claro.eai.ws.ventas.plantillaws.util.Constantes;
import pe.com.claro.eai.ws.ventas.plantillaws.util.JAXBUtilitarios;
import pe.com.claro.eai.ws.ventas.plantillaws.util.PropertiesExterno;
import pe.com.claro.esb.services.datoscliente.ws.EbsDatosCliente;
import pe.com.claro.esb.services.schemas.datoscliente.AutenticacionType;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNRequest;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;

@Service
public class DatosClienteImpl implements DatosCliente {

	private static transient Logger logger = Logger.getLogger(DatosClienteImpl.class);

	@Autowired
	private EbsDatosCliente datosClienteWS;

	@Autowired
	private PropertiesExterno propertiesExterno;

	@Override
	public DatosClientexMSISDNResponse consultarTipoCliente(String mensajeTransaccion, String pIpAplicacion,
			String pIdTransaccion, String pMsisdn) throws WSException {

		String mensajeMetodo = mensajeTransaccion + "[consultarTipoCliente]";

		DatosClientexMSISDNResponse response = null;

		DatosClientexMSISDNRequest request = new DatosClientexMSISDNRequest();

		try {
			AutenticacionType autenticacion = new AutenticacionType();
			autenticacion.setIpCliente(pIpAplicacion);
			autenticacion.setUsuario(propertiesExterno.wsDatosClienteAutenticacionUsuario);
			autenticacion.setClave(propertiesExterno.wsDatosClienteAutenticacionClave);
			autenticacion.setCodigoServicioEAI(propertiesExterno.wsDatosClienteAutenticacionCodigoServicioEai);
			autenticacion.setCapacidadServicioEAI(propertiesExterno.wsDatosClienteAutenticacionCapacidadServicioEai);

			request.setAutenticacion(autenticacion);
			request.setAplicacionConsumidora(propertiesExterno.wsDatosClienteAplicacionConsumidora);
			request.setIdTransaccionConsumidora(pIdTransaccion);
			request.setMsisdn(pMsisdn);
			request.setHlrflag(Integer.parseInt(propertiesExterno.wsDatosClienteHlrflag));
			request.setCodigoExternalOrigen(propertiesExterno.wsDatosClienteCodigoExternalOrigen);
			request.setExternalOrigen(propertiesExterno.wsDatosClienteExternalOrigen);

			logger.info(mensajeMetodo + "Se invocara al Servicio: " + propertiesExterno.wsDatosClienteEndpointAddress);
			logger.info(mensajeMetodo + "Datos de entrada al Servicio: " + JAXBUtilitarios.anyObjectToXmlText(request));

			response = datosClienteWS.consultaClienteXMSISDN(request);

			logger.info(
					mensajeMetodo + "Datos de salida del Servicio: " + JAXBUtilitarios.anyObjectToXmlText(response));

		} catch (Exception e) {

			logger.error(mensajeMetodo + "ERROR: [Exception] - [" + e.getMessage() + "] ", e);

			String excepcion = e + Constantes.VACIO;

			String msjError = Constantes.VACIO;
			String codError = Constantes.VACIO;

			if (excepcion.contains(propertiesExterno.wsErrorTimeOut)) {

				codError = propertiesExterno.consultarCodigoRespuestaIdt1;
				msjError = String.format(propertiesExterno.consultarMensajeRespuestaIdt1,
						propertiesExterno.wsDatosClienteEndpointAddress) + Constantes.DOSPUNTOS + e.getMessage();

			} else {

				codError = propertiesExterno.consultarCodigoRespuestaIdt2;
				msjError = String.format(propertiesExterno.consultarMensajeRespuestaIdt2,
						propertiesExterno.wsDatosClienteEndpointAddress) + Constantes.DOSPUNTOS + e.getMessage();

			}

			throw new WSException(codError, msjError, e);
		}

		return response;

	}
}

