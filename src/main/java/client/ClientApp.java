package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterface;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import server.model.Stock;

import javax.ws.rs.core.MediaType;

/**
 * Created by Gleb on 30.06.14.
 */
public class ClientApp {

    private WebResource baseUriWebResource;
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/rest/";

    public static void main(String[] args) throws Exception {
        ClientApp app = new ClientApp();
        app.initWebResource();
        System.out.println(app.getStock("IBM"));
    }

    private void initWebResource() {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        baseUriWebResource = client.resource(BASE_URI);
        webResource = baseUriWebResource.path("stock");
    }


    public Stock getStock(String symbol) {
        return webResource.path(symbol).get(Stock.class);
    }

    public void createStock(Stock stock) {
        UniformInterface uniformInterface = webResource.type(MediaType.APPLICATION_XML);
        uniformInterface.put(stock);
    }

    public void update(Stock stock) {
        UniformInterface uniformInterface = webResource.type(MediaType.APPLICATION_XML);
        uniformInterface.post(stock);
    }

    public void delete(Stock stock) {
        UniformInterface uniformInterface = webResource.type(MediaType.APPLICATION_XML);
        uniformInterface.delete(stock);
    }
}
