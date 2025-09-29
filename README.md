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

vamos configurar o Spring para consumir as mensagens do meu microserviço producer atraves do Cloudamqp e rabbit, vamos primeiramente pegar as credenciais da instancia 

<img width="1358" height="749" alt="image" src="https://github.com/user-attachments/assets/af50252d-d258-4dbe-8f42-ac2fbc834059" />

vamos adicionar as credenciais na aplication.propreties da mesma maneira que nos fizemos para configurar o serviço de producer vamos utilizar mesma instancia mesma fila ja criada. 

<img width="933" height="92" alt="image" src="https://github.com/user-attachments/assets/4e9e88c3-138a-4747-bbd4-cf8b6e0cbf57" />

com esta configuração nos ja estamos conectados a nossa intancia e nossa fila, agora falta apenas configurar o metodo de consumer para consumir as mensagens em fila do broker. 

para conseguirmos receber esta mensagem precisamos de adicionar uma classe de config com um conversor usando o Jackson2jsonMessageConverter, para ele interpretar receber os dados como json.

<img width="677" height="383" alt="image" src="https://github.com/user-attachments/assets/9613a798-5fe6-420f-8639-456e26d4bf90" />

precisamos tambem de adicionar algumas dependencias na nossa pom.xml 

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
    		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
    <dependency>
			<groupId>org.springframework.amqp</groupId>
			<artifactId>spring-rabbit-test</artifactId>
			<scope>test</scope>
		</dependency>

e por ultimo precisamos configurar nosso metodo de consumer, com o metodo de listener rabbit, direcionando ao broker que nos configuramos e apontamos nosso serviço de producer da mensagem

<img width="851" height="660" alt="image" src="https://github.com/user-attachments/assets/89eb149e-d29c-465e-ab89-47bfc679ec97" />

agora vamos fazer o teste se o meu serviço consegue escutar minha memsagem

<img width="641" height="463" alt="image" src="https://github.com/user-attachments/assets/29b63305-3d9d-4ef2-9d4e-fe439cfba6a4" />

ao pingar eu ja recebo a confirmação que minha mensagem foi processada e no log do consumer tambem recebo esta confirmação 

<img width="1482" height="263" alt="image" src="https://github.com/user-attachments/assets/d34ee1f5-aeef-4368-beb7-c65a1be0bba9" />

agora vou configurar as credenciais de smtp email para enviar um email no meu metodo payload. Utilizando um email valido, com sua credencial de senha de app google e com as dependencias do email,
importorg.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender; , 

<img width="519" height="163" alt="image" src="https://github.com/user-attachments/assets/b9966753-bb91-4c3b-9501-ddd1b8c93f90" />

realizando o teste chegou o email normalmente atraves de um json no nosso metodo de payload 

<img width="829" height="207" alt="image" src="https://github.com/user-attachments/assets/e6f3e2c9-14d8-49bb-b82a-7284e21c4de2" />
