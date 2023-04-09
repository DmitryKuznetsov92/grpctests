import com.google.common.collect.ImmutableList
import com.google.protobuf.Empty
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.example.TestRequest
import org.example.TestResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

class TestGrpcService {
    companion object {
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
            .setName("one")
            .setDescr("same descr for all")
            .build()

        val response = stub.getTest(
            TestRequest.newBuilder()
                .setId(1)
                .build()
        )
        assertThat(response).isEqualTo(expected)

//        assertThat(response).usingRecursiveComparison()
//            .ignoringFields("descr_", "memoizedHashCode")
//            .isEqualTo(expected)
//        assertThat(response.id).isEqualTo(1)
//        assertThat(response.name).isEqualTo("one")
    }

    @Test
    @Description("stream call test gRPC request")
    fun streamingTest() {
        val allTest = stub.getAllTest(Empty.getDefaultInstance())
        val infoResponses = ImmutableList.copyOf(allTest)
        assertThat(infoResponses).hasSize(3)

        infoResponses.forEach {
            assertThat(it.id).isNotNull
            assertThat(it.name).isNotNull
            assertThat(it.descr).isNotNull
        }
    }
}