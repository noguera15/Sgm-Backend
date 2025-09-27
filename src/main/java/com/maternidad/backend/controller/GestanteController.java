package com.maternidad.backend.controller;

import com.maternidad.backend.entity.*;
import com.maternidad.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/gestantes")
@CrossOrigin(origins = "*")
public class GestanteController {

    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private EmbarazoRepository embarazoRepository;
    
    @Autowired
    private AntecedentesRepository antecedentesRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    
    @Autowired
    private EapbRepository eapbRepository;
    
    @Autowired
    private RegimenRepository regimenRepository;
    
    @Autowired
    private MunicipioRepository municipioRepository;
    
    @Autowired
    private IpsRepository ipsRepository;
    
    @Autowired
    private ControlRepository controlRepository;

    // Obtener datos de referencia para el formulario
    @GetMapping("/referencias")
    public ResponseEntity<Map<String, Object>> getReferencias() {
        try {
            Map<String, Object> referencias = new HashMap<>();
            
            referencias.put("tiposDocumento", tipoDocumentoRepository.findAll());
            referencias.put("eapbs", eapbRepository.findAll());
            referencias.put("regimenes", regimenRepository.findAll());
            referencias.put("municipios", municipioRepository.findAll());
            referencias.put("ips", ipsRepository.findAll());
            
            return ResponseEntity.ok(referencias);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al obtener referencias: " + e.getMessage()));
        }
    }

