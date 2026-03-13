INSERT INTO personal_info (nombre, apellidos, titulo, descripcion, imagen, email, telefono, linkedin_url, created_at) VALUES
('Miguel Ángel', 'Martínez Herrera', 'Full Stack Developer', 'Aprendiendo y mejorando cada dia, un buen entorno de trabajo crea grandes resultados', '/img/perfil.png', 'herrera.martinez.miguelangel1@gmail.com', '+34 662 404 867', 'https://www.linkedin.com/in/miguel-angel-martinez-herrera-15795927b/', CURRENT_TIMESTAMP) ;

INSERT INTO skills (name, level, icono, personal_info_id) VALUES
('JAVA', 80, 'fab fa-java', 1),
('KOTLIN', 70, 'fab fa-code', 1),
('Swift', 70, 'fab fa-swift', 1),
('SpringBoot', 60, 'fab fa-leaf', 1),
('Bases de Datos', 80, 'fab fa-database', 1),
('PHP', 80, 'fab fa-php', 1),
('HTML', 90, 'fab fa-html5', 1),
('CSS', 90, 'fab fa-css3-alt', 1),
('JS', 80, 'fab fa-js-square', 1);

INSERT INTO education (titulo, centro, inicio, fin, descripcion, personal_info_id) VALUES
('Grado Superior en Desarrollo de Aplicaciones Multiplataforma', 'Cesur', '2024-09-19', '2026-05-05', 'Titulo de Desarrollador de Aplicaciones Multiplataforma donde desarrollé aplicaciones en Android, IOs y Webs.', 1),
('Ingeniería Informática', 'Universidad de Granada', '2021-09-19', NULL, 'Estoy cursando esta carrera para acabar de completar mi titulación como Ingeniero Informático.', 1);

INSERT INTO experience (titulo, empresa, inicio, fin, descripcion, personal_info_id) VALUES
('Prácticas Backend', 'CodeArts Solutions', '2025-01-24', '2025-03-24', 'Estuve dos meses de prácticas en esta empresa donde me formé un poco con Symfony, Docker...', 1),
('Desarrollador Web', 'Autónomo', '2025-09-29', NULL, 'He realizado varias páginas webs para empresas, además del mantenimiento y actulización de estas.', 1) ;

INSERT INTO idiomas (idioma, nivel, icono, personal_info_id) VALUES
('Inglés', 'C1 - Trinity College', '/img/ingles.webp', 1) ;

INSERT INTO projects (titulo, descripcion, imagen_url, proyecto_url, personal_info_id) VALUES
('Página Web - La Flamenca', 'Diseño y desarrollo web para la empresa La Flamenca, además del mantenimiento', '/img/portada.png', 'https://laflamencatv.es', 1) ;