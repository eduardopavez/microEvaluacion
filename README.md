# 📚 Microservicio de Evaluaciones - Spring Boot + Oracle

Este proyecto implementa un microservicio RESTful en Java usando Spring Boot para gestionar **evaluaciones** y sus **preguntas**. El sistema permite crear, listar, actualizar y eliminar tanto evaluaciones como sus preguntas asociadas.

## ⚙️ Tecnologías utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Oracle DB
- Lombok
- Postman (para pruebas)

---

## 📁 Estructura del Proyecto

```
src/
├── controller/
│   └── PreguntaController.java
├── model/
│   ├── Pregunta.java
│   └── Evaluacion.java
├── repository/
│   └── PreguntaRepository.java
├── service/
│   └── PreguntaService.java
└── application.properties
```

---

## 🧠 Modelo: `Evaluacion`

```java
@Entity  // Marca esta clase como una entidad JPA.
@Table(name= "EVALUACION")  // Especifica el nombre de la tabla en la base de datos.
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor // Constructor, getters y setters generados automáticamente por Lombok
public class Evaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluacion_seq")
    @SequenceGenerator(name = "evaluacion_seq", sequenceName = "EVALUACION_SEQ", allocationSize = 1))
    private Integer id;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String nombreEvaluacion;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String descripcion;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private Integer duracion; // Duración en minutos

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("evaluacion")  // Para evitar loops recursivos
    @JsonIgnore  // Ignora esta propiedad al serializar a JSON
    private List<Pregunta> preguntas; // Relación uno a muchos con Pregunta




## ❓ Modelo: `Pregunta`

```java
@Entity  // Marca esta clase como una entidad JPA.
@Table(name= "PREGUNTA")  // Especifica el nombre de la tabla en la base de datos.
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor

public class Pregunta {
    @Id  // Identifica la columna primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pregunta_seq")
    @SequenceGenerator(name = "pregunta_seq", sequenceName = "PREGUNTA_SEQ", allocationSize = 1)  // Genera automáticamente el valor de la columna.
    private Integer id;  // Identificador único de la pregunta.
    
    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String pregunta;  // Texto de la pregunta.
   
    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String respuesta;  // Respuesta correcta a la pregunta.

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    private Evaluacion evaluacion;
    
}
```

---

## 🗃️ Repositorio: `PreguntaRepository`

```java
@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {
    List<Pregunta> findByPregunta(String pregunta);

    @Query("SELECT p FROM Pregunta p WHERE p.evaluacion.id = :evaluacionId")
    List<Pregunta> findByEvaluacionId(Integer evaluacionId);
}
```

> 💡 Usa una consulta personalizada para obtener preguntas por evaluación.

---

## 🧪 Servicio: `PreguntaService`

```java
@Service
@Transactional

public class PreguntaService {
    @Autowired
    private PreguntaRepository preguntaRepository; 

    // Obtener preguntas por Evaluacion ID
    public List<Pregunta> obtenerPorEvaluacionId(Integer evaluacionId) {
        return preguntaRepository.findByEvaluacionId(evaluacionId);
    }

    // Buscar pregunta por ID
    public Pregunta buscarPorId(Integer id) {
        return preguntaRepository.findById(id).orElse(null);
    }

    // Guardar nueva pregunta
    public Pregunta guardar(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    // Actualizar pregunta existente
    public Pregunta actualizar(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    // Obtener todas las preguntas
    public List<Pregunta> obtenerTodas() {
        return preguntaRepository.findAll();
    }

    // Eliminar pregunta por ID
    public void eliminar(Integer id) {
        preguntaRepository.deleteById(id);
    }
    
}
```

---

## 🌐 Controlador: `PreguntaController`

```java
@RestController
@RequestMapping("/api/v1/preguntas")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @PostMapping("/crearPregunta")
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nueva = preguntaService.guardar(pregunta);
        return ResponseEntity.status(201).body(nueva);
    }

    @GetMapping("/listarPreguntas")
    public ResponseEntity<List<Pregunta>> listarPreguntas() {
        List<Pregunta> lista = preguntaService.obtenerTodas();
        return lista.isEmpty() ? ResponseEntity.status(404).build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/obtenerPreguntaId/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Pregunta p = preguntaService.buscarPorId(id);
        if (p == null) return ResponseEntity.status(404).body("Pregunta no encontrada.");
        return ResponseEntity.ok(p);
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<Pregunta>> preguntasPorEvaluacion(@PathVariable Integer evaluacionId) {
        List<Pregunta> lista = preguntaService.obtenerPorEvaluacionId(evaluacionId);
        return lista.isEmpty() ? ResponseEntity.status(404).build() : ResponseEntity.ok(lista);
    }

    @PutMapping("/actualizarPregunta/{id}")
    public ResponseEntity<?> actualizarPregunta(@PathVariable Integer id, @RequestBody Pregunta pregunta) {
        Pregunta existente = preguntaService.buscarPorId(id);
        if (existente == null) return ResponseEntity.status(404).body("Pregunta no encontrada.");
        pregunta.setId(id);
        Pregunta actualizada = preguntaService.actualizar(pregunta);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminarPregunta/{id}")
    public ResponseEntity<?> eliminarPregunta(@PathVariable Integer id) {
        try {
            preguntaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Pregunta no encontrada.");
        }
    }
}
```

---

## 🔐 Configuración de conexión Oracle (application.properties)

```properties
spring.datasource.url=jdbc:oracle:thin:@edutechdb_high?TNS_ADMIN=E:/ruta/correcta/Wallet
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.username=ADMIN
spring.datasource.password=*** # No lo subas con contraseña 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.show-sql=true
```

> ⚠️ Asegúrate de que el path TNS_ADMIN tenga las comillas bien cerradas y el archivo `tnsnames.ora` exista. Para evitar errores como `ORA-12261` y `ORA-17866`.

---

## 🧪 Pruebas Sugeridas (Postman)

- `POST /api/v1/preguntas/crearPregunta` → Crear pregunta
- `GET /api/v1/preguntas/listarPreguntas` → Listar preguntas
- `GET /api/v1/preguntas/obtenerPreguntaId/{id}` → Buscar por ID
- `PUT /api/v1/preguntas/actualizarPregunta/{id}` → Editar pregunta
- `DELETE /api/v1/preguntas/eliminarPregunta/{id}` → Borrar pregunta

---