    // Registrar nueva gestante
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarGestante(@RequestBody Map<String, Object> data) {
        try {
            // Log para debug
            System.out.println("Datos recibidos: " + data);
            
            // Validar campos obligatorios
            if (data.get("documento") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "El documento es obligatorio"));
            }
            if (data.get("primerNombre") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "El primer nombre es obligatorio"));
            }
            if (data.get("primerApellido") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "El primer apellido es obligatorio"));
            }
            
            // 1. Crear o actualizar paciente
            Paciente paciente = crearPaciente(data);
            paciente = pacienteRepository.save(paciente);
            
            // 2. Crear embarazo
            Embarazo embarazo = crearEmbarazo(data, paciente);
            embarazo = embarazoRepository.save(embarazo);
            
            // 3. Crear antecedentes (opcional)
            if (data.containsKey("antecedentes")) {
                Antecedentes antecedentes = crearAntecedentes(data, embarazo);
                antecedentesRepository.save(antecedentes);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Gestante registrada exitosamente");
            response.put("pacienteId", paciente.getDocumento());
            response.put("embarazoId", embarazo.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace(); // Para ver el stack trace completo
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al registrar gestante: " + e.getMessage()));
        }
    }

    private Paciente crearPaciente(Map<String, Object> data) {
        // Verificar si el paciente ya existe
        Integer documento = Integer.parseInt(data.get("documento").toString());
        Optional<Paciente> pacienteExistente = pacienteRepository.findById(documento);
        
        if (pacienteExistente.isPresent()) {
            // Actualizar paciente existente
            Paciente paciente = pacienteExistente.get();
            actualizarDatosPaciente(paciente, data);
            return paciente;
        } else {
            // Crear nuevo paciente
            return Paciente.builder()
                .documento(documento)
                .tipoDocumento(data.get("tipoDocumentoId") != null ? 
                    tipoDocumentoRepository.findById((Integer) data.get("tipoDocumentoId")).orElse(null) : null)
                .primerNombre(data.get("primerNombre") != null ? data.get("primerNombre").toString() : "")
                .segundoNombre(data.get("segundoNombre") != null ? data.get("segundoNombre").toString() : null)
                .primerApellido(data.get("primerApellido") != null ? data.get("primerApellido").toString() : "")
                .segundoApellido(data.get("segundoApellido") != null ? data.get("segundoApellido").toString() : null)
                .fechaNacimiento(data.get("fechaNacimiento") != null ? 
                    LocalDate.parse(data.get("fechaNacimiento").toString()) : null)
                .edad(data.get("edad") != null ? (Integer) data.get("edad") : 0)
                .eapb(data.get("eapbId") != null ? 
                    eapbRepository.findById((Integer) data.get("eapbId")).orElse(null) : null)
                .regimen(data.get("regimenId") != null ? 
                    regimenRepository.findById((Integer) data.get("regimenId")).orElse(null) : null)
                .servicioSaludActivo(data.get("servicioSaludActivo") != null ? 
                    (Boolean) data.get("servicioSaludActivo") : true)
                .telefonoPersonal(data.get("telefonoPersonal") != null ? 
                    Long.parseLong(data.get("telefonoPersonal").toString()) : null)
                .telefonoFamiliar(data.get("telefonoFamiliar") != null ? 
                    Long.parseLong(data.get("telefonoFamiliar").toString()) : null)
                .direccionResidencia(data.get("direccionResidencia") != null ? 
                    data.get("direccionResidencia").toString() : "")
                .municipio(data.get("municipioId") != null ? 
                    municipioRepository.findById((Integer) data.get("municipioId")).orElse(null) : null)
                .build();
        }
    }

    private void actualizarDatosPaciente(Paciente paciente, Map<String, Object> data) {
        // Actualizar solo los campos que pueden cambiar
        if (data.get("telefonoPersonal") != null) {
            paciente.setTelefonoPersonal(Long.parseLong(data.get("telefonoPersonal").toString()));
        }
        if (data.get("telefonoFamiliar") != null) {
            paciente.setTelefonoFamiliar(Long.parseLong(data.get("telefonoFamiliar").toString()));
        }
        if (data.get("direccionResidencia") != null) {
            paciente.setDireccionResidencia(data.get("direccionResidencia").toString());
        }
        if (data.get("municipioId") != null) {
            paciente.setMunicipio(municipioRepository.findById((Integer) data.get("municipioId")).orElse(null));
        }
    }

    private Embarazo crearEmbarazo(Map<String, Object> data, Paciente paciente) {
        return Embarazo.builder()
            .paciente(paciente)
            .fechaUltimaMenstruacion(data.get("fechaUltimaMenstruacion") != null ? 
                LocalDate.parse(data.get("fechaUltimaMenstruacion").toString()) : null)
            .primerEmbarazo(data.get("primerEmbarazo") != null ? 
                (Boolean) data.get("primerEmbarazo") : true)
            .fechaUltimoParto(data.get("fechaUltimoParto") != null ? 
                LocalDate.parse(data.get("fechaUltimoParto").toString()) : null)
            .fechaProbableParto(data.get("fechaProbableParto") != null ? 
                LocalDate.parse(data.get("fechaProbableParto").toString()) : null)
            .ips(data.get("ipsId") != null ? 
                ipsRepository.findById((Integer) data.get("ipsId")).orElse(null) : null)
            .build();
    }

    @SuppressWarnings("unchecked")
    private Antecedentes crearAntecedentes(Map<String, Object> data, Embarazo embarazo) {
        Map<String, Object> antecedentesData = (Map<String, Object>) data.get("antecedentes");
        
        return Antecedentes.builder()
            .embarazo(embarazo)
            .gravida(antecedentesData.get("gravida") != null ? (Integer) antecedentesData.get("gravida") : null)
            .partos(antecedentesData.get("partos") != null ? (Integer) antecedentesData.get("partos") : null)
            .abortos(antecedentesData.get("abortos") != null ? (Integer) antecedentesData.get("abortos") : null)
            .cesareas(antecedentesData.get("cesareas") != null ? (Integer) antecedentesData.get("cesareas") : null)
            .build();
    }

    // Obtener lista de gestantes
    @GetMapping
    public ResponseEntity<List<Paciente>> getGestantes() {
        try {
            List<Paciente> gestantes = pacienteRepository.findAll();
            return ResponseEntity.ok(gestantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Obtener gestante por documento con información completa
    @GetMapping("/{documento}")
    public ResponseEntity<?> getGestanteByDocumento(@PathVariable Integer documento) {
        try {
            Optional<Paciente> pacienteOpt = pacienteRepository.findById(documento);
            if (pacienteOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Paciente paciente = pacienteOpt.get();
            
            // Obtener embarazo activo
            List<Embarazo> embarazos = embarazoRepository.findByPacienteDocumento(documento);
            Embarazo embarazoActivo = embarazos.isEmpty() ? null : embarazos.get(0);
            
            // Obtener antecedentes del embarazo activo
            Antecedentes antecedentes = null;
            if (embarazoActivo != null) {
                antecedentes = antecedentesRepository.findByEmbarazoId(embarazoActivo.getId()).orElse(null);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("paciente", paciente);
            response.put("embarazo", embarazoActivo);
            response.put("antecedentes", antecedentes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al obtener gestante: " + e.getMessage()));
        }
    }

    // Actualizar datos de gestante
    @PutMapping("/{documento}")
    public ResponseEntity<?> actualizarGestante(@PathVariable Integer documento, @RequestBody Map<String, Object> data) {
        try {
            Optional<Paciente> pacienteOpt = pacienteRepository.findById(documento);
            if (pacienteOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Paciente paciente = pacienteOpt.get();
            actualizarDatosPaciente(paciente, data);
            paciente = pacienteRepository.save(paciente);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Gestante actualizada exitosamente",
                "paciente", paciente
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al actualizar gestante: " + e.getMessage()));
        }
    }

    // Agregar control prenatal
    @PostMapping("/{documento}/controles")
    public ResponseEntity<?> agregarControl(@PathVariable Integer documento, @RequestBody Map<String, Object> controlData) {
        try {
            // Buscar embarazo activo
            List<Embarazo> embarazos = embarazoRepository.findByPacienteDocumento(documento);
            if (embarazos.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "No se encontró embarazo activo para esta paciente"));
            }

            Embarazo embarazo = embarazos.get(0);
            
            // Crear nuevo control
            Control control = Control.builder()
                .embarazo(embarazo)
                .numeroControl((Integer) controlData.get("numeroControl"))
                .semanasGestacion((Integer) controlData.get("semanasGestacion"))
                .tensionSistolica(controlData.get("tensionSistolica") != null ? (Integer) controlData.get("tensionSistolica") : null)
                .tensionDiastolica(controlData.get("tensionDiastolica") != null ? (Integer) controlData.get("tensionDiastolica") : null)
                .peso(controlData.get("peso") != null ? new java.math.BigDecimal(controlData.get("peso").toString()) : null)
                .talla(controlData.get("talla") != null ? new java.math.BigDecimal(controlData.get("talla").toString()) : null)
                .alturaUterina(controlData.get("alturaUterina") != null ? new java.math.BigDecimal(controlData.get("alturaUterina").toString()) : null)
                .fechaUltimoControl(controlData.get("fechaUltimoControl") != null ? LocalDate.parse(controlData.get("fechaUltimoControl").toString()) : null)
                .fechaProximoControl(controlData.get("fechaProximoControl") != null ? LocalDate.parse(controlData.get("fechaProximoControl").toString()) : null)
                .build();

            Control controlGuardado = controlRepository.save(control);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Control prenatal agregado exitosamente",
                "control", control
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al agregar control: " + e.getMessage()));
        }
    }

    // Obtener controles de una gestante
    @GetMapping("/{documento}/controles")
    public ResponseEntity<?> getControles(@PathVariable Integer documento) {
        try {
            List<Embarazo> embarazos = embarazoRepository.findByPacienteDocumento(documento);
            if (embarazos.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "No se encontró embarazo activo"));
            }

            Embarazo embarazo = embarazos.get(0);
            
            List<Control> controles = controlRepository.findByEmbarazoIdOrderByNumeroControlAsc(embarazo.getId());
            
            return ResponseEntity.ok(controles);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al obtener controles: " + e.getMessage()));
        }
    }

    // Obtener gestantes con información básica (para listado)
    @GetMapping("/lista")
    public ResponseEntity<?> getListaGestantes() {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            
            List<Map<String, Object>> gestantes = pacientes.stream()
                .map(paciente -> {
                    Map<String, Object> gestanteInfo = new HashMap<>();
                    gestanteInfo.put("documento", paciente.getDocumento());
                    gestanteInfo.put("nombre", paciente.getPrimerNombre() + " " + paciente.getPrimerApellido());
                    gestanteInfo.put("edad", paciente.getEdad());
                    gestanteInfo.put("telefono", paciente.getTelefonoPersonal());
                    gestanteInfo.put("eps", paciente.getEapb() != null ? paciente.getEapb().getNombre() : null);
                    
                    // Obtener información del embarazo
                    List<Embarazo> embarazos = embarazoRepository.findByPacienteDocumento(paciente.getDocumento());
                    if (!embarazos.isEmpty()) {
                        Embarazo embarazo = embarazos.get(0);
                        gestanteInfo.put("fechaProbableParto", embarazo.getFechaProbableParto());
                        gestanteInfo.put("primerEmbarazo", embarazo.getPrimerEmbarazo());
                    }
                    
                    return gestanteInfo;
                })
                .toList();

            return ResponseEntity.ok(gestantes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al obtener lista de gestantes: " + e.getMessage()));
        }
    }

    // Buscar gestantes por criterios
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarGestantes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer documento,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax) {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            
            List<Paciente> resultados = pacientes.stream()
                .filter(paciente -> {
                    if (nombre != null && !nombre.isEmpty()) {
                        String nombreCompleto = (paciente.getPrimerNombre() + " " + paciente.getPrimerApellido()).toLowerCase();
                        if (!nombreCompleto.contains(nombre.toLowerCase())) {
                            return false;
                        }
                    }
                    
                    if (documento != null && !paciente.getDocumento().equals(documento)) {
                        return false;
                    }
                    
                    if (edadMin != null && paciente.getEdad() < edadMin) {
                        return false;
                    }
                    
                    if (edadMax != null && paciente.getEdad() > edadMax) {
                        return false;
                    }
                    
                    return true;
                })
                .toList();

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Error al buscar gestantes: " + e.getMessage()));
        }
    }

    // Endpoint de prueba para verificar datos
    @PostMapping("/test")
    public ResponseEntity<?> testRegistro(@RequestBody Map<String, Object> data) {
        System.out.println("=== DATOS RECIBIDOS ===");
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue() + " (tipo: " + 
                (entry.getValue() != null ? entry.getValue().getClass().getSimpleName() : "null") + ")");
        }
        
        return ResponseEntity.ok(Map.of(
            "message", "Datos recibidos correctamente",
            "data", data
        ));
    }

}
