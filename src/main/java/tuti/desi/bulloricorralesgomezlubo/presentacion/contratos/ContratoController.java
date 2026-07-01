package tuti.desi.bulloricorralesgomezlubo.presentacion.contratos;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.bulloricorralesgomezlubo.accesoDatos.IPersonaRepo;
import tuti.desi.bulloricorralesgomezlubo.entidades.Contrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.EstadoContrato;
import tuti.desi.bulloricorralesgomezlubo.entidades.Persona;
import tuti.desi.bulloricorralesgomezlubo.entidades.Propiedad;
import tuti.desi.bulloricorralesgomezlubo.excepciones.Excepcion;
import tuti.desi.bulloricorralesgomezlubo.servicios.ContratoService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PropiedadContratoService;
import tuti.desi.bulloricorralesgomezlubo.servicios.PropiedadService;

import java.time.LocalDate;

@Controller
@RequestMapping("/contratos")
public class ContratoController {
    private final ContratoService contratoService;
    private final PropiedadService propiedadService;
    private final PropiedadContratoService propiedadContratoService;
    private final IPersonaRepo personaRepo;

    public ContratoController(ContratoService contratoService, PropiedadService propiedadService, PropiedadContratoService propiedadContratoService, IPersonaRepo personaRepo) {
        this.contratoService = contratoService;
        this.propiedadService = propiedadService;
        this.propiedadContratoService = propiedadContratoService;
        this.personaRepo = personaRepo;
    }

    private void cargarListas(Model model) {
        model.addAttribute("propiedades", propiedadService.filter(null, null, null, null));
        model.addAttribute("estados", EstadoContrato.values());
        model.addAttribute("inquilinos", personaRepo.findByEliminadaFalse());
    }

    @GetMapping
    public String obtenerTodas(
            @RequestParam(required = false) Long propiedad,
            @RequestParam(required = false) Long inquilino,
            @RequestParam(required = false) EstadoContrato estadoContrato,
            @RequestParam(required = false) LocalDate fechaDesde,
            @RequestParam(required = false) LocalDate fechaHasta,
            Model model
    ) {
        cargarListas(model);
        model.addAttribute("propiedadSelect", propiedad);
        model.addAttribute("inquilinoSelect", inquilino);
        model.addAttribute("estadoSelec", estadoContrato);
        model.addAttribute("fechaDesdeSelec", fechaDesde);
        model.addAttribute("fechaHastaSelec", fechaHasta);
        model.addAttribute("contratos", contratoService.obtenerContratos(propiedad, inquilino, estadoContrato, fechaDesde, fechaHasta));
        return "lista-contratos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        cargarListas(model);
        model.addAttribute("contrato", new Contrato());

        return "formulario-contrato";
    }

    @PostMapping("/guardar")
    public String guardarPropiedad(@Valid @ModelAttribute Contrato contrato, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                cargarListas(model);
                return "formulario-contrato";
            }

            if (contrato.getId() == null) { propiedadContratoService.guardarContrato(contrato);
            } else propiedadContratoService.actualizarContrato(contrato);

            redirectAttributes.addFlashAttribute("mensaje", "Accion exitosa");
            return "redirect:/contratos";
        } catch (Excepcion e) {
            cargarListas(model);
            model.addAttribute("contrato", contrato);
            model.addAttribute("errorGlobal", e.getMessage());
            return "formulario-contrato";
        }
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Contrato contrato = contratoService.obtenerContrato(id);

            cargarListas(model);
            model.addAttribute("contrato", contrato);

            return "formulario-contrato";
        } catch (Excepcion e) {
            redirectAttributes.addFlashAttribute("errorGlobal", e.getMessage());
            return "redirect:/contratos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            contratoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Publicacion eliminada con exito");
            return "redirect:/contratos";
        } catch (Excepcion e) {
            redirectAttributes.addFlashAttribute("errorGlobal", e.getMessage());
            return "redirect:/contratos";
        }
    }
}
