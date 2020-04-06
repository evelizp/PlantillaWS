package pe.com.claro.eai.ws.ventas.plantillaws.dao;

import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import oracle.jdbc.OracleTypes;
import pe.com.claro.eai.ws.ventas.plantillaws.bean.ResponseBean;
import pe.com.claro.eai.ws.ventas.plantillaws.exception.DBException;
import pe.com.claro.eai.ws.ventas.plantillaws.util.Constantes;
import pe.com.claro.eai.ws.ventas.plantillaws.util.JAXBUtilitarios;
import pe.com.claro.eai.ws.ventas.plantillaws.util.PropertiesExterno;

@Repository
public class BscsDaoImpl implements BscsDao {

	private static transient Logger logger = Logger.getLogger(BscsDaoImpl.class);

	@Autowired
	private PropertiesExterno propertiesExterno;

	@Autowired
	@Qualifier("bscsdbDS")
	private DataSource bscsdbDS;

	@Override
	public void consultarNroContrato(String mensajeTransaccion, String msisdn) throws DBException {

		String mensajeMetodo = mensajeTransaccion + "[ consultarNroContrato ]";

		ResponseBean responseBean = new ResponseBean();

		try {

			logger.info(mensajeMetodo + "Consultando BD " + propertiesExterno.dbBscs + ", con JNDI = ["
					+ propertiesExterno.dbBscsJndi + "]");

			bscsdbDS.setLoginTimeout(Integer.parseInt(propertiesExterno.dbBscsTimeoutConnectionMaxTime));

			SimpleJdbcCall procConsulta1 = new SimpleJdbcCall(bscsdbDS).withoutProcedureColumnMetaDataAccess()
					.withSchemaName(propertiesExterno.dbBscsOwner)
					.withProcedureName(propertiesExterno.spConsultaNroContrato)
					.declareParameters(new SqlParameter("p_dn_num", OracleTypes.VARCHAR),
							new SqlOutParameter("p_result", OracleTypes.VARCHAR),
							new SqlOutParameter("p_result_tipo", OracleTypes.VARCHAR));

			logger.info(mensajeMetodo + "Se invocara el SP: " + propertiesExterno.dbBscsOwner + "."
					+ propertiesExterno.spConsultaNroContrato);
			logger.info(mensajeMetodo + "PARAMETROS [INPUT]: ");
			logger.info(mensajeMetodo + "[p_dn_num] = " + msisdn);

			SqlParameterSource objParametrosIN = new MapSqlParameterSource().addValue("p_dn_num", msisdn);

			procConsulta1.getJdbcTemplate()
					.setQueryTimeout(Integer.parseInt(propertiesExterno.dbBscsTimeoutExecutionMaxTime));

			Map<String, Object> resultMap = procConsulta1.execute(objParametrosIN);

			logger.info(mensajeMetodo + "Se invoco con exito el SP: " + propertiesExterno.dbBscsOwner + "."
					+ propertiesExterno.spConsultaNroContrato);

			responseBean.setCoId(resultMap.get("p_result").toString());
			responseBean.setSnCode(resultMap.get("p_result_tipo").toString());

			logger.info(mensajeMetodo + "PARAMETROS [OUPUT]: ");
			logger.info(mensajeMetodo + JAXBUtilitarios.anyObjectToXmlText(responseBean));

		} catch (Exception e) {
			logger.error(mensajeTransaccion + "ERROR: [Exception] - [" + e.getMessage() + "] ", e);

			String excepcion = e + Constantes.VACIO;

			String msjError = Constantes.VACIO;
			String codError = Constantes.VACIO;

			if (excepcion.contains(propertiesExterno.dbTimeOut)) {

				codError = propertiesExterno.consultarCodigoRespuestaIdt1;
				msjError = String.format(propertiesExterno.consultarMensajeRespuestaIdt1,
						propertiesExterno.wsDatosClienteEndpointAddress) + Constantes.DOSPUNTOS + e.getMessage();

			} else {

				codError = propertiesExterno.consultarCodigoRespuestaIdt2;
				msjError = String.format(propertiesExterno.consultarMensajeRespuestaIdt2,
						propertiesExterno.wsDatosClienteEndpointAddress) + Constantes.DOSPUNTOS + e.getMessage();

			}

			throw new DBException(codError, msjError, e);
		}
	}
}

