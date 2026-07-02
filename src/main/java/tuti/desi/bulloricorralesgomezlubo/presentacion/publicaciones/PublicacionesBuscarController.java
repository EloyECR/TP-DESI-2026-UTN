package tuti.desi.bulloricorralesgomezlubo.presentacion.publicaciones;

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
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;
import tuti.desi.bulloricorralesgomezlubo.servicios.CiudadService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PropiedadService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PublicacionService;

@Controller
@RequestMapping("/publicacionesBuscar")
public class PublicacionesBuscarController {

	@Autowired
	private PublicacionService service;

	@Autowired
	private PropiedadService propiedadService;

	@Autowired
	private CiudadService ciudadService;

	@RequestMapping(method = RequestMethod.GET)
	public String preparaForm(Model modelo) {
		modelo.addAttribute("formBean", new PublicacionesBuscarForm());
		modelo.addAttribute("resultados", service.getNoEliminadas());
		return "publicacionesBuscar";
	}

	@ModelAttribute("allPropiedades")
	public List<Propiedad> getAllPropiedades() {
		return propiedadService.getNoEliminadas();
	}

	@ModelAttribute("allCiudades")
	public List<Ciudad> getAllCiudades() {
		return ciudadService.getAll();
	}

	@ModelAttribute("allEstados")
	public EstadoPublicacion[] getAllEstados() {
		return EstadoPublicacion.values();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(PublicacionesBuscarForm formBean, BindingResult result, ModelMap modelo,
			@RequestParam String action) {

		if (action.equals("Buscar")) {
			List<Publicacion> publicaciones = service.filter(formBean.getPropiedadId(), formBean.getCiudadId(),
					formBean.getEstado(), formBean.getPrecioMin(), formBean.getPrecioMax());

			modelo.addAttribute("formBean", formBean);
			modelo.addAttribute("resultados", publicaciones);
			return "publicacionesBuscar";
		}

		if (action.equals("Volver")) {
			modelo.clear();
			return "redirect:/";
		}

		if (action.equals("Limpiar")) {
			modelo.clear();
			return "redirect:/publicacionesBuscar";
		}

		if (action.equals("Registrar")) {
			modelo.clear();
			return "redirect:/publicacionEditar";
		}

		return "redirect:/";
	}
}
