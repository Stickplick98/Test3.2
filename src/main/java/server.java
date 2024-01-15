import hepl.sysdist.helloworld.GreeterGrpc;
import hepl.sysdist.helloworld.HelloReply;
import hepl.sysdist.helloworld.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

public class server {
    private static final Logger logger = Logger.getLogger(server.class.getName());
    public static void main(String args[]) throws IOException, InterruptedException{
        final Server server = ServerBuilder.forPort(50053).addService(new GreeterImpl()).build().start();
        logger.info("Server started, listening on " + 50053);
        server.awaitTermination();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase { //GreeterGrpc.GreeterImplBase est le nom créé par la génération du fichier proto à partir du service que l'on a écrit, ici Greeter deviens donc GreeterGrpc.GreeterImplBase
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply helloReply = HelloReply.newBuilder().setMessage("Hello" +req.getName()).build();
            responseObserver.onNext(helloReply);
            responseObserver.onCompleted();
        }
    }
}
