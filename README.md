Projeto dispoinível em http://localhost:8080/banco-talento/talentos

[Tomcat Web Application Manager](http://localhost:8080/manage)


## Tests

Comandos `curl` para testar todos os métodos (`GET`, `POST`, `PUT`, `DELETE`) do seu servlet:

### 1. **Listar todos os talentos (GET)**:
```bash
curl -X GET http://localhost:8080/banco-talento/talentos
```

### 2. **Listar um talento específico (GET)**:
```bash
curl -X GET http://localhost:8080/banco-talento/talentos/1
```

### 3. **Adicionar um novo talento (POST)**:
```bash
curl -X POST http://localhost:8080/banco-talento/talentos \
-H "Content-Type: application/json" \
-d '{
  "nome": "Novo Talento"
}'
```

### 4. **Atualizar um talento existente (PUT)**:
```bash
curl -X PUT http://localhost:8080/banco-talento/talentos/1 \
-H "Content-Type: application/json" \
-d '{
  "nome": "Talento 1 Atualizado"
}'
```

### 5. **Deletar um talento (DELETE)**:
```bash
curl -X DELETE http://localhost:8080/banco-talento/talentos/3
```

Esses comandos `curl` testam os métodos HTTP em seu servlet, permitindo interagir com o serviço REST que você desenvolveu.


---
Referências:
- [Servlets, o coração do Java para web](https://www.devmedia.com.br/servlets-o-coracao-do-java-para-web/26036);
- [Part 5. Servlets and the Java Servlet API. Writing a simple web application](https://codegym.cc/groups/posts/301-part-5-servlets-and-the-java-servlet-api-writing-a-simple-web-application)
- [Como configurar um Java Servlet para Visual Studio Code em 2024?](https://medium.com/@opedrolacerda/como-configurar-um-java-servlet-para-visual-studio-code-em-2024-8b20d17bb5a6);
- [Criando uma aplicação Java Web com Servlet](https://www.alura.com.br/artigos/criando-uma-aplicacao-java-web-com-servlet?utm_term=&utm_campaign=%5BSearch%5D+%5BPerformance%5D+-+Dynamic+Search+Ads+-+Artigos+e+Conte%C3%BAdos&utm_source=adwords&utm_medium=ppc&hsa_acc=7964138385&hsa_cam=11384329873&hsa_grp=164240702375&hsa_ad=703829337057&hsa_src=g&hsa_tgt=dsa-2276348409543&hsa_kw=&hsa_mt=&hsa_net=adwords&hsa_ver=3&gad_source=1&gclid=Cj0KCQjwmOm3BhC8ARIsAOSbapXZ8y-2KymnbhJOuS0zyq1Tp2pp5uRB_AMQy0yfgFqwCw8wGTOWtYwaAsGyEALw_wcB);
- [leefsmp/models.java](https://gist.github.com/leefsmp/b4c089734852c793cf85);
