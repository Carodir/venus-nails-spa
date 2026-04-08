#  Venus Nails Spa — Sistema de Gestión Web

> Plataforma web para administrar citas, clientes, servicios, pagos y horarios de un spa de uñas, con tres roles diferenciados.

![Java](https://img.shields.io/badge/Java-21-7c3aed?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.13-6d28d9?style=flat-square)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Motor_de_plantillas-2563eb?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-Base_de_datos-16a34a?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.9.14-ea580c?style=flat-square)

---

## 📋 Descripción del proyecto

Venus Nails Spa es un sistema web de gestión integral para un spa de uñas. Permite administrar clientes, agendar citas, gestionar servicios, registrar pagos, asignar horarios a manicuristas y recopilar reseñas. El sistema cuenta con autenticación por roles (admin, cliente, manicurista) y una capa de API REST adicional para integración con otros servicios.

---

## ⚙️ Configuración técnica

| Parámetro | Valor |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.5.13 |
| Gestor de dependencias | Maven 3.9.14 |
| Motor de plantillas | Thymeleaf |
| Base de datos | MySQL — `venus_nails` en `localhost:3306` |
| Seguridad | Spring Security con roles |
| Puerto del servidor | `8081` |

---

## 🧩 Stack tecnológico (dependencias Maven)

| Dependencia | Descripción |
|---|---|
| `spring-boot-starter-data-jpa` | Persistencia con JPA e Hibernate |
| `spring-boot-starter-thymeleaf` | Motor de plantillas HTML |
| `spring-boot-starter-web` | Soporte para controladores MVC y HTTP |
| `spring-boot-starter-security` | Autenticación y autorización por roles |
| `thymeleaf-extras-springsecurity6` | Integración Thymeleaf con Spring Security |
| `mysql-connector-j` | Conector JDBC para MySQL |
| `lombok` | Reducción de código boilerplate |

---

## 🚀 Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/Carodir/venus-nails-spa.git
cd venus-nails-spa
```

### 2. Crear la base de datos en MySQL

```sql
CREATE DATABASE venus_nails;
```

### 3. Configurar credenciales en `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/venus_nails
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8081
```

### 4. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

### 5. Acceder en el navegador

```
http://localhost:8081/login
```

---

## 👥 Roles del sistema

| Rol | Ruta base | Acceso |
|---|---|---|
| **Admin** | `/admin/**` | Acceso completo: usuarios, citas, servicios, pagos, horarios y reseñas |
| **Cliente** | `/cliente/**` | Panel personal: ver y agendar citas, escribir reseñas |
| **Manicurista** | `/manicurista/**` | Panel propio: ver citas del día, confirmar, cancelar y ver horarios |

> Spring Security maneja los roles internamente como `ROLE_admin`, `ROLE_cliente` y `ROLE_manicurista`.

---

## 🗂️ Arquitectura del proyecto

El proyecto sigue una arquitectura en capas basada en el patrón **MVC (Modelo - Vista - Controlador)**.

```
venus.nails/
├── VenusNailsSpaApplication.java     ← Clase principal
├── modelo/                           ← Entidades JPA (tablas BD)
│   ├── Usuario.java
│   ├── Cita.java
│   ├── Servicio.java
│   ├── Pago.java
│   ├── Horario.java
│   └── Resena.java
├── repositorio/                      ← Interfaces JpaRepository
│   ├── UsuarioRepositorio.java
│   ├── CitaRepositorio.java
│   ├── ServicioRepositorio.java
│   ├── PagoRepositorio.java
│   ├── HorarioRepositorio.java
│   └── ResenaRepositorio.java
├── controlador/                      ← Controladores MVC y REST
│   ├── LoginControlador.java
│   ├── AdminControlador.java
│   ├── ClienteControlador.java
│   ├── ManicuristaControlador.java
│   ├── CitaControlador.java
│   ├── ServicioControlador.java
│   ├── UsuarioControlador.java
│   ├── PagoControlador.java
│   ├── HorarioControlador.java
│   └── ResenaControlador.java
└── config/                           ← Seguridad
    ├── SecurityConfig.java
    └── UserDetailsServiceImpl.java
```

---

## ✅ Módulos completados

- [x] Autenticación con Spring Security (login, registro, logout, redirección por rol)
- [x] Panel de administración (`/admin/panel`)
- [x] Módulo de citas — listar, agendar, modificar, confirmar, cancelar (`/citas`)
- [x] Módulo de servicios — CRUD completo (`/servicios`)
- [x] Módulo de usuarios — CRUD completo con roles (`/usuarios`)
- [x] Módulo de pagos — registrar y listar (`/pagos`)
- [x] Módulo de horarios — agregar y eliminar (`/horarios`)
- [x] Módulo de reseñas — agregar, listar y eliminar (`/resenas`)
- [x] Panel de cliente — citas propias y reseñas (`/cliente`)
- [x] Panel de manicurista — citas del día y horarios (`/manicurista`)
- [x] API REST con 4 módulos (rama `api`)
- [x] Versionamiento con Git y GitHub

---

## 🔌 API REST

> Disponible en la rama `api`. Todos los endpoints son públicos bajo `/api/**` y retornan JSON con los campos `exito` y `mensaje`.

### Usuarios — `/api/usuarios`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/usuarios/listar` | Lista todos los usuarios |
| `POST` | `/api/usuarios/registro` | Registra un nuevo usuario |
| `POST` | `/api/usuarios/login` | Autentica un usuario |

### Servicios — `/api/servicios`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/servicios/listar` | Lista todos los servicios |
| `GET` | `/api/servicios/{id}` | Obtiene un servicio por ID |
| `POST` | `/api/servicios/crear` | Crea un nuevo servicio |
| `DELETE` | `/api/servicios/eliminar/{id}` | Elimina un servicio por ID |

### Citas — `/api/citas`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/citas/listar` | Lista todas las citas |
| `POST` | `/api/citas/crear` | Crea una nueva cita (estado: Pendiente) |
| `PUT` | `/api/citas/confirmar/{id}` | Confirma una cita |
| `PUT` | `/api/citas/cancelar/{id}` | Cancela una cita |

### Pagos — `/api/pagos`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/pagos/listar` | Lista todos los pagos |
| `POST` | `/api/pagos/crear` | Registra un nuevo pago |

---

## 🔐 Seguridad

| Aspecto | Detalle |
|---|---|
| Framework | Spring Security |
| Autenticación | Por correo y contraseña |
| Autorización | Roles diferenciados por ruta |
| Password Encoder | `NoOpPasswordEncoder` (mejora pendiente: BCrypt) |
| CSRF | Desactivado para permitir peticiones REST |
| Rutas públicas | `/login`, `/registro`, `/css/**`, `/js/**`, `/images/**`, `/api/**` |

---

## 🗃️ Modelos de base de datos

| Entidad | Tabla | Campos principales |
|---|---|---|
| Usuario | `usuario` | idUsuario, nombre, apellido, correo, contraseña, telefono, rol |
| Cita | `citas` | idCita, fecha, horaInicio, horaFin, estado |
| Servicio | `servicio` | idServicio, nombre, descripcion, duracion, precio |
| Pago | `pago` | idPago, monto, metodo, observaciones, fecha |
| Horario | `horario_disponible` | dia, horaInicio, horaFin |
| Reseña | `resena` | comentario, calificacion (1-5), fecha |

---

## 📁 Repositorio y ramas

```
https://github.com/Carodir/venus-nails-spa
```

| Rama | Contenido |
|---|---|
| `master` | Proyecto principal con vistas Thymeleaf y todos los módulos |
| `api` | Capa de API REST agregada sin afectar la rama master |

---

## ⚠️ Mejoras pendientes

- [ ] Encriptar contraseñas con BCrypt (actualmente en texto plano)
- [ ] Página de error personalizada (reemplazar Whitelabel Error Page)
- [ ] Notificaciones de citas para clientes y manicuristas
- [ ] Calendario visual de disponibilidad al agendar citas
- [ ] Bloqueo de horarios ocupados para evitar conflictos
- [ ] Selección de manicurista disponible por servicio desde el panel del cliente

---

*Proyecto desarrollado como evidencia de aprendizaje — SENA | Tecnología en Análisis y Desarrollo de Sistemas de Información*
