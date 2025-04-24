import android.content.Context
import com.example.save_pets.DBHelper
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DBHelperTest {

    private lateinit var mockContext: Context
    private lateinit var dbHelper: DBHelper

    @Before
    fun setup() {
        // Creamos un contexto falso usando Mockito
        mockContext = Mockito.mock(Context::class.java)
        dbHelper = DBHelper(mockContext)
    }

    @Test
    fun testRegistroUsuarioExitoso() {
        val resultado = dbHelper.registrarUsuario("Juan", "Perez", "71234567", "1234")
        assertTrue("El usuario debería registrarse correctamente", resultado)
    }

    @Test
    fun testVerificarUsuarioExistente() {
        dbHelper.registrarUsuario("Ana", "Lopez", "78945612", "abcd")
        val resultado = dbHelper.verificarUsuario("Ana", "Lopez", "abcd")
        assertTrue("El usuario debería existir", resultado)
    }

    @Test
    fun testVerificarUsuarioInexistente() {
        val resultado = dbHelper.verificarUsuario("Pedro", "Gomez", "pass123")
        assertFalse("El usuario NO debería existir", resultado)
    }
}