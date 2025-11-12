package com.example.bdlibros

import android.R.attr.textSize
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bdlibros.ui.theme.BDLibrosTheme
import com.example.bdlibros.ui.theme.coopbl
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BDLibrosTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            PagLogin(navController = navController)
                        }
                        composable("ventas") {
                            Ventas()
                        }
                    }
                }
            }
        }
    }
}

data class ModeloRegistro(
    val dato1: String,
    val dato2: Double,
    val dato3: Int
)
interface ApiService {
    @GET("servicio.php?registros")
    suspend fun registros(): List<ModeloRegistro>

    @POST("servicio.php?iniciarSesion")
    @FormUrlEncoded
    suspend fun iniciarSesion(
        @Field("usuario") usuario: String,
        @Field("contrasena") contrasena: String,
    ): Response<String>

    @POST("servicio.php?agregarRegistro")
    @FormUrlEncoded
    suspend fun agregarRegistro(
        @Field("dato1") dato1: String,
        @Field("dato2") dato2: Double,
        @Field("dato3") dato3: Int
    ): Response<Unit>
}

val retrofit = Retrofit.Builder()
    .baseUrl("http://98.94.191.26/api/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(ApiService::class.java)






@Composable

fun LstMenuContent(navController: NavHostController, modifier: Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .horizontalScroll(scrollState)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = { navController.navigate("libros") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFAF5C96),
                contentColor = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
            //modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                //modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.palkia_icon),
                    contentDescription = "Palkia Icon",
                    //modifier = Modifier.fillMaxWidth()
                    modifier = Modifier.size(168.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Libros",
                    //modifier = Modifier.fillMaxWidth(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = coopbl,
                        fontSize = 30.sp
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("autores") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF686A76),
                contentColor = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
            //modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                //modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Autores",
                    modifier = Modifier.fillMaxWidth(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = coopbl,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.giratina_icon),
                    contentDescription = "Giratina Icon",
                    //modifier = Modifier.fillMaxWidth()
                    modifier = Modifier.size(168.dp)
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("resenas") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF32558A),
                contentColor = Color.White
            ),
            //modifier = Modifier.fillMaxWidth(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Row(
                //modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.dialga_icon),
                    contentDescription = "Palkia Icon",
                    //modifier = Modifier.fillMaxWidth()
                    modifier = Modifier.size(168.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reseñas",
                    modifier = Modifier.fillMaxWidth(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = coopbl,
                        fontSize = 26.sp
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8B30B),
                contentColor = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Cerrar sesión",
                    modifier = Modifier.fillMaxWidth(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = coopbl,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.arceus_icon),
                    contentDescription = "Arceus Icon",
                    modifier = Modifier.size(64.dp)
                )

            }
        }

    }
}

