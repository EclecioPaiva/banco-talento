package com.projeto.servlet;

import com.google.gson.Gson;
import com.projeto.service.TalentService;
import com.projeto.service.ModelTalento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("/talentos/*")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    private TalentService talentService = new TalentService();

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(obj));
        out.flush();
    }

   // Recupera talentos
   // GET/banco-talento/talentos
   // GET/banco-talento/talentos/id
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // request.getRequestDispatcher("servlet.html").forward(req, response);

        String pathInfo = request.getPathInfo();  // Corrigido para 'req' em vez de 'request'
        System.out.println("GET pathInfo: " + pathInfo);
        if (pathInfo == null || pathInfo.equals("/")) {
            sendAsJson(response, talentService.getAll());
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Requisição inválida");
            return;
        }

        String modelId = splits[1];
        if (!talentService.containsKey(modelId)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Talento não encontrado");
            return;
        }

        System.out.println("ID: " + modelId);
        ModelTalento talento = talentService.getById(modelId);
        sendAsJson(response, talento);
        return;

        // PrintWriter writer = responsegetWriter();
        // writer.print("Meus talentos");
        //  writer.print("pathInfo");
    }

    // Insere um novo talento no Banco
    // POST/banco-talento/talentos
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("POST pathInfo: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            System.out.println("POST payload: " + payload);

            ModelTalento talent = gson.fromJson(buffer.toString(), ModelTalento.class);
            sendAsJson(response, talentService.addNew(talent.nome));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // Atualiza um talento no banco
    // PUT/banco-talento/talentos/id
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("PUT pathInfo: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String modelId = splits[1];
        if (!talentService.containsKey(modelId)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Talento não encontrado");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        System.out.println("payload: " + payload);

        ModelTalento updatedTalent = gson.fromJson(payload, ModelTalento.class);
        sendAsJson(response, talentService.update(modelId, updatedTalent.nome));
    }


    // Excui um talento no banco
    // DELETE/banco-talento/talentos/id
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("DELETE pathInfo: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String modelId = splits[1];
        if (!talentService.containsKey(modelId)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Talento não encontrado");
            return;
        }

        System.out.println("ID: " + modelId);
        sendAsJson(response, talentService.delete(modelId));
    }
}
