package tuti.desi.bulloricorralesgomezlubo.presentacion.propiedades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.TipoPropiedad;
import tuti.desi.bulloricorralesgomezlubo.servicios.CiudadService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PropiedadService;

@Controller
@RequestMapping("/propiedadesBuscar")
public class PropiedadesBuscarController {

	@Autowired
	private PropiedadService service;

	@Autowired
	private CiudadService ciudadService;

	@RequestMapping(method = RequestMethod.GET)
	public String preparaForm(Model modelo) {
		modelo.addAttribute("formBean", new PropiedadesBuscarForm());
		modelo.addAttribute("resultados", service.getNoEliminadas());
		return "propiedadesBuscar";
	}

	@ModelAttribute("allCiudades")
	public List<Ciudad> getAllCiudades() {
		return ciudadService.getAll();
	}

	@ModelAttribute("allTipos")
	public TipoPropiedad[] getAllTipos() {
		return TipoPropiedad.values();
	}

	@ModelAttribute("allEstados")
	public EstadoDisponibilidad[] getAllEstados() {
		return EstadoDisponibilidad.values();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(PropiedadesBuscarForm formBean, BindingResult result, ModelMap modelo,
			@RequestParam String action) {

		if (action.equals("Buscar")) {
			List<Propiedad> propiedades = service.filter(formBean.getDireccion(), formBean.getCiudadSeleccionada(),
					formBean.getTipo(), formBean.getEstadoDisponibilidad());

			modelo.addAttribute("formBean", formBean);
			modelo.addAttribute("resultados", propiedades);
			return "propiedadesBuscar";
		}

		if (action.equals("Cancelar")) {
			modelo.clear();
			return "redirect:/";
		}

		if (action.equals("Registrar")) {
			modelo.clear();
			return "redirect:/propiedadEditar";
		}

		return "redirect:/";
	}
}
