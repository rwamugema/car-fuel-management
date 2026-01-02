package com.CarFuelManagement.CarFuelManagement;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
@WebServlet(urlPatterns = "/servlet/fuel-stats")
public class FuelStatsServlet extends HttpServlet {

    @Autowired
    private CarService carService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String carIdParam = req.getParameter("carId");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (carIdParam == null || carIdParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"carId is required\"}");
            return;
        }

        try {
            Long carId = Long.valueOf(carIdParam);
            Map<String, Object> stats = carService.getFuelStats(carId);

            resp.setStatus(HttpServletResponse.SC_OK);

            String json = objectMapper.writeValueAsString(stats);
            resp.getWriter().write(json);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid carId format\"}");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Car not found\"}");
        }
    }
}
