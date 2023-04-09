import com.google.common.collect.ImmutableList
import com.google.protobuf.Empty
import io.grpc.ManagedChannelBuilder
import io.qameta.allure.Description
import io.qameta.allure.grpc.AllureGrpc
import org.assertj.core.api.Assertions.assertThat
import org.example.RpcServiceGrpc
import org.example.TestRequest
import org.example.TestResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

class TestGrpcService {
    companion object {
//        private val ch = ManagedChannelBuilder
//            .forAddress("localhost", 9090)
//            .usePlaintext()
//            .build()
//
//        private val stub = RpcServiceGrpc.newBlockingStub(ch)
//            .withInterceptors(AllureGrpc())

        private val stub = RpcClient.stub

        @JvmStatic
        @BeforeAll
        fun setupAll() {
            println("before all executed")
        }
    }

    @BeforeEach
    fun setupEach() {
        println("before each executed")
    }

    @Test
    @Description("unary call test gRPC request")
    fun unaryCallTest() {
        val expected = TestResponse.newBuilder()
            .setId(1)
            .setName("kek")
            .setDescr("same descr for all")
            .build()

        val response = stub?.getTest(
            TestRequest.newBuilder()
                .setId(1)
                .build()
        )

        assertThat(response).usingRecursiveComparison()
            .ignoringFields("descr_", "memoizedHashCode")
            .isEqualTo(expected)
        assertThat(response).isEqualTo(expected)
        assertThat(response?.id).isEqualTo(1)
        assertThat(response?.name).isEqualTo("one")
    }

    @Test
    @Description("stream call test gRPC request")
    fun streamingTest() {
        val expected = TestResponse.newBuilder()
            .setId(1)
            .setName("test")
            .setDescr("test descr")
            .build()
        val allTest = stub?.getAllTest(Empty.getDefaultInstance())
        val infoResponses = ImmutableList.copyOf(allTest!!).reverse()
        assertThat(infoResponses).hasSize(3)
        assertThat(infoResponses[0].id).isEqualTo(1)
        assertThat(infoResponses[2].name).isEqualTo("three")

        infoResponses.forEach {
            assertThat(it).isEqualTo(expected)
        }
    }
}