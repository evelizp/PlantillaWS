package pe.com.claro.eai.ws.ventas.plantillaws;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import pe.com.claro.eai.ws.ventas.plantillaws.service.PlantillaEaiService;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarRequest;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarResponse;


@WebService(portName = "ebsPlantillaSB12", serviceName = "PlantillaWSService", targetNamespace = "http://claro.com.pe/eai/ws/ventas/plantillaws", wsdlLocation = "/WEB-INF/wsdl/PlantillaWS.wsdl", endpointInterface = "pe.com.claro.eai.ws.ventas.plantillaws.PlantillaWSPortType")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class PlantillaSB12 implements PlantillaWSPortType {

	@Autowired
	private PlantillaEaiService plantillaEaiService;

	@PostConstruct
	public void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public ConsultarResponse consultar(ConsultarRequest request) {
		return plantillaEaiService.consultar(request);
	}

}
