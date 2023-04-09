import org.junit.Test

class Example {

    interface ISender {
        public fun sendMessage(to: String, text: String)
    }

    open class EmailSender(private val email: String,
                      private val emailPass: String): ISender {

        public override fun sendMessage(to: String, mailText: String){
            // using email, emailPass
        }
    }

    class PrettyEmailSender(private val email: String,
                            private val emailPass: String): EmailSender(email, emailPass) {

        private fun doItPretty(text: String): String{
            // some code
            return text
        }
        public override fun sendMessage(to: String, mailText: String){
            val prettyText = doItPretty(mailText)
            super.sendMessage(to, prettyText)
        }
    }

    class VkSender(private val login: String,
                   private val password: String): ISender {

        public override fun sendMessage(to: String, vkText: String){
            // using login, password
        }
    }

    class MyClass(aa: Int, bb: Int) {
        var a = aa
        var b = bb
    }

    private fun MyClass.extension() {
        println(a + b)
    }

    @Test
    fun main() {

        val temp = MyClass(1,2)
        temp.a = 3
        println(temp.a)
        println(temp.b)
        temp.extension()

        val someSender: ISender = EmailSender("", "") // явный интерфейс ISender

        val arr = arrayOf(
            EmailSender("from1@mail.ru", "******1"),
            VkSender("login", "pass")
        )

        arr.forEach {
            // понимается котлином какой это интерфейс и так, берется ISender
            // просто я это не указываю
            it.sendMessage("Miro", "hello")
        }
    }
}