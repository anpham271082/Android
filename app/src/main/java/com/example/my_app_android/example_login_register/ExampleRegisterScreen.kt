package com.example.my_app_android.example_login_register

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.my_app_android.R

@Composable
fun ExampleRegisterScreen (navController: NavController, paddingValues: PaddingValues){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf( false) }
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val composition by rememberLottieComposition( LottieCompositionSpec.RawRes (R.raw.login_animation))
    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.7f
    )

    Column (
        modifier = Modifier
                .fillMaxSize()
                .padding (paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LottieAnimation(
            modifier = Modifier
                .size(180.dp)
                .align (Alignment.CenterHorizontally),
            composition = composition,
            progress = {progress}
        )

        Text(text = "Create Account",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth().padding (start = 25.dp,top= 15.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.ExtraBold)

        Spacer (modifier = Modifier.height(10.dp))
        Text(text = "Please enter your details",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth().padding(start = 25.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Light)

        Spacer (modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = {name = it},
            label = { Text (nameError.ifEmpty { "Name" }, color = if (nameError.isNotEmpty()) Red else Unspecified)},

            leadingIcon = {
                Icon(Icons.Rounded.Person,  contentDescription = "")
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer (modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text (emailError.ifEmpty { "Email" }, color = if (emailError.isNotEmpty()) Red else Unspecified)},

            leadingIcon = {
                Icon(Icons.Rounded.AccountCircle,  contentDescription = "")
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text (passwordError.ifEmpty { "Password" }, color = if (passwordError.isNotEmpty()) Red else Unspecified)},

            leadingIcon = {
                Icon(Icons.Rounded.Lock,  contentDescription = "")
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.visibility)
                else
                    painterResource(id = R.drawable.visibility_off)
                Icon(
                    painter = image,
                    contentDescription = "",
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible })
            },
            shape = RoundedCornerShape (8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding (vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors (
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent)
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = { Text (confirmPasswordError.ifEmpty { "Confirm Password" }, color = if (confirmPasswordError.isNotEmpty()) Red else Unspecified)},

            leadingIcon = {
                Icon(Icons.Rounded.Lock,  contentDescription = "")
            },
            visualTransformation = if(confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible)
                    painterResource(id = R.drawable.visibility)
                else
                    painterResource(id = R.drawable.visibility_off)
                Icon(
                    painter = image,
                    contentDescription = "",
                    modifier = Modifier.clickable { confirmPasswordVisible = !confirmPasswordVisible })
            },
            shape = RoundedCornerShape (8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding (vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors (
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent)
        )

        Spacer (modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                nameError = if (name.isBlank()) "Name is required" else ""
                emailError = if (email.isBlank()) "Email is required"
                else if (!Patterns.EMAIL_ADDRESS.matcher (email).matches())
                    "Enter a valid email address" else ""
                passwordError = if (password.isBlank()) "Password is required" else ""
                confirmPasswordError = if (confirmPassword.isBlank()) "Confirm password is required"
                else (if (password != confirmPassword) "Password do mot match" else "")

                if (nameError.isEmpty() && emailError.isEmpty() && passwordError.isEmpty() && confirmPasswordError.isEmpty()){
                    //handle registration logic here
                }
            }
        ){
            Text("Register")
        }

        Spacer (modifier = Modifier.height(8.dp))
        TextButton(
            onClick= {
                navController.navigate("Login")
            }) {
            Text("Already have am account? Login")
        }
    }
}
