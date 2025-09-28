# StockSense 🚚📦
# StockSense

StockSense é um sistema de inventário online desenvolvido em Java utilizando o framework Spring Boot. O objetivo do projeto é gerenciar produtos, estoques e operações relacionadas, oferecendo uma interface web para administração e consulta.
StockSense é um sistema de inventário online desenvolvido em Java utilizando o framework Spring Boot. O objetivo do projeto é gerenciar produtos, estoques e operações relacionadas, oferecendo uma interface web para administração e consulta.

## Funcionalidades
- Cadastro, alteração, exclusão e listagem de produtos 📝
- Filtros de busca para produtos 🔍
- Interface web responsiva com páginas HTML, CSS e JavaScript 💻
- Organização modular do código seguindo boas práticas de arquitetura Java 🏗️
- Utilização de templates para páginas dinâmicas 🧩
## 🗂️ Estrutura do Projeto
## 📁 Detalhamento dos Principais Arquivos e Pastas
## ▶️ Como Executar o Projeto
1. Certifique-se de ter o Java 11+ e o Maven instalados ☕️
## 🛠️ Tecnologias Utilizadas
- Java ☕️
- Spring Boot 🌱
- Maven 🧰
- Thymeleaf 📝
- HTML, CSS, JavaScript 🖥️
## ℹ️ Observações
## 🖼️ Estrutura de Telas
- **Página Inicial**: `templates/home/index.html` 🏠
- **Listagem de Produtos**: `templates/Produto/listProduto.html` 📋
- **Inserção de Produto**: `templates/Produto/inserirProduto.html` ➕
- **Alteração de Produto**: `templates/Produto/alterar.html` ✏️
- **Filtro de Produtos**: `templates/Produto/filtroProdutos.html` 🔎
## 🤝 Contribuição
1. Faça um fork do projeto 🍴
2. Crie uma branch para sua feature (`git checkout -b feature/nome-feature`) 🌿
3. Commit suas alterações (`git commit -m 'feat: minha feature'`) 💾
4. Faça um push para o branch (`git push origin feature/nome-feature`) 🚀
5. Abra um Pull Request 🔄
## 📝 Licença

- Cadastro, alteração, exclusão e listagem de produtos
- Filtros de busca para produtos
- Interface web responsiva com páginas HTML, CSS e JavaScript
- Organização modular do código seguindo boas práticas de arquitetura Java
- Utilização de templates para páginas dinâmicas

## Estrutura do Projeto

```
├── pom.xml                        # Gerenciamento de dependências Maven
├── src/
│   ├── main/
│   │   ├── java/br/com/stocksense/  # Código-fonte Java
│   │   │   ├── controllers/         # Controladores MVC
│   │   │   ├── DAO/                 # Objetos de acesso a dados
│   │   │   ├── enums/               # Enumerações
│   │   │   └── model/               # Modelos de domínio
│   │   └── resources/
│   │       ├── application.properties # Configurações da aplicação
│   │       ├── static/                # Arquivos estáticos (CSS, JS, imagens)
│   │       └── templates/             # Templates HTML (Thymeleaf)
│   │           ├── fragmentos/        # Fragmentos reutilizáveis
│   │           ├── home/              # Página inicial
│   │           └── Produto/           # Páginas de produto
│   └── test/                          # Testes automatizados
└── target/                            # Arquivos gerados na build
```

## Detalhamento dos Principais Arquivos e Pastas

- **pom.xml**: arquivo de configuração do Maven, define as dependências do projeto.
- **application.properties**: configurações do Spring Boot (porta, banco de dados, etc).
- **static/**: contém arquivos estáticos como CSS, imagens e scripts JS.
- **templates/**: páginas HTML dinâmicas, organizadas por contexto (home, produto, fragmentos).
- **controllers/**: classes responsáveis por receber requisições HTTP e direcionar para as views ou serviços.
- **DAO/**: classes de acesso a dados, responsáveis por interagir com o banco de dados.
- **model/**: classes que representam as entidades do domínio (ex: Produto).
- **enums/**: definições de tipos enumerados usados no domínio.

## Como Executar o Projeto

1. Certifique-se de ter o Java 11+ e o Maven instalados.
2. No terminal, execute:
   ```
   ./mvnw spring-boot:run
   ```
   ou, no Windows:
   ```
   mvnw.cmd spring-boot:run
   ```
3. Acesse `http://localhost:8080` no navegador.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- Thymeleaf
- HTML, CSS, JavaScript

## Observações

- O projeto pode ser customizado para integração com diferentes bancos de dados, alterando o `application.properties`.
- As imagens e arquivos estáticos estão em `src/main/resources/static`.
- Os templates HTML utilizam fragmentos para facilitar a manutenção e reutilização de código.

## Estrutura de Telas

- **Página Inicial**: `templates/home/index.html`
- **Listagem de Produtos**: `templates/Produto/listProduto.html`
- **Inserção de Produto**: `templates/Produto/inserirProduto.html`
- **Alteração de Produto**: `templates/Produto/alterar.html`
- **Filtro de Produtos**: `templates/Produto/filtroProdutos.html`

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nome-feature`)
3. Commit suas alterações (`git commit -m 'feat: minha feature'`)
4. Faça um push para o branch (`git push origin feature/nome-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
