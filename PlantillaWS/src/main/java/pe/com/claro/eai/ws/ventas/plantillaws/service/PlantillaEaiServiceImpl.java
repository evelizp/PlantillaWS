package pe.com.claro.eai.ws.ventas.plantillaws.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.claro.eai.ws.baseschema.AuditResponseType;
import pe.com.claro.eai.ws.ventas.plantillaws.dao.BscsDao;
import pe.com.claro.eai.ws.ventas.plantillaws.exception.DBException;
import pe.com.claro.eai.ws.ventas.plantillaws.exception.WSException;
import pe.com.claro.eai.ws.ventas.plantillaws.proxy.DatosCliente;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarRequest;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarResponse;
import pe.com.claro.eai.ws.ventas.plantillaws.util.JAXBUtilitarios;
import pe.com.claro.eai.ws.ventas.plantillaws.util.PropertiesExterno;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;

@Service
public class PlantillaEaiServiceImpl implements PlantillaEaiService {

	private final Logger logger = Logger.getLogger(PlantillaEaiServiceImpl.class);

	@Autowired
	private DatosCliente datosCliente;

	@Autowired
	private BscsDao bscsDao;

	@Autowired
	private PropertiesExterno propertiesExterno;

	@Override
	public ConsultarResponse consultar(ConsultarRequest request) {

		String mensajeTransaccion = "[idTx=" + request.getAuditRequest().getIdTransaccion() + "][consultar] ";

		logger.info(mensajeTransaccion + "-----INICIO-----");

		logger.info(
				mensajeTransaccion + "Datos de entrada del Servicio: " + JAXBUtilitarios.anyObjectToXmlText(request));

		logger.info(mensajeTransaccion + "Consulta WS");

		try {
			DatosClientexMSISDNResponse responseDatosCliente = datosCliente.consultarTipoCliente(mensajeTransaccion,
					request.getAuditRequest().getIpAplicacion(), request.getAuditRequest().getIdTransaccion(),
					request.getMsisdn());

		} catch (WSException e) {
			logger.error(mensajeTransaccion + "Error al consultar servicio", e);
			logger.error(mensajeTransaccion + "Codigo:  " + e.getCodError());
			logger.error(mensajeTransaccion + "Mensaje:  " + e.getMsjError());
		}

		logger.info(mensajeTransaccion + "Consulta a base de datos");

		try {

			bscsDao.consultarNroContrato(mensajeTransaccion, request.getMsisdn());

		} catch (DBException e) {
			logger.error(mensajeTransaccion + "Error al consultar base de datos", e);
			logger.error(mensajeTransaccion + "Codigo:  " + e.getCodError());
			logger.error(mensajeTransaccion + "Mensaje:  " + e.getMsjError());
		}

		ConsultarResponse consultarResponse = new ConsultarResponse();
		AuditResponseType auditResponseType = new AuditResponseType();
		auditResponseType.setCodigoRespuesta(propertiesExterno.consultarCodigoRespuestaIdf0);
		auditResponseType.setIdTransaccion(request.getAuditRequest().getIdTransaccion());
		auditResponseType.setMensajeRespuesta(propertiesExterno.consultarMensajeRespuestaIdf0);

		consultarResponse.setAuditResponse(auditResponseType);

		logger.info(mensajeTransaccion + "Datos de salida del Servicio: "
				+ JAXBUtilitarios.anyObjectToXmlText(consultarResponse));

		return consultarResponse;
	}

}

