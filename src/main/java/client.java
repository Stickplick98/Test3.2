import hepl.sysdist.helloworld.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class client {
    public static void main(String[] args) throws Exception{
        GreeterGrpc.GreeterBlockingStub blockingStub; // Déclare une variable de type GreeterBlockingStub, qui est un stub gRPC utilisé pour effectuer des appels bloquants.
        String target = "localhost:50053";
        String user = "Gaston";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build(); // Crée un canal de communication gRPC avec le service distant en utilisant l'adresse spécifiée.
        try {
            blockingStub = GreeterGrpc.newBlockingStub(channel); // Initialise le stub avec le canal.
            HelloRequest helloRequest = HelloRequest.newBuilder().setName(user).build(); // Crée une requête gRPC avec le nom de l'utilisateur.
            HelloReply helloReply = blockingStub.sayHello(helloRequest); // Effectue un appel bloquant à la méthode sayHello du service distant en passant la requête, et récupère la réponse.
            System.out.println(helloReply.getMessage());
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS); // Ferme le canal de communication gRPC lorsque l'utilisation est terminée, avec un délai d'attente de 5 secondes pour permettre une fermeture propre.
        }
    }
}
