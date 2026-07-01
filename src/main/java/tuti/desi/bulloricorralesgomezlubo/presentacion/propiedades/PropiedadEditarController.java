package tuti.desi.bulloricorralesgomezlubo.presentacion.propiedades;

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
import tuti.desi.bulloricorralesgomezlubo.entidades.Ciudad;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoDisponibilidad;
import tuti.desi.bulloricorralesgomezlubo.entidades.Persona;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.entidades.TipoPropiedad;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;
import tuti.desi.bulloricorralesgomezlubo.servicios.CiudadService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PersonaService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PropiedadService;

@Controller
@RequestMapping("/propiedadEditar")
public class PropiedadEditarController {

	@Autowired
	private PropiedadService service;

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private PersonaService personaService;

	@RequestMapping(path = { "", "/{id}" }, method = RequestMethod.GET)
	public String preparaForm(Model modelo, @PathVariable("id") Optional<Long> id) {
		if (id.isPresent()) {
			Propiedad entity = service.getById(id.get());
			modelo.addAttribute("formBean", new PropiedadForm(entity));
		} else {
			modelo.addAttribute("formBean", new PropiedadForm());
		}

		return "propiedadEditar";
	}

	@ModelAttribute("allCiudades")
	public List<Ciudad> getAllCiudades() {
		return ciudadService.getAll();
	}

	@ModelAttribute("allPropietarios")
	public List<Persona> getAllPropietarios() {
		return personaService.getNoEliminadas();
	}

	@ModelAttribute("allTipos")
	public TipoPropiedad[] getAllTipos() {
		return TipoPropiedad.values();
	}

	@ModelAttribute("allEstados")
	public EstadoDisponibilidad[] getAllEstados() {
		return EstadoDisponibilidad.values();
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.POST)
	public String deleteById(Model modelo, @PathVariable("id") Long id) {
		try {
			service.deleteById(id);
		} catch (Excepcion e) {
			modelo.addAttribute("formBean", new PropiedadesBuscarForm());
			modelo.addAttribute("resultados", service.getNoEliminadas());
			modelo.addAttribute("error", e.getMessage());
			return "propiedadesBuscar";
		}

		return "redirect:/propiedadesBuscar";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(@ModelAttribute("formBean") @Valid PropiedadForm formBean, BindingResult result,
			ModelMap modelo, @RequestParam String action) {

		if (action.equals("Aceptar")) {
			if (result.hasErrors()) {
				modelo.addAttribute("formBean", formBean);
				return "propiedadEditar";
			}

			try {
				Propiedad propiedad = formBean.toPojo();
				propiedad.setCiudad(ciudadService.getById(formBean.getIdCiudad()));
				propiedad.setPropietario(personaService.getById(formBean.getIdPropietario()));
				service.save(propiedad);
				return "redirect:/propiedadesBuscar";
			} catch (Excepcion e) {
				if (e.getAtributo() == null) {
					result.addError(new ObjectError("globalError", e.getMessage()));
				} else {
					result.addError(new FieldError("formBean", e.getAtributo(), e.getMessage()));
				}
				modelo.addAttribute("formBean", formBean);
				return "propiedadEditar";
			}
		}

		if (action.equals("Cancelar")) {
			modelo.clear();
			return "redirect:/propiedadesBuscar";
		}

		return "redirect:/";
	}
}
