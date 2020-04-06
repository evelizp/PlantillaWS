package pe.com.claro.eai.ws.ventas.plantillaws.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.doNothing;
import pe.com.claro.eai.ws.baseschema.AuditRequestType;
import pe.com.claro.eai.ws.ventas.plantillaws.dao.BscsDao;
import pe.com.claro.eai.ws.ventas.plantillaws.exception.DBException;
import pe.com.claro.eai.ws.ventas.plantillaws.exception.WSException;
import pe.com.claro.eai.ws.ventas.plantillaws.proxy.DatosCliente;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarRequest;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarResponse;
import pe.com.claro.eai.ws.ventas.plantillaws.util.JAXBUtilitarios;
import pe.com.claro.eai.ws.ventas.plantillaws.util.PropertiesExterno;
import pe.com.claro.esb.services.schemas.datoscliente.DatosClientexMSISDNResponse;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PlantillaEaiServiceImplTest {

	@InjectMocks
	PlantillaEaiServiceImpl plantillaEaiServiceImpl;

	@Mock
	DatosCliente datosCliente;

	@Mock
	BscsDao bscsDao;

	@Mock
	PropertiesExterno propertiesExterno;

	@Before
	public void init() {
		propertiesExterno.consultarCodigoRespuestaIdf0 = "0";
		propertiesExterno.consultarMensajeRespuestaIdf0 = "OK";
	}

	@Test
	public void testConsultar() throws DBException, WSException {
		System.out.println("Inicio Método testConsultar");

		DatosClientexMSISDNResponse responseDatosCliente = new DatosClientexMSISDNResponse();
		responseDatosCliente.setResultado("0");
		responseDatosCliente.setMensaje("Transaccion OK");

		when(datosCliente.consultarTipoCliente("", "1.1.1.1", "1232132131", "987656780"))
				.thenReturn(responseDatosCliente);
		doNothing().when(bscsDao).consultarNroContrato("1232132131", "987656780");

		ConsultarRequest request = new ConsultarRequest();
		AuditRequestType auditRequestType = new AuditRequestType();

		auditRequestType.setIdTransaccion("1232132131");
		auditRequestType.setIpAplicacion("1.1.1.1");
		auditRequestType.setNombreAplicacion("EAI");
		auditRequestType.setUsuarioAplicacion("USREAI");

		request.setId("2");
		request.setAccion("1");
		request.setMsisdn("987656780");
		request.setAuditRequest(auditRequestType);

		ConsultarResponse response = plantillaEaiServiceImpl.consultar(request);

		System.out.println("Datos de salida del Servicio: " + JAXBUtilitarios.anyObjectToXmlText(response));

		Assert.assertNotNull(response);

		System.out.println("Fin Método testConsultar");

	}

}

