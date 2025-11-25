# 🤝 Guide de contribution

Merci de votre intérêt pour contribuer à la plateforme Omra ! Ce document vous guidera à travers le processus de contribution.

## 📋 Table des matières

- [Code de conduite](#code-de-conduite)
- [Comment contribuer](#comment-contribuer)
- [Standards de code](#standards-de-code)
- [Tests](#tests)
- [Processus de Pull Request](#processus-de-pull-request)

## 📜 Code de conduite

En participant à ce projet, vous acceptez de respecter notre code de conduite :

- Soyez respectueux et inclusif
- Acceptez les critiques constructives
- Concentrez-vous sur ce qui est le mieux pour la communauté
- Faites preuve d'empathie envers les autres membres

## 🚀 Comment contribuer

### Signaler un bug

1. Vérifiez que le bug n'a pas déjà été signalé dans les [Issues](../../issues)
2. Créez une nouvelle issue avec le template "Bug Report"
3. Incluez :
   - Description claire du problème
   - Étapes pour reproduire
   - Comportement attendu vs comportement actuel
   - Captures d'écran si applicable
   - Environnement (OS, version Java, etc.)

### Proposer une fonctionnalité

1. Créez une issue avec le template "Feature Request"
2. Décrivez clairement la fonctionnalité
3. Expliquez pourquoi elle serait utile
4. Proposez une implémentation si possible

### Contribuer au code

1. **Fork** le repository
2. **Clone** votre fork localement
3. Créez une **branche** pour votre fonctionnalité
4. **Committez** vos changements
5. **Push** vers votre fork
6. Ouvrez une **Pull Request**

## 💻 Standards de code

### Java

#### Style de code

Suivez les conventions Java standard :

```java
// ✅ Bon
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

// ❌ Mauvais
public class userservice {
    private UserRepository repo;
    
    public User FindById(long ID) {
        return repo.findById(ID).get();
    }
}
```

#### Conventions de nommage

- **Classes** : PascalCase (`UserService`, `FlightController`)
- **Méthodes** : camelCase (`findById`, `calculateOptimalPlan`)
- **Variables** : camelCase (`userName`, `totalCost`)
- **Constantes** : UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT`)
- **Packages** : lowercase (`com.omra.auth.service`)

#### Structure des classes

```java
public class ExampleService {
    // 1. Constantes
    private static final int MAX_ATTEMPTS = 3;
    
    // 2. Variables d'instance
    private final Repository repository;
    private final Logger logger;
    
    // 3. Constructeur
    public ExampleService(Repository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(ExampleService.class);
    }
    
    // 4. Méthodes publiques
    public void publicMethod() {
        // ...
    }
    
    // 5. Méthodes privées
    private void privateMethod() {
        // ...
    }
}
```

#### Gestion des exceptions

```java
// ✅ Bon - Exception spécifique
public User findUser(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
}

// ❌ Mauvais - Exception générique
public User findUser(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Error"));
}
```

#### Logging

```java
// ✅ Bon
logger.info("User {} logged in successfully", userId);
logger.error("Failed to process payment for order {}", orderId, exception);

// ❌ Mauvais
System.out.println("User logged in");
logger.error("Error: " + exception.getMessage());
```

### Spring Boot

#### Controllers

```java
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
```

#### Services

```java
@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        logger.debug("Finding user with id: {}", id);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToDto(user);
    }
    
    @Transactional
    public UserDto create(CreateUserRequest request) {
        logger.info("Creating new user with email: {}", request.getEmail());
        // Validation
        validateEmail(request.getEmail());
        // Création
        User user = new User();
        user.setEmail(request.getEmail());
        user = userRepository.save(user);
        logger.info("User created successfully with id: {}", user.getId());
        return mapToDto(user);
    }
    
    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already in use");
        }
    }
    
    private UserDto mapToDto(User user) {
        // Mapping logic
        return new UserDto(user.getId(), user.getEmail());
    }
}
```

#### Repositories

```java
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.enabled = true AND u.createdAt > :date")
    List<User> findActiveUsersSince(@Param("date") LocalDateTime date);
}
```

### Tests

#### Tests unitaires

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void findById_UserExists_ReturnsUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        // When
        UserDto result = userService.findById(userId);
        
        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
    }
    
    @Test
    void findById_UserNotFound_ThrowsException() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }
}
```

