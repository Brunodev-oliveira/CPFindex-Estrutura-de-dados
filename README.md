# Verificador CPF — Sistema Receita Federal

Projeto acadêmico em Java que simula um sistema de consulta de CPF utilizando uma **Árvore Binária de Busca (BST)** como estrutura de dados principal.

---

## 📋 Sobre o Projeto

O sistema permite:
- Cadastrar CPFs na base de dados
- Consultar CPFs existentes
- Remover CPFs da base
- Carregar automaticamente 1.000.000 de registros de um arquivo `mock.json` na inicialização

**Estruturas de dados implementadas:** `BinarySearchTree`, `LinkedList`, `NoBst`, `NodeLkdList`

---

## 🛠️ Pré-requisitos

| Ferramenta | Versão mínima | Download |
|------------|---------------|----------|
| Java JDK   | 17+           | https://adoptium.net |
| IntelliJ IDEA (recomendado) | Qualquer | https://www.jetbrains.com/idea |

> **Não é necessário instalar o Gradle manualmente.** O projeto usa o **Gradle Wrapper** (`gradlew`), que baixa a versão correta automaticamente na primeira execução.

---

## ⚠️ Arquivo mock.json (necessário para rodar)

O arquivo `src/mock.json` **não está no repositório** (está no `.gitignore` por ter ~93MB). Sem ele, o sistema vai falhar ao iniciar.

**Baixe pelo Google Drive do grupo:** https://drive.google.com/drive/folders/1FiRC3OePOqhtf-i3cLqjTp6Z2eLGsXx0?usp=sharing

Após baixar, coloque o arquivo no seguinte caminho dentro do projeto:

```
verificador-cpf/
└── src/
    └── mock.json   ← coloque aqui
```

---

## ⚙️ Configuração do Projeto no IntelliJ IDEA

1. **Abra o projeto**
   - Vá em `File → Open` e selecione a pasta `verificador-cpf`
   - O IntelliJ vai detectar o arquivo `build.gradle` e perguntar: **"Open as Gradle project?"** → clique em **Trust Project**

2. **Aguarde a sincronização**
   - O IntelliJ vai baixar as dependências automaticamente (requer internet na primeira vez)
   - Você verá o progresso na barra inferior

3. **Verifique o JDK**
   - Vá em `File → Project Structure → Project`
   - Certifique-se de que o **SDK** está configurado como Java 17 ou superior
   - Se não aparecer nenhum JDK, clique em `Add SDK → Download JDK`

---

## ▶️ Como Executar

### Via IntelliJ IDEA
- Abra o arquivo `src/main/java/com/cpfindex/Main.java`
- Clique no botão **▶ Run** ao lado do método `main`

### Via terminal (linha de comando)

**Linux/macOS:**
```bash
./gradlew run
```

**Windows:**
```cmd
gradlew.bat run
```

> Na primeira execução, o Gradle Wrapper vai baixar o Gradle 9.0.0 automaticamente. Isso pode levar alguns minutos dependendo da conexão.

---

## 📦 Estrutura do Projeto

```
verificador-cpf/
├── src/
│   └── main/java/
│       ├── com/cpfindex/
│       │   └── Main.java               ← Ponto de entrada
│       ├── controller/
│       │   ├── ReceitaController.java  ← Lógica principal do sistema
│       │   └── DataGeneratorControler.java ← Carrega o mock.json
│       ├── model/
│       │   ├── BinarySearchTree.java   ← Estrutura BST
│       │   ├── NoBst.java              ← Nó da BST
│       │   ├── LinkedList.java         ← Lista encadeada
│       │   ├── NodeLkdList.java        ← Nó da lista
│       │   └── Contribuinte.java       ← Modelo de dados
│       └── view/
│           ├── MenuView.java           ← Interface de texto
│           └── RelatorioView.java      ← Exibição de relatórios
├── src/mock.json                       ← Base de dados inicial (baixar pelo Drive)
├── build.gradle                        ← Configuração do build
├── gradlew / gradlew.bat               ← Gradle Wrapper
└── gradle/wrapper/
    └── gradle-wrapper.properties       ← Define versão do Gradle (9.0.0)
```

---

## 📄 Dependências (build.gradle)

```groovy
dependencies {
    implementation 'org.json:json:20240303'
}
```

A única dependência externa é a biblioteca `org.json` para leitura do `mock.json`. Ela é baixada automaticamente pelo Gradle via Maven Central.

---

## ❓ Problemas Comuns

**"Could not find tools.jar"**
→ Certifique-se de ter o **JDK** instalado (não apenas o JRE). Configure o caminho em `File → Project Structure`.

**"Permission denied: ./gradlew" (Linux/macOS)**
→ Execute no terminal:
```bash
chmod +x gradlew
```

**O projeto não reconhece como Gradle**
→ Clique com o botão direito no arquivo `build.gradle` → `Link Gradle Project`

**Demora muito para iniciar**
→ Normal na primeira execução. O sistema carrega 1 milhão de registros do `mock.json` na BST antes de exibir o menu.
