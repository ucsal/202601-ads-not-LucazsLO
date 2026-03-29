# 📚 Projeto Olimpíadas - SOLID

Refatoração do sistema "Olimpíada de Questões" com o principal foco em melhorar a modularidade, organização de domínios e a aplicação prática da Orientação a Objetos por meio dos princípios do **SOLID**. 

## 🔧 O que mudou (Os 5 Princípios)

- **SRP (Responsabilidade Única):** A classe central `App.java` foi totalmente simplificada. Menus de texto e lógicas visuais (como o tabuleiro) foram separados em classes auxiliares, e o acesso direto aos dados passou a ser exclusividade dos pacotes `Repository`.
- **OCP (Aberto/Fechado):**  A classe `Questao` original converteu-se em Mãe Abstrata. Isso permitiu criar filhos específicos (como a `QuestaoMultiplaEscolha`) sem correr o risco de quebrar ou engessar o validador base do sistema.
- **LSP (Substituição de Liskov):** O registro de opções marcadas na prova passou do tipo engessado `char` para genérico `String`, permitindo usar qualquer tamanho de string no futuro caso novas filhas da classe Questão adentrem na avaliação.
- **ISP (Segregação de Interfaces):** Criamos obrigatoriedades flexíveis. Uma nova interface `QuestaoComAlternativas` nasceu para que os temidos "Textos de A a E" sejam implementados estritamente apenas por quem de fato precisa deles.
- **DIP (Inversão de Dependências):** O aplicativo já não chama "ArrayLists" físicos. Agora, toda a hierarquia e motor principal conversa apenas com interfaces frouxas (ex: `ParticipanteRepository`), permitindo plugar tranquilamente Bancos de Dados remotos no futuro.

## ⚙️ Melhorias de Clean Code (Bônus)

- **Encapsulamento de Responsabilidade:** O cálculo de conferência de notas foi tirado do app principal e isolado diretamente na classe modelo de `Tentativa` (Information Expert).
- **Consultas Otimizadas:** A busca e filtros em memória no `App.java` que tentavam ler coleções inteiras por varredura foram dizimadas em favor das requisições unitárias otimizadas nas interfaces de busca (`buscarPorId`).
- Código mais limpo, escalável, legível e rigorosamente dentro dos padrões modernos.
