# DragoLandia
## Introducción
Este proyecto pretende probar Hybernate a través de su utilización en un pequeño sistema de peleas automatizado. El programa consiste en que un mago se enfrenta contra un monstruo en un bosque y aquel que sobreviva es el ganador.

## Análisis
### Diagrama de Clases

```mermaid
classDiagram
direction TB

class Principal {
}

class Monstruo {
    +int id
    +String nombre
    +int vida
    +Tipo tipo
    +int fuerza
    +Monstruo()
    +Monstruo(id:int, nombre:String, vida:int, tipo:Tipo, fuerza:int)
    +atacar(objetivo:Mago)
}

class Tipo {
    <<enumeration>>
    ogro
    troll
    espectro
}

class Bosque {
    +int id
    +String nombre
    +int nivelPeligro
    +List~Monstruo~ monstruos
    +Monstruo monstruoJefe
    +Bosque()
    +Bosque(id:int, nombre:String, nivelPeligro:int, monstruoJefe:Monstruo)
    +mostrarJefe()
    +cambiarJefe(nuevoJefe:Monstruo)
}

class Mago {
    +int id
    +String nombre
    +int vida
    +int nivelMagia
    +List~Hechizo~ hechizos
    +Mago()
    +Mago(id:int, nombre:String, vida:int, nivelMagia:int)
    +lanzarHechizo(objetivo:Monstruo)
    +aprenderHechizo(h:Hechizo)
}

class Hechizo {
    +int id
    +String nombre
    +Hechizo()
    +Hechizo(nombre:String)
    +aplicar(objetivo:Monstruo)
}

class Dragon {
    +int id
    +String nombre
    +int intensidadFuego
    +int resistencia
    +Dragon()
    +Dragon(nombre:String, intensidadFuego:int, resistencia:int)
    +atacar(objetivo:Mago)
}

class Vista {
    +imprimirMensaje(mensaje:String)
    +seleccionMago()
}

class Controlador {
    -Controlador instancia
    -Modelo modelo
    -Vista vista
    +Controlador()
    +comenzarCombate()
    +getModelo():Modelo
    +getVista():Vista
    +loadFromDatabase()
}

class GestorBosque {
    -ControladorBosque instancia
    +ControladorBosque()
    +insertarBosque()
    +modificarBosque()
    +eliminarBosque()
    +borrarDatos()
    +seleccionarTodosBosques()
}

class GestorDragon {
    -CotroladorDragon instancia
    +ControladorDragon()
    +insertarDragon()
    +modificarDragon()
    +eliminarDragon()
    +borrarDatos()
    +seleccionarTodosDragon()
}

class GestorHechizo{
    -CotroladorHechizo instancia
    +ControladorHechizo()
    +insertarHechizo()
    +modificarHechizo()
    +eliminarHechizo()
    +borrarDatos()
    +seleccionarTodosHechizo()
}

class GestorMago {
    -CotroladorMago instancia
    +ControladorMago()
    +insertarMago()
    +modificarMago()
    +eliminarMago()
    +borrarDatos()
    +seleccionarTodosMago()
}

class GestorMonstruo {
    -CotroladorMonstruo instancia
    +ControladorMonstruo()
    +insertarMonstruo()
    +modificarMonstruo()
    +eliminarMonstruo()
    +borrarDatos()
    +seleccionarTodosMonstruo()
}

class Modelo {
    -static Modelo instancia
    -List~Monstruo~ listaMonstruos
    -List~Mago~ listaMagos
    -List~Bosque~ listaBosques
    -Monstruo monstruo
    -Mago mago
    -Bosque bosque
    +Modelo()
    +static getInstancia():Modelo
    +inicializarJuego()
    +addMagoToLista(m:Mago)
    +addMonstruoToLista(m:Monstruo)
    +addBosqueToLista(b:Bosque)
    +getListaMagos():List~Mago~
}

class HybernateUtil {
    -EntityManagerFactory gestorEntidades
    +getSesion()
    +cerrarSesion()
}

class InicializadorDatos {
    +borrarDatos()
    +cargarDatosIniciales()
}

class MotorCombate {
    -Modelo modelo
    -Vista vista
    +comenzarCombate()
}

InicializadorDatos --> HybernateUtil
GestorMonstruo --> InicializadorDatos : usa 
GestorMago --> InicializadorDatos : usa
GestorDragon --> InicializadorDatos : usa
GestorHechizo --> InicializadorDatos : usa
GestorBosque --> InicializadorDatos : usa

Controlador --> MotorCombate

Monstruo --> Tipo
Bosque o-- Monstruo : monstruoJefe
Bosque o-- "0..*" Monstruo : contiene
Mago o-- "0..*" Hechizo : hechizos
Bosque o-- Dragon
Principal ..> Controlador : usa
Controlador ..> GestorMonstruo : usa
Controlador ..> GestorMago : usa
Controlador ..> GestorDragon : usa
Controlador ..> GestorHechizo : usa
Controlador ..> GestorBosque : usa
Controlador --> Vista
Controlador --> Modelo
Modelo --> Vista
Modelo --> Monstruo
Modelo --> Mago
Modelo --> Bosque
Vista --> Controlador

```

## Diseño
### Diagrama E-R

```mermaid
erDiagram
direction TB

MAGO {
    int id PK
    string nombre
    int vida
    int nivelMagia
}

MONSTRUO {
    int id PK
    string nombre
    int vida
    string tipo
    int fuerza
}

BOSQUE {
    int id PK
    string nombre
    int nivelPeligro
    int dragon_id FK
}

DRAGON {
    int id PK
    string nombre
    int intensidadFuego
    int resistencia
}

HECHIZO {
    int id PK
    string nombre
}

MAGOS_HECHIZOS {
    int mago_id FK
    int hechizo_id FK
}

BOSQUE_MONSTRUO {
    int bosque_id FK
    int monstruo_id FK
}

BOSQUE ||--|| MONSTRUO : "tiene jefe"
BOSQUE ||--o{ MONSTRUO : "contiene"
BOSQUE ||--|| DRAGON : "tiene"
MAGO }o--o{ HECHIZO : "aprende"
```

## Ampliación
Mejoras o cambios posibles:
- Permitir controlar a los magos, es decir, poder elegir qué hechizos se lanzan cuándo.
- Añadir otras acciones además de atacar (curarse, bloquear).
- Añadir posibilidades de fallo. Cuando se lanza un ataque, tiene una probabilidad de fallar y no hacer daño.
- Añadir críticos. A veces el daño del ataque es doble.