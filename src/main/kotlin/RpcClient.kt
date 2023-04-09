import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.qameta.allure.grpc.AllureGrpc
import org.example.RpcServiceGrpc

object RpcClient {

    private val channel: ManagedChannel? = ManagedChannelBuilder
        .forAddress(RpcMainServiceURL.GrpcServiceURL, RpcMainServiceURL.GrpcServicePort)
        .usePlaintext()
        .build()

    val stub: RpcServiceGrpc.RpcServiceBlockingStub? = RpcServiceGrpc.newBlockingStub(channel)
        .withInterceptors(AllureGrpc())
}