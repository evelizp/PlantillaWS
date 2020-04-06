package pe.com.claro.eai.ws.ventas.plantillaws.dao;

import pe.com.claro.eai.ws.ventas.plantillaws.exception.DBException;

public interface BscsDao {

	void consultarNroContrato(String mensajeTransaccion, String msisdn) throws DBException;

}