@Composable
fun LstLibrosContent(navController: NavHostController, modifier: Modifier, tabla: Int) {
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    when (tabla) {

        1 -> {
            data class Libro(val id: Int,val titulo: String, val autor: String, val fechaPub: String, val precio: Double)
            val libros = remember {
                mutableStateListOf(
                    Libro(1,"La biblia de arceus","Arceus","?", 330.0),
                    Libro(2,"Libro Escarlata","Eriad","?", 440.0),
                    Libro(3,"Don Quixote de la mancha","Miguel de Cervantes","1615-01-01", 180.0)
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(verticalScrollState),
            ) {
                Column(
                    modifier = Modifier
                        .horizontalScroll(horizontalScrollState)
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text("ID", modifier = Modifier.width(50.dp), fontWeight = FontWeight.Bold)
                        Text("Titulo", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Autor", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Publicación", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Precio", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    libros.forEachIndexed { index, objeto ->
                        val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                        Row(
                            modifier = Modifier
                                .background(bgColor)
                        ) {
                            Text(
                                objeto.id.toString(), modifier = Modifier
                                    .width(50.dp)
                            )
                            Text(
                                objeto.titulo, modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                objeto.autor, modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                objeto.fechaPub, modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                "$ ${objeto.precio}", modifier = Modifier
                                    .width(100.dp)
                            )
                            Button(onClick = {
                                libros.removeAt(index)
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the buttons
                ) {
                    Button(
                        onClick = {
                            navController.navigate("formlib")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0075E0),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Insertar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Start,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.kyogre_icon),
                            contentDescription = "Kyogre Icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate("menu")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDD2F06),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Regresar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Start,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.groudon_icon),
                            contentDescription = "Groudon Icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        libros.add(Libro(4, "data", "test", "date", 99.99))


                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF169226),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                ) {
                    Text(
                        "Insertar prueba",
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = coopbl,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.rayquaza_icon),
                        contentDescription = "Rayquaza Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }

            }


        }


        2 -> {
            data class Autor(val id: Int,val autor: String, val apellidos: String, val nacionalidad: String)
            val autores = remember {
                mutableStateListOf(
                    Autor(1,"Arceus","","Sinnoense"),
                    Autor(2,"Eriad","","Paldense"),
                    Autor(3,"Miguel","de Cervantes","Español")
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(verticalScrollState),
            ) {
                Column(
                    modifier = Modifier
                        .horizontalScroll(horizontalScrollState)
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text("ID", modifier = Modifier.width(50.dp), fontWeight = FontWeight.Bold)
                        Text("Nombre", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Apellidos", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Nacionalidad", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    autores.forEachIndexed { index, objeto ->
                        val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                        Row(
                            modifier = Modifier
                                .background(bgColor)
                        ) {
                            Text(
                                objeto.id.toString(), modifier = Modifier
                                    .width(50.dp)
                            )
                            Text(
                                objeto.autor, modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                objeto.apellidos, modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                objeto.nacionalidad, modifier = Modifier
                                    .width(100.dp)
                            )
                            Button(onClick = {
                                autores.removeAt(index)
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the buttons
                ) {
                    Button(
                        onClick = {
                            navController.navigate("formaut")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0075E0),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Insertar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Start,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.kyogre_icon),
                            contentDescription = "Kyogre Icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate("menu")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDD2F06),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Regresar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Start,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.groudon_icon),
                            contentDescription = "Groudon Icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        autores.add(Autor(4, "name", "surname", "data"))


                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF169226),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                ) {
                    Text(
                        "Insertar prueba",
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = coopbl,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.rayquaza_icon),
                        contentDescription = "Rayquaza Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }

            }
        }
        3 -> {
            data class Resena(val id: Int,val libro: String, val resena: String, val calificacion: Int, val critico: String)
            val resenas = remember {
                mutableStateListOf(
                    Resena(1,"La biblia de arceus","Te odio arceus, al menos cyrus me hace mejor compañia de la que tu me has hecho aqui en la dimensión distorsionada",0, "Giratina"),
                    Resena(2,"Libro Escarlata","Eriad",5, ""),
                    Resena(3,"Don Quixote de la mancha","muy bueno xde",6, "Omar")
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(verticalScrollState),
            ) {
                Column(
                    modifier = Modifier
                        .horizontalScroll(horizontalScrollState)
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text("ID", modifier = Modifier.width(25.dp), fontWeight = FontWeight.Bold)
                        Text("Libro", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Reseña", modifier = Modifier.width(300.dp), fontWeight = FontWeight.Bold)
                        Text("Calificación", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                        Text("Crítico", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    resenas.forEachIndexed { index, objeto ->
                        val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                        Row(
                            modifier = Modifier
                                .background(bgColor)
                        ) {
                            Text(
                                objeto.id.toString(), modifier = Modifier
                                    .width(25.dp)
                            )
                            Text(
                                objeto.libro, modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                objeto.resena, modifier = Modifier
                                    .width(300.dp)
                            )
                            Text(
                                objeto.calificacion.toString(), modifier = Modifier
                                    .width(100.dp)
                            )
                            Text(
                                objeto.critico, modifier = Modifier
                                    .width(100.dp)
                            )
                            Button(onClick = {
                                resenas.removeAt(index)
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the buttons
                ) {
                    Button(
                        onClick = {
                            navController.navigate("formres")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0075E0),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Insertar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Start,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.kyogre_icon),
                            contentDescription = "Kyogre Icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate("menu")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDD2F06),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Regresar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Start,
                            //modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.groudon_icon),
                            contentDescription = "Groudon Icon",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        resenas.add(Resena(4, "book", "content", 0, "critic"))


                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF169226),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)

                ) {
                    Text(
                        "Insertar prueba",
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = coopbl,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.rayquaza_icon),
                        contentDescription = "Rayquaza Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Ventas(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "menu") {
        composable("libros") { LstLibrosContent(navController, modifier, 1) }
        composable("autores") { LstLibrosContent(navController, modifier, 2) }
        composable("resenas") { LstLibrosContent(navController, modifier, 3) }
        composable("formlib") { PagForm(navController, modifier, 1) }
        composable("formaut") { PagForm(navController, modifier, 2) }
        composable("formres") { PagForm(navController, modifier, 3) }
        composable("menu") { LstMenuContent(navController, modifier) }
        composable("login") { PagLogin(navController, modifier) }
    }
}

@Composable
fun PagLogin(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var usuario: String by remember { mutableStateOf("") }
    var contrasena: String by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Inicio de Sesión",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Usuario:")
        TextField(
            value = usuario,
            onValueChange = { usuario = it },
            placeholder = { Text("Ingresa tu usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Contraseña:")
        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            placeholder = { Text("Ingresa tu contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val respuesta : Response<String> = api.iniciarSesion(usuario, contrasena)
                        if(respuesta.body()=="correcto"){
                            Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                            navController.navigate("ventas")
                        }else{
                            Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }
                    catch (e: Exception) {
                        Log.e("API", "Error al iniciar sesión: ${e.message}")
                    }
                }

                /*Toast.makeText(context, "Usuario: ${usuario}", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Contraseña: ${contrasena}", Toast.LENGTH_SHORT).show()
                if (usuario == "admin" && contrasena == "admin") {
                    Toast.makeText(context, "Credenciales correctas", Toast.LENGTH_SHORT).show()
                    navController.navigate("ventas")
                } else {
                    Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }*/
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFDDDED),
                contentColor = Color.DarkGray
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Iniciar sesión",
                    style = androidx.compose.ui.text.TextStyle(
                        fontFamily = coopbl,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.mew_icon),
                    contentDescription = "Mew Icon",
                    modifier = Modifier.size(64.dp)
                )

            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagForm(navController: NavHostController, modifier: Modifier = Modifier, tabla: Int) {
    val context = LocalContext.current
    var id: String by remember { mutableStateOf("") }
    var titulo: String by remember { mutableStateOf("") }
    var autor: String by remember { mutableStateOf("") }
    var fechaPub: String by remember { mutableStateOf("") }
    var precio: String by remember { mutableStateOf("") }

    when (tabla) {

        1 -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Insertar Entrada",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "ID")
                TextField(
                    value = id,
                    onValueChange = { id = it },
                    placeholder = { Text("Inserta el ID") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Titulo:")
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    placeholder = { Text("Ingresa el titulo") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Autor:")
                TextField(
                    value = autor,
                    onValueChange = { autor = it },
                    placeholder = { Text("Ingresa el autor") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Fecha publicación:")
                TextField(
                    value = fechaPub,
                    onValueChange = { fechaPub = it },
                    placeholder = { Text("Ingresa la fecha de Pub.") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Precio:")
                TextField(
                    value = precio,
                    onValueChange = { precio = it },
                    placeholder = { Text("Ingresa el precio") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the buttons
                ) {
                    Button(
                        onClick = {
                            navController.navigate("libros")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE5EAEE),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Regresar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 16.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.reshiram_icon),
                            contentDescription = "Kyogre Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "ID: ${id}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Titulo: ${titulo}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Autor: ${autor}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Fecha de publicación: ${fechaPub}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Precio: ${precio}", Toast.LENGTH_SHORT).show()
                            navController.navigate("libros")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4D5054),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Enviar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.zekrom_icon),
                            contentDescription = "Groudon Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                }
            }

        }
        2 -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Insertar Entrada",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "ID")
                TextField(
                    value = id,
                    onValueChange = { id = it },
                    placeholder = { Text("Inserta el ID") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Autor:")
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    placeholder = { Text("Ingresa el nombre del autor") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Apellidos:")
                TextField(
                    value = autor,
                    onValueChange = { autor = it },
                    placeholder = { Text("Ingresa sus apellidos") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Nacionalidad:")
                TextField(
                    value = fechaPub,
                    onValueChange = { fechaPub = it },
                    placeholder = { Text("Ingresa la nacionalidad") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the buttons
                ) {
                    Button(
                        onClick = {
                            navController.navigate("autores")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE5EAEE),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Regresar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 16.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.reshiram_icon),
                            contentDescription = "Kyogre Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "ID: ${id}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Nombre: ${titulo}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Apellidos: ${autor}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Nacionalidad: ${fechaPub}", Toast.LENGTH_SHORT).show()
                            navController.navigate("autores")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4D5054),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Enviar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.zekrom_icon),
                            contentDescription = "Groudon Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                }
            }

        }
        3 -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                val bookOptions = listOf("La biblia de arceus", "Libro Escarlata", "Don Quijote de la Mancha")
                var isExpanded by remember { mutableStateOf(false) }

                Text(
                    text = "Insertar Entrada",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "ID")
                TextField(
                    value = id,
                    onValueChange = { id = it },
                    placeholder = { Text("Inserta el ID") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Libro:")
                @OptIn(ExperimentalMaterial3Api::class)
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }
                ) {
                    TextField(
                        value = titulo,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona un Libro") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        bookOptions.forEach { book ->
                            DropdownMenuItem(
                                text = { Text(book) },
                                onClick = {
                                    titulo = book
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Resena:")
                TextField(
                    value = autor,
                    onValueChange = { autor = it },
                    placeholder = { Text("Escribe la resena") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Calificación:")
                TextField(
                    value = fechaPub,
                    onValueChange = { fechaPub = it },
                    placeholder = { Text("Calificación 1-5") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Crítico:")
                TextField(
                    value = precio,
                    onValueChange = { precio = it },
                    placeholder = { Text("Ingresa tu nombre") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the buttons
                ) {
                    Button(
                        onClick = {
                            navController.navigate("resenas")

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE5EAEE),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Regresar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 16.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.reshiram_icon),
                            contentDescription = "Kyogre Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "ID: ${id}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Libro: ${titulo}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Resena: ${autor}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Calificación: ${fechaPub}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "Crítico: ${precio}", Toast.LENGTH_SHORT).show()
                            navController.navigate("resenas")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4D5054),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Enviar",
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = coopbl,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.zekrom_icon),
                            contentDescription = "Groudon Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                }
            }

        }

    }


}


@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    /*BDLibrosTheme {
        PagLogin(navController = rememberNavController())
    }*/
    BDLibrosTheme {
        Ventas()
    }

}