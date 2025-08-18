<div align="center">
  <h1>🗳️ Projeto II - POO2</h1>
  <br>
  <img src="https://img.shields.io/badge/Tecnologia-Java-blue?style=for-the-badge&logo=java" alt="Java Badge">
  <img src="https://img.shields.io/badge/Sistema-Distribuído-red?style=for-the-badge" alt="Sistema Distribuído Badge">
  <img src="https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge" alt="Status Badge">
</div>

---

### 📚 Sobre o Projeto

Bem-vindo ao repositório **Projeto II - POO2**!

Este projeto implementa um **Sistema de Votação Distribuído com Interface Gráfica**, desenvolvido para fins acadêmicos na disciplina de Programação Orientada a Objetos II. O sistema simula um ambiente real de votação eletrônica, com autenticação, segurança, controle de votos e visualização dos resultados.

---

### 🚀 Funcionalidades Principais

-   **Sistema Cliente-Servidor Distribuído:** Utiliza **sockets TCP/IP** para comunicação entre múltiplos clientes e um servidor central.
-   **Votação Segura e Individual:** Cada eleitor se autentica via CPF, garantindo que cada um vote apenas uma vez. O servidor valida os CPFs e impede a duplicidade.
-   **Interface Gráfica para Administração:** O administrador pode criar novas votações, iniciar e finalizar sessões e visualizar os resultados em tempo real.
-   **Interface Gráfica para o Eleitor:** O eleitor pode inserir seu CPF, visualizar a pergunta da votação, selecionar uma opção e receber feedback sobre o status de seu voto.
-   **Controle de Votos e Apuração:** O sistema previne a duplicidade, contabiliza o resultado de forma transparente e, ao final, gera um relatório gráfico com a contagem de votos.
-   **Ajuda e Documentação Integrada:** Telas de ajuda e orientação para administradores, explicando cada etapa do processo.

---

### 🧠 O que Você Pode Aprender com Este Projeto

Este projeto é uma excelente fonte de aprendizado sobre:

-   **Programação Orientada a Objetos Avançada:** Aplicação de conceitos como encapsulamento, herança e interfaces para criar componentes bem definidos (cliente, servidor, voto, etc.).
-   **Sistemas Distribuídos:** Prática com sockets, fluxos de dados em rede e multithreading para lidar com múltiplos clientes.
-   **Integridade de Dados:** Implementação de mecanismos para garantir que cada CPF vote apenas uma vez, prevenindo fraudes.
-   **Interface Gráfica com Java Swing:** Criação de telas dinâmicas e intuitivas para administradores e eleitores, com feedback visual e relatórios.
-   **Experiência do Usuário:** Desenvolvimento de diálogos de ajuda, confirmação e feedback de erros, priorizando a usabilidade.
-   **Código Limpo:** Prática de boas práticas de documentação e separação de responsabilidades.

---

### 🛠️ Tecnologias Utilizadas

-   **Linguagem:** Java
-   **GUI:** Java Swing
-   **Comunicação:** Sockets TCP/IP
-   **Arquitetura:** Padrão Cliente-Servidor, Multithreading
-   **Estruturas de Dados:** `HashMap` (para contagem de votos)

---

### 📦 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/Lucas-Mta/Projeto-II-POO2.git](https://github.com/Lucas-Mta/Projeto-II-POO2.git)
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd Projeto-II-POO2
    ```
3.  **Compile o projeto:**
    ```bash
    javac src/**/*.java
    ```
4.  **Inicie o servidor:**
    Em um terminal, execute o servidor:
    ```bash
    java -cp src server.MainServer
    ```
5.  **Inicie os clientes:**
    Em terminais separados, execute os clientes:
    ```bash
    java -cp src client.MainClient
    ```

---

### 📂 Estrutura do Projeto
```
src/
├── client/             
├── clientServer/             
└── server/              
```

---

### 👤 Autor

<div align="center">
  <table>
    <tr>
      <td align="center">
        <a href="https://github.com/Lucas-Mta">
          <img src="https://avatars.githubusercontent.com/u/166257886?v=4" width="100px;" alt="Foto do Contribuidor"/>
          <br />
          <sub><b>Lucas Mota</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/pedrodsconceicao">
          <img src="https://avatars.githubusercontent.com/u/151598422?v=4" width="100px;" alt="Foto do Contribuidor"/>
          <br />
          <sub><b>Pedro Conceição</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/Lucas-Mta">
          <img src="https://avatars.githubusercontent.com/u/109035337?v=4" width="100px;" alt="Foto do Contribuidor"/>
          <br />
          <sub><b>Amanda Sales</b></sub>
        </a>
      </td>
    </tr>
  </table>
</div>

---

### ✨ Agradecimentos

Gostaria de agradecer à equipe da disciplina e aos colegas que colaboraram com ideias, testes e revisões, tornando este projeto possível.

---

> _Sinta-se à vontade para sugerir melhorias, abrir issues ou contribuir!_
