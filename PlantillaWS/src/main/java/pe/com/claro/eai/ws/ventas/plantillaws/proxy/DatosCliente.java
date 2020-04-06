package pe.com.claro.eai.ws.ventas.plantillaws.proxy;

import pe.com.claro.eai.ws.ventas.plantillaws.exception.WSException;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;

public interface DatosCliente {

	DatosClientexMSISDNResponse consultarTipoCliente(String mensajeTransaccion, String pIpAplicacion,
			String pIdTransaccion, String pMsisdn) throws WSException;

}

