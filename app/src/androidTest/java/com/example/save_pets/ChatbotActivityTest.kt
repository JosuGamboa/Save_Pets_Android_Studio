import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.save_pets.ChatbotActivity
import com.example.save_pets.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatbotActivityTest {

    @Test
    fun testUserMessageFormat() {
        val scenario = ActivityScenario.launch(ChatbotActivity::class.java)
        scenario.onActivity { activity ->
            activity.addUserMessage("Hola")
            val layout = activity.findViewById<LinearLayout>(R.id.chatLayout)
            val lastView = layout.getChildAt(layout.childCount - 1) as TextView
            assertEquals("Tú: Hola", lastView.text.toString())
        }
    }
    @Test
    fun testAddBotMessage_displaysBotMessageCorrectly() {
        val scenario = ActivityScenario.launch(ChatbotActivity::class.java)
        scenario.onActivity { activity ->
            activity.addBotMessage("Soy tu asistente")

            val lastMessageView = activity.findViewById<LinearLayout>(R.id.chatLayout)
                .getChildAt(activity.findViewById<LinearLayout>(R.id.chatLayout).childCount - 1) as TextView

            assertEquals("Bot: Soy tu asistente", lastMessageView.text.toString())
        }
    }
    @Test
    fun testUpdateLastBotMessage_updatesBotResponseCorrectly() {
        val scenario = ActivityScenario.launch(ChatbotActivity::class.java)
        scenario.onActivity { activity ->
            // Primero agregamos un mensaje del bot
            activity.addBotMessage("Cargando respuesta...")

            // Luego simulamos que el bot responde
            activity.updateLastBotMessage("Hola, ¿cómo puedo ayudarte?")

            val lastMessageView = activity.findViewById<LinearLayout>(R.id.chatLayout)
                .getChildAt(activity.findViewById<LinearLayout>(R.id.chatLayout).childCount - 1) as TextView

            assertEquals("Bot: Hola, ¿cómo puedo ayudarte?", lastMessageView.text.toString())
        }
    }
}