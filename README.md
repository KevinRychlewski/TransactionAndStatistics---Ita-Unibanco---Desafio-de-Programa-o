# 📊 Transaction Statistics API

API REST desenvolvida em **Java 21 + Spring Boot** para registrar transações financeiras e calcular **estatísticas em tempo real** considerando apenas as transações ocorridas nos **últimos 60 segundos**.

O projeto segue o padrão de **desafios técnicos utilizados por instituições financeiras (ex.: Itaú)**, com foco em **regras de negócio claras, arquitetura limpa, concorrência, versionamento com Git e testes unitários**.

---

## 🚀 Funcionalidades

- Registro de transações financeiras
- Validação de regras de negócio (tempo e valor)
- Cálculo dinâmico de estatísticas
- Repositório em memória **thread-safe**
- Tratamento global de exceções
- Testes unitários focados em regras de negócio

---

## 🧠 Regras de Negócio

### Transações
- `amount` deve ser **maior ou igual a 0**
- `timestamp`:
  - deve ser informado pelo cliente
  - **não pode estar no futuro**
  - deve estar dentro da janela dos **últimos 60 segundos**
- Transações fora da janela:
  - **não são erro**
  - são **ignoradas**

### Estatísticas
- Calculadas sob demanda
- Consideram apenas transações dos últimos 60 segundos
- Nunca retornam valores `null`
- Estatística vazia retorna todos os valores como `0`

---

## 🌐 Endpoints

### ➕ Criar transação
POST /transactions

bash
Copiar código

#### Body
json
{
  "amount": 100.50,
  "timestamp": "2026-01-08T12:00:00Z"
}
Respostas
Status	Descrição
201	Transação criada
204	Transação fora da janela de 60s
422	Dados válidos no formato, porém inválidos nas regras
400	JSON inválido

🗑️ Remover todas as transações
bash
Copiar código
DELETE /transactions
Resposta
css
Copiar código
204 No Content
📈 Obter estatísticas
pgsql
Copiar código
GET /statistics
Resposta
json
Copiar código
{
  "sum": 150.00,
  "avg": 50.00,
  "min": 20.00,
  "max": 80.00,
  "count": 3
}
📌 Sempre retorna 200 OK, mesmo quando não há transações.

🏗️ Arquitetura do Projeto
text
Copiar código
controller
 ├── TransactionController
 └── StatisticsController

service
 ├── TransactionService
 └── StatisticsService

repository
 └── TransactionRepository (in-memory, thread-safe)

dto
 ├── TransactionRequestDTO
 └── StatisticsResponseDTO

exception
 └── GlobalExceptionHandler
Principais decisões técnicas
Repository em memória usando ConcurrentLinkedQueue

Services sem estado, cálculo de estatísticas sob demanda

Controllers finos, responsáveis apenas por mapear HTTP

GlobalExceptionHandler para respostas consistentes

Uso de BigDecimal para valores monetários

Uso de Instant para manipulação correta de tempo

🧪 Testes
Foram implementados testes unitários focados exclusivamente na camada de serviço.

TransactionService
Criação de transação válida

Rejeição de timestamp no futuro

Ignorar transações fora da janela de 60 segundos

StatisticsService
Estatística vazia

Estatística com múltiplas transações válidas

📌 Os testes:

Não sobem o contexto do Spring

São rápidos

Validam apenas regras de negócio

🧬 Versionamento (Git)
O projeto foi desenvolvido utilizando feature branches e Pull Requests, seguindo um fluxo profissional:

develop → branch de desenvolvimento

main → branch final

branches por etapa (feature/service, feature/controller, feature/tests, etc.)

▶️ Como executar o projeto
Pré-requisitos
Java 21+
Maven

Executar a aplicação
bash
Copiar código
mvn spring-boot:run
Executar os testes
bash
Copiar código
mvn test
