# MicroServiceEmailConsumer

Email Consumer - Microserviço de Processamento de Emails com RabbitMQ

📌 Descrição

Este é um microserviço de Email Consumer que recebe mensagens de uma fila RabbitMQ e processa solicitações de envio de e-mails. Ele utiliza Spring Boot e Spring AMQP para integração com o RabbitMQ, permitindo o consumo de mensagens de forma assíncrona.

🛠 Tecnologias Utilizadas

Java 17+

Spring Boot

Spring AMQP (RabbitMQ)

RabbitMQ

Docker (para rodar o RabbitMQ localmente)

🚀 Funcionalidades

Consome mensagens de uma fila RabbitMQ.

Processa mensagens de e-mail recebidas e exibe no console.

Utiliza conversores JSON para desserialização automática.

📦 Estrutura do Projeto

📂 ms-email-consumer
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java/com/ms/email
│   │   │   ├── 📂 configs      # Configuração do RabbitMQ
│   │   │   ├── 📂 consumers    # Listeners para consumo de mensagens
│   │   │   ├── 📂 dtos         # Definição do DTO para mensagens
│   │   │   ├── 📜 EmailConsumer.java
│   │   │   ├── 📜 RabbitMQConfig.java
│   │   ├── 📜 Application.java
│   ├── 📂 resources
│   │   ├── 📜 application.yml  # Configuração do RabbitMQ
├── 📜 pom.xml
├── 📜 README.md

📜Configuração do RabbitMQ

Adicione as seguintes configurações no application.yml:

spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

broker:
  queue:
    email:
      name: email.queue
      simple: email.simple.queue

📝 Configuração do RabbitMQ no Docker

Se você não tem o RabbitMQ instalado, pode rodá-lo com Docker:

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

Acesse o RabbitMQ Management UI em http://localhost:15672 (usuário: guest, senha: guest).

📩 Exemplo de Envio de Mensagem

Para testar o envio de uma mensagem na fila, use um script Python:

import pika
import json

message = {
    "emailTo": "user@example.com",
    "text": "Hello, this is a test message!"
}

connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()
channel.queue_declare(queue='email.queue')
channel.basic_publish(exchange='', routing_key='email.queue', body=json.dumps(message))
connection.close()

🛠 Como Rodar o Projeto

Clone o repositório:

git clone https://github.com/seu-usuario/ms-email-consumer.git
cd ms-email-consumer

Configure o RabbitMQ via Docker (caso não esteja rodando).

Compile e rode a aplicação:

mvn spring-boot:run

Envie uma mensagem para a fila e veja a saída no console!

🛠 Melhorias Futuras

Integração com um serviço de envio de e-mails real.

Armazenamento de logs das mensagens recebidas.

Reprocessamento de mensagens em caso de falha.

📌 Contribuição

Sinta-se à vontade para abrir issues e enviar pull requests para melhorias no projeto!

