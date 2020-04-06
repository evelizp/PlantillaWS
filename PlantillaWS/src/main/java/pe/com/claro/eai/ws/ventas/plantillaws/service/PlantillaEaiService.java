package pe.com.claro.eai.ws.ventas.plantillaws.service;

import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarRequest;
import pe.com.claro.eai.ws.ventas.plantillaws.types.ConsultarResponse;

public interface PlantillaEaiService {
	public ConsultarResponse consultar(ConsultarRequest request);
}

