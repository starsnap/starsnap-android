package com.photo.starsnap.main.ui.screen.auth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.title4
import com.photo.starsnap.main.ui.component.AppIcon
import com.photo.starsnap.main.ui.component.AppleLoginButton
import com.photo.starsnap.main.ui.component.EditText
import com.photo.starsnap.main.ui.component.GoogleLoginButton
import com.photo.starsnap.main.ui.component.MainButton
import com.photo.starsnap.main.ui.component.PasswordEditText
import com.photo.starsnap.main.ui.component.TextButton
import com.photo.starsnap.main.viewmodel.auth.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    moveSignupNavigation: () -> Unit,
    moveMainNavigation: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val googleLogin =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            // Once the account has been added, do sign in again.
            doGoogleSignIn(context, coroutineScope, loginViewModel, null)
        }
    var isClickable by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    isClickable = username.isNotBlank() && password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
    ) {
        AppIcon(
            Modifier
                .padding(vertical = 70.dp)
                .align(Alignment.CenterHorizontally)
        )

        EditText(stringResource(R.string.login_edit_text_username_hint)) { username = it }
        Spacer(Modifier.height(15.dp))
        PasswordEditText(hint = stringResource(R.string.login_edit_text_password_hint)) {
            password = it
        }
        Spacer(Modifier.height(20.dp))
        MainButton(
            { loginViewModel.login(username, password) },
            isClickable,
            stringResource(R.string.login)
        )
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(
                text = stringResource(R.string.login_find_username_button_text),
                textStyle = title4,
                onClick = { })
            Spacer(Modifier.width(30.dp))
            TextButton(
                text = stringResource(R.string.login_find_password_button_text),
                textStyle = title4,
                onClick = { })
            Spacer(Modifier.width(30.dp))
            TextButton(
                text = stringResource(R.string.signup),
                textStyle = title4,
                onClick = moveSignupNavigation
            )
        }
        Spacer(Modifier.weight(1F))
        Column {
            GoogleLoginButton(onClick = {
                doGoogleSignIn(
                    context,
                    coroutineScope,
                    loginViewModel,
                    null
                )
            })
            Spacer(Modifier.height(10.dp))
            AppleLoginButton {}
            Spacer(Modifier.height(30.dp))
        }
    }

}

private fun doGoogleSignIn(
    context: Context,
    coroutineScope: CoroutineScope,
    loginViewModel: LoginViewModel,
    startAddAccountIntentLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
) {
    val credentialManager = CredentialManager.create(context)

    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it ->
        str + "%02x".format(it)
    }

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.google_client_id))
        .setAutoSelectEnabled(false)
        .setNonce(hashedNonce)
        .build()

    val request: GetCredentialRequest = Builder()
        .addCredentialOption(googleIdOption)
        .build()

    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                // Use an activity-based context to avoid undefined system UI
                // launching behavior.
                context = context,
                request = request
            )
            loginViewModel.handleSignIn(result)
        } catch (e: NoCredentialException) {
            startAddAccountIntentLauncher?.launch(getAddGoogleAccountIntent())
            Log.d("SignupScreen", e.toString())
        }
    }


}

fun getAddGoogleAccountIntent(): Intent {
    val intent = Intent(Settings.ACTION_ADD_ACCOUNT)
    intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
    return intent
}