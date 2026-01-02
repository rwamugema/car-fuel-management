package com;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CliApplication {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        System.out.println("Car Fuel Management CLI" + Arrays.toString(args));
        if (args.length == 0) {
            printUsage();
            return;
        }

        try {
            String command = args[0];
            switch (command) {
                case "create-car" -> createCar(args);
                case "add-fuel" -> addFuel(args);
                case "fuel-stats" -> fuelStats(args);
                case "get-cars" -> getCars();
                default -> {
                    System.out.println("Unknown command: " + command);
                    printUsage();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void createCar(String[] args) throws Exception {
        String brand = getArg(args, "--brand");
        String model = getArg(args, "--model");
        String year = getArg(args, "--year");

        Map<String, Object> body = Map.of("brand", brand,
                "model", model,
                "year", Integer.valueOf(year)
        );

        String json = MAPPER.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/car"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = CLIENT.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            System.out.println("Car created successfully!");
            System.out.println(response.body());
        } else {
            System.err.println("Failed to create car: " + response.statusCode());
        }
    }

    private static void addFuel(String[] args) throws Exception {
        String carId = getArg(args, "--carId");
        String liters = getArg(args, "--liters");
        String price = getArg(args, "--price");
        String odometer = getArg(args, "--odometer");

        Map<String, Object> body = Map.of(
                "liters", Integer.valueOf(liters),
                "price", Integer.valueOf(price),
                "odometers", Integer.valueOf(odometer) 
        );

        String json = MAPPER.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/car/" + carId + "/fuel"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = CLIENT.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Fuel entry added successfully!");
            System.out.println(response.body());
        } 
        else {
            System.err.println("Failed: " + response.statusCode());
        }
    }

    private static void fuelStats(String[] args) throws Exception {
        String carId = getArg(args, "--carId");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/car/" + carId + "/fuel/stats"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request,
                HttpResponse.BodyHandlers.ofString());

        switch (response.statusCode()) {
            case 200 -> {
                Map<String, Object> stats = MAPPER.readValue(
                        response.body(), Map.class);
                System.out.println("\n=== Fuel Statistics ===");
                System.out.printf("Total fuel: %.1f L\n", stats.get("totalFuel"));
                System.out.printf("Total cost: %.2f\n", stats.get("totalCost"));
                System.out.printf("Average consumption: %.1f L/100km\n",
                        stats.get("avgConsumption"));
            }
            case 404 -> System.err.println("Car not found with ID: " + carId);
            default -> System.err.println("Failed: " + response.statusCode());
        }
    }

    private static void getCars() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/car"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            System.out.println("\n=== Cars ===");

            // Expecting a list of cars
            var cars = MAPPER.readValue(response.body(), Object.class);
            System.out.println(MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cars));

        } else {
            System.err.println("Failed to fetch cars: " + response.statusCode());
        }
    }

    private static String getArg(String[] args, String flag) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(flag)) {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException("Missing required flag: " + flag);
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  create-car --brand <brand> --model <model> --year <year>");
        System.out.println("  add-fuel --carId <id> --liters <L> --price <price> --odometer <km>");
        System.out.println("  fuel-stats --carId <id>");
        System.out.println("  get-cars");
    }
}
