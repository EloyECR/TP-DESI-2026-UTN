package tuti.desi.bulloricorralesgomezlubo.presentacion.publicaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoPublicacion;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Publicacion;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;
import tuti.desi.bulloricorralesgomezlubo.servicios.PropiedadService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PublicacionService;

@Controller
@RequestMapping("/publicacionEditar")
public class PublicacionEditarController {

	@Autowired
	private PublicacionService service;

	@Autowired
	private PropiedadService propiedadService;

	@RequestMapping(path = { "", "/{id}" }, method = RequestMethod.GET)
	public String preparaForm(Model modelo, @PathVariable("id") Optional<Long> id) {
		boolean edicion = id.isPresent();
		if (edicion) {
			Publicacion entity = service.getById(id.get());
			modelo.addAttribute("formBean", new PublicacionForm(entity));
		} else {
			modelo.addAttribute("formBean", new PublicacionForm());
		}

		modelo.addAttribute("edicion", edicion);
		modelo.addAttribute("allPropiedades", propiedadesSegunEdicion(edicion));
		return "publicacionEditar";
	}

	@ModelAttribute("allEstados")
	public EstadoPublicacion[] getAllEstados() {
		return EstadoPublicacion.values();
	}

	private List<Propiedad> propiedadesSegunEdicion(boolean edicion) {
		if (edicion) {
			return propiedadService.getNoEliminadas();
		}
		return propiedadService.filter(null, null, null, EstadoDisponibilidad.DISPONIBLE);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
	public String deleteById(Model modelo, @PathVariable("id") Long id) {
		try {
			service.deleteById(id);
		} catch (Excepcion e) {
			modelo.addAttribute("formBean", new PublicacionesBuscarForm());
			modelo.addAttribute("resultados", service.getNoEliminadas());
			modelo.addAttribute("error", e.getMessage());
			return "publicacionesBuscar";
		}

		return "redirect:/publicacionesBuscar";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(@ModelAttribute("formBean") @Valid PublicacionForm formBean, BindingResult result,
			ModelMap modelo, @RequestParam String action) {

		if (action.equals("Aceptar")) {
			if (result.hasErrors()) {
				modelo.addAttribute("formBean", formBean);
				modelo.addAttribute("edicion", formBean.getId() != null);
				modelo.addAttribute("allPropiedades", propiedadesSegunEdicion(formBean.getId() != null));
				return "publicacionEditar";
			}

			try {
				Publicacion publicacion = formBean.toPojo();
				publicacion.setPropiedad(propiedadService.getById(formBean.getIdPropiedad()));
				service.save(publicacion);
				return "redirect:/publicacionesBuscar";
			} catch (Excepcion e) {
				if (e.getAtributo() == null) {
					result.addError(new ObjectError("globalError", e.getMessage()));
				} else {
					result.addError(new FieldError("formBean", e.getAtributo(), e.getMessage()));
				}
				modelo.addAttribute("formBean", formBean);
				modelo.addAttribute("edicion", formBean.getId() != null);
				modelo.addAttribute("allPropiedades", propiedadesSegunEdicion(formBean.getId() != null));
				return "publicacionEditar";
			}
		}

		if (action.equals("Volver")) {
			modelo.clear();
			return "redirect:/";
		}

		return "redirect:/";
	}
}
