package com.txd.compose

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.gyf.immersionbar.ktx.immersionBar
import com.txd.compose.ui.data.SampleData
import com.txd.compose.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        immersionBar {
            statusBarColor(R.color.white)
            navigationBarColor(R.color.white)
            fitsSystemWindows(true)
            statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
            navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
        }

        setContent {
            ComposeDemoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
//                    MessageCard(Message("Android", "Jetpack Compose"))
//                    Conversation(SampleData.conversationSample)
                    GetContentExample()
                }
            }
        }

    }
}

@Composable
fun MessageCard(msg: Message, modifier: Modifier = Modifier) {
    Row(modifier) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)

        Column(/*modifier = Modifier.clickable { isExpanded = !isExpanded }*/) {

            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(2.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 10.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
                    .clickable { isExpanded = !isExpanded }
                /*color = MaterialTheme.colors.secondary*/
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp, 8.dp, 24.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun GetContentExample() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    Column {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Load Image")
        }
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "My Image"
        )
    }
}

@Preview(
    name = "Conversation",
    group = "Conversation",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewConversation() {
    ComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Conversation(SampleData.conversationSample)
        }
    }
}

@Preview(
    name = "Light Mode",
    group = "Theme",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Dark Mode",
    group = "Theme",
    apiLevel = 31,
    widthDp = 200,
    heightDp = 100,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewMessageCard() {
    ComposeDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            MessageCard(
                Message("Android", "Jetpack Compose"),
                modifier = Modifier.padding(all = 8.dp)
            )
        }
    }
}

data class Message(val author: String, val body: String)
