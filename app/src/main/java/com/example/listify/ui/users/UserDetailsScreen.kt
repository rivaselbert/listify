package com.example.listify.ui.users

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.listify.R
import com.example.listify.data.model.User
import com.example.listify.extension.toDisplayDate
import com.example.listify.ui.components.FullImageViewer
import com.example.listify.ui.components.textfield.LabeledTextField
import com.example.listify.ui.theme.ListifyTheme
import com.example.listify.utils.TestDataFactory

@Composable
fun UserDetailsScreen(
    user: User,
    navigateBack: () -> Unit,
) {
    UserDetailsScreenContent(
        user = user,
        onBackClick = navigateBack
    )
}

@Composable
private fun UserDetailsScreenContent(
    user: User,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    var showFullImageViewer by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            UserDetailsScreenTopBar(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier
                    .size(130.dp)
                    .border(4.dp, MaterialTheme.colorScheme.surfaceContainerHighest, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        showFullImageViewer = true
                    },
                model = user.picture.large,
                placeholder = painterResource(R.drawable.empty_user_placeholder),
                error = painterResource(R.drawable.empty_user_placeholder),
                contentDescription = "User thumbnail",
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            LabeledTextField(
                label = stringResource(id = R.string.name),
                value = user.name.fullName,
            )
            LabeledTextField(
                label = stringResource(id = R.string.email),
                value = user.email,
            )
            LabeledTextField(
                label = stringResource(id = R.string.phone),
                value = user.phone,
            )
            LabeledTextField(
                label = stringResource(id = R.string.dob),
                value = user.dob.date.toDisplayDate(),
            )
            LabeledTextField(
                label = stringResource(id = R.string.age),
                value = user.dob.age.toString(),
            )
            LabeledTextField(
                label = stringResource(id = R.string.address),
                value = user.location.fullAddress,
            )
        }
    }

    if (showFullImageViewer) {
        FullImageViewer(
            imageUrl = user.picture.large,
            onDismiss = { showFullImageViewer = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserDetailsScreenTopBar(
    onBackClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.user_details),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                ),
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector =  Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back icon"
                )
            }
        },
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun UserDetailsScreenPreview() {
    ListifyTheme {
        UserDetailsScreenContent(
            user = TestDataFactory.allUsers.first(),
            onBackClick = {}
        )
    }
}