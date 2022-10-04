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
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    // обработка одного подключения
                    String inString = in.readLine();

                    // TODO: Сформировать JSON из строки и добавить данные в ProductTracer

                    out.println("{" +
                            "  \"maxCategory\": {" +
                            "    \"category\": \"еда\"," +
                            "    \"sum\": 350000" +
                            "  }" +
                            "}");
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
