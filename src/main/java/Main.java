import com.google.gson.Gson;
import jsonData.JsonProductData;
import product.ProductsTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            System.out.println("Сервер запущен!");

            ProductsTracker productsTracker = new ProductsTracker();
            while (true) { // в цикле(!) принимаем подключения
                System.out.println("Сервер ожидает подключение от клиента");
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    // Обработка одного подключения
                    String inString = in.readLine();

                    // Формируем JSON из строки и добавляем данные в ProductTracer
                    Gson gson = new Gson();
                    JsonProductData jsonProductData = gson.fromJson(inString, JsonProductData.class);
                    productsTracker.addNewProduct(jsonProductData);

                    // Подготовка json для клиента, ответ на запрос
                    String outJsonData = productsTracker.getJsonMaxSumForCategoryes();

                    System.out.println("Сформированный json для клиента: ");
                    System.out.println(outJsonData);

                    out.println(outJsonData);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