#### Tests d'intégration

```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    void createUser_ValidRequest_ReturnsCreated() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest("test@example.com", "password");
        
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
```

## 🧪 Tests

### Exécuter les tests

```bash
# Tous les tests
mvn test

# Tests d'un service spécifique
cd auth-service
mvn test

# Avec couverture
mvn test jacoco:report
```

### Couverture de code

- Minimum 70% de couverture pour les nouvelles fonctionnalités
- Testez les cas normaux ET les cas d'erreur
- Utilisez des noms de tests descriptifs

### Bonnes pratiques

1. **Arrange-Act-Assert** : Structurez vos tests clairement
2. **Un test = un concept** : Ne testez qu'une chose à la fois
3. **Tests indépendants** : Chaque test doit pouvoir s'exécuter seul
4. **Noms descriptifs** : `findById_UserNotFound_ThrowsException`
5. **Données de test** : Utilisez des données réalistes

## 🔄 Processus de Pull Request

### Avant de soumettre

- [ ] Le code compile sans erreur
- [ ] Tous les tests passent
- [ ] Vous avez ajouté des tests pour votre code
- [ ] Le code respecte les standards
- [ ] La documentation est à jour
- [ ] Les commits sont clairs et atomiques

### Template de Pull Request

```markdown
## Description
Brève description des changements

## Type de changement
- [ ] Bug fix
- [ ] Nouvelle fonctionnalité
- [ ] Breaking change
- [ ] Documentation

## Tests
- [ ] Tests unitaires ajoutés
- [ ] Tests d'intégration ajoutés
- [ ] Tous les tests passent

## Checklist
- [ ] Code respecte les standards
- [ ] Documentation mise à jour
- [ ] Pas de warnings de compilation
- [ ] Commits atomiques et clairs
```

### Revue de code

Votre PR sera revue selon ces critères :

1. **Fonctionnalité** : Le code fait-il ce qu'il est censé faire ?
2. **Tests** : Y a-t-il suffisamment de tests ?
3. **Qualité** : Le code est-il lisible et maintenable ?
4. **Performance** : Y a-t-il des problèmes de performance ?
5. **Sécurité** : Y a-t-il des failles de sécurité ?

### Après la revue

- Répondez aux commentaires
- Effectuez les modifications demandées
- Demandez une nouvelle revue si nécessaire

## 📝 Messages de commit

### Format

```
type(scope): subject

body (optional)

footer (optional)
```

### Types

- `feat`: Nouvelle fonctionnalité
- `fix`: Correction de bug
- `docs`: Documentation
- `style`: Formatage, point-virgules manquants, etc.
- `refactor`: Refactoring du code
- `test`: Ajout de tests
- `chore`: Maintenance

### Exemples

```bash
# Bon
feat(auth): add password reset functionality
fix(hotel): correct distance calculation
docs(readme): update installation instructions

# Mauvais
update stuff
fix bug
changes
```

## 🎯 Priorités

### High Priority
- Bugs de sécurité
- Bugs critiques
- Performance

### Medium Priority
- Nouvelles fonctionnalités
- Améliorations UX
- Refactoring

### Low Priority
- Documentation
- Tests supplémentaires
- Optimisations mineures

## 📚 Ressources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

## 💬 Questions ?

N'hésitez pas à :
- Ouvrir une issue pour poser des questions
- Rejoindre les discussions
- Demander de l'aide dans les PR

Merci de contribuer à la plateforme Omra ! 🙏
